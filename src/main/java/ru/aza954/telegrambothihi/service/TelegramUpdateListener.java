package ru.aza954.telegrambothihi.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import ru.aza954.telegrambothihi.model.Jokes;
import ru.aza954.telegrambothihi.model.dto.JokeSaveDTO;

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
                            "Доступные команды:\n" +
                                    "/jokes - случайная шутка\n" +
                                    "/add Заголовок Шутка - добавить шутку\n" +
                                    "/topjokes - топ популярных шуток\n" +
                                    "/jokepage N - список анекдотов на странице N (например, /jokepage 1)"
                    ));
                }


                if (messageText.equals("/jokes")) {
                    List<Jokes> jokes = jokesService.getAllJokes(null);
                    if (!jokes.isEmpty()) {
                        Jokes joke = jokes.get((int) (Math.random() * jokes.size()));
                        jokesService.logJokeCall(chatId, joke);
                        String text = '"' + joke.getTitle() + '"' + '.' + "\n" + joke.getContent();
                        telegramBot.execute(new SendMessage(chatId, text));
                    }
                }

                if (messageText.startsWith("/jokepage")) {
                    String[] parts = messageText.split(" ");
                    int pageNumber = 0;
                    int pageSize = 5;

                    if (parts.length >= 2) {
                        try {
                            pageNumber = Integer.parseInt(parts[1]) - 1;
                            if (pageNumber < 0) pageNumber = 0;
                        } catch (NumberFormatException e) {
                            telegramBot.execute(new SendMessage(chatId, "Неверный номер страницы. Используйте: /jokepage 1"));
                            return UpdatesListener.CONFIRMED_UPDATES_ALL;
                        }
                    }

                    Pageable pageable = PageRequest.of(pageNumber, pageSize);
                    Page<Jokes> jokePage = jokesService.getJokesPage(null, pageable);

                    if (jokePage.hasContent()) {
                        StringBuilder builder = new StringBuilder("Анекдоты (страница " + (pageNumber + 1) + " из " + jokePage.getTotalPages() + "):\n\n");
                        for (Jokes joke : jokePage.getContent()) {
                            builder.append("• ").append(joke.getTitle()).append("\n")
                                    .append(joke.getContent()).append("\n\n\n\n\n\n");
                        }
                        telegramBot.execute(new SendMessage(chatId, builder.toString()));
                    } else {
                        telegramBot.execute(new SendMessage(chatId, "Анекдотов не найдено на этой странице"));
                    }
                }


                if (messageText.equals("/topjokes")) {
                    List<Jokes> topJokes = jokesService.getTopJokes(5);
                    StringBuilder builder = new StringBuilder("Топ-5 самых популярных анекдотов:\n");
                    for (int i = 0; i < topJokes.size(); i++) {
                        Jokes joke = topJokes.get(i);
                        builder.append(i + 1)
                                .append(". ")
                                .append(joke.getTitle())
                                .append("\n");
                    }
                    telegramBot.execute(new SendMessage(chatId, builder.toString()));
                }

                if (update.message().text().startsWith("/add")) {
                    String[] parts = update.message().text().split(" ", 3); // ограничиваем на 3 части

                    if (parts.length < 3) {
                        telegramBot.execute(new SendMessage(update.message().chat().id(), "Ошибка: недостаточно аргументов."));
                        return UpdatesListener.CONFIRMED_UPDATES_ALL;
                    }

                    String command = parts[0];
                    String title = parts[1];
                    String content = parts[2];

                    JokeSaveDTO jokes = new JokeSaveDTO();
                    jokes.setTitle(title);
                    jokes.setContent(content);
                    jokesService.addJokes(jokes);
                    telegramBot.execute(new SendMessage(update.message().chat().id(), "Шутка добавлена!"));


                    System.out.println("Команда: " + command);
                    System.out.println("Тайтл: " + title);
                    System.out.println("Контент: " + content);
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
