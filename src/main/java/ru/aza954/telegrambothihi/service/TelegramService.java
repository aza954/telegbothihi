package ru.aza954.telegrambothihi.service;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface TelegramService {
    void subscribeTelegram(Update update);
}
