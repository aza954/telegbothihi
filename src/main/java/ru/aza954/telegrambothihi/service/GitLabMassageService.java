package ru.aza954.telegrambothihi.service;

import org.springframework.http.ResponseEntity;
import ru.aza954.telegrambothihi.model.GitLabEvent;

public interface GitLabMassageService {
    ResponseEntity<String> handleGitlabWebhook(GitLabEvent event, String token);
}
