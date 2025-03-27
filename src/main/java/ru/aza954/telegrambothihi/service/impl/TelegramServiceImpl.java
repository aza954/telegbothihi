package ru.aza954.telegrambothihi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.aza954.telegrambothihi.config.BotProperties;
import ru.aza954.telegrambothihi.model.Subscriber;
import ru.aza954.telegrambothihi.repository.SubscriberRepository;
import ru.aza954.telegrambothihi.service.TelegramService;

@Service
@RequiredArgsConstructor
public class TelegramServiceImpl implements TelegramService {
    private final SubscriberRepository subscriberRepository;
    private final BotProperties botProperties;

    @Override
    public void subscribeTelegram(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Message msg = update.getMessage();
            String text = msg.getText();
            Long chatId = msg.getChatId();

            switch (text) {
                case "/start", "/subscribe" -> handleSubscribe(chatId, msg.getFrom().getUserName());
                case "/unsubscribe", "/stop" -> handleUnsubscribe(chatId);
            }
        }
    }

    private void handleSubscribe(Long chatId, String username) {
        subscriberRepository.findByChatId(chatId).ifPresentOrElse(
                s -> sendMessage(chatId, "Вы уже подписаны"),
                () -> {
                    subscriberRepository.save(new Subscriber(null, chatId, username));
                    sendMessage(chatId, "✅ Вы подписались на уведомления");
                }
        );
    }

    private void handleUnsubscribe(Long chatId) {
        subscriberRepository.deleteByChatId(chatId);
        sendMessage(chatId, "❌ Вы отписались от уведомлений");
    }

    private void sendMessage(Long chatId, String text) {
        String url = String.format(
                "https://api.telegram.org/bot%s/sendMessage?chat_id=%d&text=%s",
                botProperties.getTelegramToken(),
                chatId,
                text
        );
        new RestTemplate().getForObject(url, String.class);
    }
}
