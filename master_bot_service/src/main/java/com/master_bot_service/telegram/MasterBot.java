package com.master_bot_service.telegram;

import com.master_bot_service.data.Chats;
import com.master_bot_service.telegram.handler.HandlerScenario;
import com.master_bot_service.telegram.parser.UserMessageParser;
import com.master_bot_service.data.Event;
import com.master_bot_service.telegram.session.EventSession;
import com.master_bot_service.utils.OptionalHandler;
import com.master_bot_service.utils.Poster;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.inject.Singleton;
import java.util.HashMap;

@Component
@Singleton
public class MasterBot extends TelegramLongPollingBot {


    private final MasterBotCredentials credentials = new MasterBotCredentials();
    public final String server = "http://localhost:8084/api/"; //TODO Удалить это и написать нормально
    public SendMessage lastMessage = null;
    public int a = 0;
    public Object lastUpdate;
    public HashMap<String, EventSession> currentSessions = new HashMap<>();
    public final UserMessageParser createEventCommandParser = new UserMessageParser("create_event");
    public final UserMessageParser chatListCommandParser = new UserMessageParser("chat_list");

    @Override
    @SneakyThrows
    public void onUpdateReceived(Update update) {
        lastUpdate = update;
        HandlerScenario scenario = HandlerScenario.findScenario(update, this);
        if (scenario != HandlerScenario.PASS) {
            scenario.getMessageHandler().handle(update, this);
        }
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