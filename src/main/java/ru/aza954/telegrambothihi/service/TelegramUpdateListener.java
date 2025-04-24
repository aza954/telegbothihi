package ru.aza954.telegrambothihi.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.aza954.telegrambothihi.model.Jokes;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Component
public class TelegramUpdateListener implements UpdatesListener {

    private final JokesService jokesService;
    private final TelegramBot telegramBot;

    @Override
    public int process(List<Update> list) {
        for (Update update : list) {
            log.info("Получен апдейт: {}", update);

            if (update.message() != null && update.message().text() != null) {
                String messageText = update.message().text();
                Long chatId = update.message().chat().id();

                if (messageText.equals("/start")) {
                    telegramBot.execute(new SendMessage(chatId,
                            "Доступные команды:\n/jokes - случайная шутка"));
                }

                if (update.message().text().equals("/jokes")) {
                    List<Jokes> jokes = jokesService.getAllJokes(null);

                    if (!jokes.isEmpty()) {
                        Jokes joke = jokes.get((int) (Math.random() * jokes.size()));
                        String text = '"' + joke.getTitle() + '"' + '.' + "\n" + joke.getContent();
                        telegramBot.execute(new SendMessage(update.message().chat().id(), text));
                    }
                }
            }
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onReady() {
        telegramBot.setUpdatesListener(this);
    }
}
