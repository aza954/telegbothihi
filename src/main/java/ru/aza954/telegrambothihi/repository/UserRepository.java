package ru.aza954.telegrambothihi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aza954.telegrambothihi.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);
}

