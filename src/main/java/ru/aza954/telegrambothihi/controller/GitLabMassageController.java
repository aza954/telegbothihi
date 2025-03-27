package ru.aza954.telegrambothihi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import ru.aza954.telegrambothihi.model.GitLabEvent;
import ru.aza954.telegrambothihi.service.GitLabMassageService;

@RestController
@RequiredArgsConstructor
public class GitLabMassageController {
    private final GitLabMassageService gitLabMassageService;

    @PostMapping("/gitlab-webhook")
    public ResponseEntity<String> handleGitlabWebhook(
            @RequestBody GitLabEvent event,
            @RequestHeader("X-Gitlab-Token") String token) {
        return gitLabMassageService.handleGitlabWebhook(event, token);
    }
}