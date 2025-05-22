package ru.aza954.telegrambothihi.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.aza954.telegrambothihi.model.Jokes;
import ru.aza954.telegrambothihi.model.dto.JokeSaveDTO;

import java.util.List;

public interface JokesService {

    Jokes addJokes(JokeSaveDTO jokeSaveDTO);

    List<Jokes> getAllJokes(String title);

    List<Jokes> getTopJokes(int limit);

    void logJokeCall(Long userId, Jokes joke);

    Page<Jokes> getJokesPage(String title, Pageable pageable);

    Jokes getJokesById(Long id);

    Jokes editJokes(Long id, JokeSaveDTO joke);

    void deleteJokes(Long id);
}

