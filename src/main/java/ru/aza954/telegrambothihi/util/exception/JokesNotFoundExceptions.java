package ru.aza954.telegrambothihi.util.exception;

import lombok.Getter;

@Getter
public class JokesNotFoundExceptions extends RuntimeException {
    public JokesNotFoundExceptions(String message) {
        super(message);
    }
}
