package billy.config;


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
