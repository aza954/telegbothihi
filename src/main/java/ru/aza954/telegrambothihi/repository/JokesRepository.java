package ru.aza954.telegrambothihi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import ru.aza954.telegrambothihi.model.Jokes;

import java.util.List;

public interface JokesRepository extends JpaRepository<Jokes, Long> {
    List<Jokes> findByTitle(String title);

    Page<Jokes> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    Page<Jokes> findAll(Pageable pageable);
}
