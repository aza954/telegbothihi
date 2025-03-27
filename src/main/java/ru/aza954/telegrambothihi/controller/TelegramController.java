package ru.aza954.telegrambothihi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.aza954.telegrambothihi.service.TelegramService;

@RestController
@RequestMapping("/telegram")
@Transactional
@RequiredArgsConstructor
public class TelegramController {
    private final TelegramService telegramService;

    @PostMapping("/webhook")
    public void handleUpdate(@RequestBody Update update) {
        telegramService.subscribeTelegram(update);
    }
}
