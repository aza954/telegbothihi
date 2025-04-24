package ru.aza954.telegrambothihi.service;


import ru.aza954.telegrambothihi.model.Jokes;
import ru.aza954.telegrambothihi.model.dto.JokeSaveDTO;

import java.util.List;

public interface JokesService {

    Jokes addJokes(JokeSaveDTO jokeSaveDTO);

    List<Jokes> getAllJokes(String title);

    Jokes getJokesById(Long id);

    Jokes editJokes(Long id, JokeSaveDTO joke);

    void deleteJokes(Long id);
}

