package ru.aza954.telegrambothihi.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.aza954.telegrambothihi.config.BotProperties;
import ru.aza954.telegrambothihi.model.GitLabEvent;
import ru.aza954.telegrambothihi.repository.SubscriberRepository;
import ru.aza954.telegrambothihi.service.GitLabMassageService;

@Service
@RequiredArgsConstructor
@Slf4j
public class GitLabMassageServiceImpl implements GitLabMassageService {
    private final SubscriberRepository subscriberRepository;
    private final BotProperties botProperties;

    @Override
    public ResponseEntity<String> handleGitlabWebhook(GitLabEvent event, String token) {
        log.debug("Пришел вебхук: {}", event);
        if (!botProperties.getGitlabToken().equals(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }

        if (isValidMerge(event)) {
            sendNotifications(event);
        }

        return ResponseEntity.ok("Webhook processed");
    }

    private boolean isValidMerge(GitLabEvent event) {
        return "merge_request".equals(event.getObjectKind()) &&
                "merged".equals(event.getObjectAttributes().getState());
    }

    private void sendNotifications(GitLabEvent event) {
        String message = String.format(
                "🚨 Мердж в проект %s%nВетка: %s%nТребуется перезапуск ноды в Kubernetes!",
                event.getProject().getName(),
                event.getObjectAttributes().getTargetBranch()
        );

        RestTemplate restTemplate = new RestTemplate();


        subscriberRepository.findAll().forEach(subscriber -> {
            String url = UriComponentsBuilder.fromHttpUrl("https://api.telegram.org/bot" + botProperties.getTelegramToken() + "/sendMessage")
                    .queryParam("chat_id", subscriber.getChatId())
                    .queryParam("text", message)
                    .build()
                    .toUriString();

            System.out.println("Sending to URL: " + url);
            restTemplate.getForObject(url, String.class);
        });
    }
}
