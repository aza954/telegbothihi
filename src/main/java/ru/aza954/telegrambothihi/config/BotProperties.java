package ru.aza954.telegrambothihi.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "bot")
@Data
public class BotProperties {
    private String telegramToken;
    private String gitlabToken;

}
