package ru.aza954.telegrambothihi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity(name = "joke_calls")
@Table(name = "joke_calls")
public class JokeCall {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "joke_calls_seq_gen")
    @SequenceGenerator(name = "joke_calls_seq_gen", sequenceName = "joke_calls_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "call_time", nullable = false)
    private LocalDateTime callTime;

    @ManyToOne
    @JoinColumn(name = "joke_id", nullable = false)
    private Jokes joke;

    public JokeCall(Long userId, LocalDateTime callTime, Jokes joke) {
        this.userId = userId;
        this.callTime = callTime;
        this.joke = joke;
    }
}