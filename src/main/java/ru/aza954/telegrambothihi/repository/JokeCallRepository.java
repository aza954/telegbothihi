package ru.aza954.telegrambothihi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.aza954.telegrambothihi.model.JokeCall;

import java.util.List;

public interface JokeCallRepository extends JpaRepository<JokeCall, Long> {
    public interface JokeStats {
        Long getJokeId();
        Long getCount();
    }

    @Query("SELECT jc.joke.id as jokeId, COUNT(jc) as count FROM joke_calls jc GROUP BY jc.joke.id ORDER BY count DESC")
    List<JokeStats> findTopJokes();
}