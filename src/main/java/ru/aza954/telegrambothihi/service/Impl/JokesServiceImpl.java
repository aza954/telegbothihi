package ru.aza954.telegrambothihi.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.aza954.telegrambothihi.model.JokeCall;
import ru.aza954.telegrambothihi.model.Jokes;
import ru.aza954.telegrambothihi.model.dto.JokeSaveDTO;
import ru.aza954.telegrambothihi.repository.JokeCallRepository;
import ru.aza954.telegrambothihi.repository.JokesRepository;
import ru.aza954.telegrambothihi.service.JokesService;
import ru.aza954.telegrambothihi.util.exception.JokesNotFoundExceptions;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@Service
public class JokesServiceImpl implements JokesService {

    private final JokesRepository jokesRepository;

    private final JokeCallRepository jokeCallRepository;

    public void logJokeCall(Long userId, Jokes joke) {
        JokeCall call = new JokeCall(userId, LocalDateTime.now(), joke);
        jokeCallRepository.save(call);
    }

    public Page<Jokes> getJokesPage(String title, Pageable pageable) {
        if (title != null && !title.isEmpty()) {
            return jokesRepository.findByTitleContainingIgnoreCase(title, pageable);
        }
        return jokesRepository.findAll(pageable);
    }

    public List<Jokes> getTopJokes(int limit) {
        List<JokeCallRepository.JokeStats> stats = jokeCallRepository.findTopJokes();
        List<Long> jokeIds = stats.stream()
                .map(JokeCallRepository.JokeStats::getJokeId)
                .limit(limit)
                .collect(Collectors.toList());
        return StreamSupport.stream(jokesRepository.findAllById(jokeIds).spliterator(), false)
                .collect(Collectors.toList());

    }

    public Jokes addJokes(JokeSaveDTO joke) {
        Jokes joke1 = new Jokes(joke.getTitle(), joke.getContent());
        return jokesRepository.save(joke1);
    }

    public List<Jokes> getAllJokes(String title) {
        if (title != null) {
            return jokesRepository.findByTitle(title);
        }
        return (List<Jokes>) jokesRepository.findAll();
    }

    public Jokes getJokesById(Long id) {
        return getJokesOrThrowExcep(id);
    }

    private Jokes getJokesOrThrowExcep(Long id) {
        return jokesRepository.findById(id).orElseThrow(() -> new JokesNotFoundExceptions("Шутка с таким id: " + id + " не найдена"));
    }

    public Jokes editJokes(Long id, JokeSaveDTO joke) {
        Jokes joke1 = getJokesOrThrowExcep(id);
        joke1.setTitle(joke.getTitle());
        joke1.setContent(joke.getContent());

        return jokesRepository.save(joke1);
    }

    public void deleteJokes(Long id) {
        if (!jokesRepository.existsById(id)) {
            throw new JokesNotFoundExceptions("Шутка с таким id: " + id + " не найдена");
        }
        jokesRepository.deleteById(id);
    }
}