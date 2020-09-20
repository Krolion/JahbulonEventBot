package com.participants_bot_service.telegram;

import com.participants_bot_service.data.Chat;
import com.participants_bot_service.data.Question;
import com.participants_bot_service.telegram.handler.HandlerScenario;
import com.participants_bot_service.telegram.parser.UserMessageParser;
import com.participants_bot_service.utils.OptionalHandler;
import com.participants_bot_service.utils.Poster;
import com.sun.el.stream.Optional;
import lombok.SneakyThrows;
import org.apache.commons.io.filefilter.FalseFileFilter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.inject.Singleton;

@Component
@Singleton
public class QASlavePBot extends TelegramLongPollingBot {

    private final QASlavePBotCredentials credentials = new QASlavePBotCredentials();
    public final String server = "http://localhost:8084/api/"; //TODO Удалить это и написать нормально
    public SendMessage lastMessage = null;
    public Object lastUpdate;
    public final UserMessageParser questionCommandParser = new UserMessageParser("question");

    @Override
    @SneakyThrows
    public void onUpdateReceived(Update update) {
        lastUpdate = update;
        HandlerScenario scenario = HandlerScenario.findScenario(update, this);
        if (scenario != HandlerScenario.PASS) {
            scenario.getMessageHandler().handle(update, this);
        }
    }

    @SneakyThrows
    public void sendMessage(SendMessage sendMessage) {
        execute(sendMessage);
    }

    @Override
    public String getBotUsername() {
        return credentials.getBotUsername();
    }

    @Override
    public String getBotToken() {
        return credentials.getBotToken();
    }
}
