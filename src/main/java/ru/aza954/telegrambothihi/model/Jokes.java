package ru.aza954.telegrambothihi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity(name = "jokes")
@Table(name = "jokes")
public class Jokes {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jokes_seq_gen")
    @SequenceGenerator(name = "jokes_seq_gen", sequenceName = "jokes_id_seq", allocationSize = 1)
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    public Jokes(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
