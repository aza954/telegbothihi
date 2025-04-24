package ru.aza954.telegrambothihi.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(JokesNotFoundExceptions.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ErrorResponse handleJokesNotFound(JokesNotFoundExceptions ex) {
        return new ErrorResponse(ex.getMessage());
    }
}
