package ru.aza954.telegrambothihi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.aza954.telegrambothihi.service.UserService;

@RestController
@RequestMapping("/api/registration")
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<String> register(@RequestBody RegistrationRequest request) {
        userService.registerNewUser(request.getUsername(), request.getPassword());
        return ResponseEntity.ok("Пользователь успешно зарегистрирован");
    }

    public static class RegistrationRequest {
        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}