package ru.aza954.telegrambothihi.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.aza954.telegrambothihi.model.Jokes;
import ru.aza954.telegrambothihi.model.dto.JokeSaveDTO;
import ru.aza954.telegrambothihi.repository.JokesRepository;
import ru.aza954.telegrambothihi.service.JokesService;
import ru.aza954.telegrambothihi.util.exception.JokesNotFoundExceptions;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@Service
public class JokesServiceImpl implements JokesService {

    private final JokesRepository jokesRepository;

    public Jokes addJokes(JokeSaveDTO joke) {
        Jokes joke1 = new Jokes(joke.getTitle(), joke.getContent());
        return jokesRepository.save(joke1);
    }

    public List<Jokes> getAllJokes(String title) {
        if (title != null) {
            return StreamSupport.stream(jokesRepository.findAll().spliterator(), false)
                    .filter(joke -> title.equals(joke.getTitle()))
                    .collect(Collectors.toList());
        } else {
            return (List<Jokes>) jokesRepository.findAll();
        }
    }

    public Jokes getJokesById(Long id) {
        Jokes jokes = getJokesOrThrowExcep(id);
        return jokes;
    }

    private Jokes getJokesOrThrowExcep(Long id) {
        Jokes jokes = jokesRepository.findById(id).orElseThrow(() -> new JokesNotFoundExceptions("Шутка с таким id: " + id + " не найдена"));
        return jokes;
    }

    public Jokes editJokes(Long id, JokeSaveDTO joke) {
        Jokes joke1 = getJokesOrThrowExcep(id);
        joke1.setTitle(joke.getTitle());
        joke1.setContent(joke.getContent());
        return joke1;
    }

    public void deleteJokes(Long id) {
        if (!jokesRepository.existsById(id)) {
            throw new JokesNotFoundExceptions("Шутка с таким id: " + id + " не найдена");
        }
        jokesRepository.deleteById(id);
    }
}