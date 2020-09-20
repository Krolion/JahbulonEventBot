package com.organizers_bot_service.telegram;

import com.organizers_bot_service.data.Chat;
import com.organizers_bot_service.data.Question;
import com.organizers_bot_service.data.QuestionWithAnswer;
import com.organizers_bot_service.telegram.handler.HandlerScenario;
import com.organizers_bot_service.telegram.parser.UserMessageParser;
import com.organizers_bot_service.utils.OptionalHandler;
import com.organizers_bot_service.utils.Poster;
import lombok.SneakyThrows;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.HashMap;

@Component
@Singleton
public class QASlaveOBot extends TelegramLongPollingBot {

    private final QASlaveOBotCredentials credentials = new QASlaveOBotCredentials();
    public final String server = "http://localhost:8084/api/"; //TODO Удалить это и написать нормально
    public SendMessage lastMessage;
    public Update lastUpdate;
    public ArrayList<Question> activeQuestions = new ArrayList<Question>();
    public HashMap<String, Question> myQuestions = new HashMap<String, Question>();
    public final UserMessageParser answerCommandParser = new UserMessageParser("answer");
    public final UserMessageParser answerFinalCommandParser = new UserMessageParser("answer_final");


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
