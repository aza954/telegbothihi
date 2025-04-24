package ru.aza954.telegrambothihi.repository;

import org.springframework.data.repository.CrudRepository;
import ru.aza954.telegrambothihi.model.Jokes;

public interface JokesRepository extends CrudRepository<Jokes, Long> {
}
