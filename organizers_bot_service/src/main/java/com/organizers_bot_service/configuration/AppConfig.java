package com.organizers_bot_service.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;

import javax.inject.Singleton;

@Configuration
public class AppConfig {

    @Bean
    @Singleton
    public TelegramBotsApi telegramBotsApi() {
        return new TelegramBotsApi();
    }

}
