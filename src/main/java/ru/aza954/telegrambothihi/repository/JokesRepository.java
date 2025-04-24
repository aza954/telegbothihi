package ru.aza954.telegrambothihi.repository;

import org.springframework.data.repository.CrudRepository;
import ru.aza954.telegrambothihi.model.Jokes;

import java.util.List;

public interface JokesRepository extends CrudRepository<Jokes, Long> {
    List<Jokes> findByTitle(String title);
}
