package ru.aza954.telegrambothihi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.aza954.telegrambothihi.model.Subscriber;

import java.util.Optional;

@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {
    Optional<Subscriber> findByChatId(Long chatId);
    void deleteByChatId(Long chatId);
}
