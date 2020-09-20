package com.master_bot_service.telegram;

import com.master_bot_service.data.Chats;
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
    private final String server = "http://localhost:8084/api/"; //TODO Удалить это и написать нормально
    public SendMessage lastMessage = null;
    public int a = 0;
    public Object lastUpdate;
    public HashMap<String, EventSession> currentSessions = new HashMap<>();
    private final UserMessageParser createEventCommandParser = new UserMessageParser("create_event");
    private final UserMessageParser chatListCommandParser = new UserMessageParser("chat_list");

    @Override
    @SneakyThrows
    public void onUpdateReceived(Update update) {
        lastUpdate = update;
        if (OptionalHandler.inCurrentSession(update, this)) {
            this.continueSession(update);
            return;
        }
        if (OptionalHandler.hasCommand(update, chatListCommandParser)) {
            SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());
            RestTemplate restTemplate = new RestTemplate();
            Chats pChats = restTemplate.getForObject(server + "pchats", Chats.class);
            Chats oChats = restTemplate.getForObject(server + "ochats", Chats.class);
            assert oChats != null;
            assert pChats != null;
            sendMessage.setText("Participants chats \n" + pChats.chats.toString()
                    + "\nOrgs chat\n" + oChats.chats.toString());
            lastMessage = sendMessage;
            execute(sendMessage);
            return;
        }
        if (OptionalHandler.hasCommand(update, createEventCommandParser)) {
            SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());
            if (createEventCommandParser.text.equals("")){
                EventSession eventSession = new EventSession(update.getMessage().getFrom().getUserName());
                currentSessions.put(eventSession.username, eventSession);
                sendMessage.setText("Введите id чата участников.");
            }
            else {
                sendMessage.setText("В данной версии /create_event не нуждается в параметрах. " +
                        "Введите команду /create_event без других значений.");
            }
            lastMessage = sendMessage;
            try {
                execute(sendMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void continueSession(Update update) {
        SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());
        String s = currentSessions.get(update.getMessage().getFrom().getUserName()).continueSession(update);
        if (currentSessions.get(update.getMessage().getFrom().getUserName()).stage ==
                currentSessions.get(update.getMessage().getFrom().getUserName()).final_stage) {
            currentSessions.remove(update.getMessage().getFrom().getUserName());
        }
        if (s.equals("")) {
            sendMessage.setText("Сессия остановлена. Все изменения удалены.");
            currentSessions.remove(update.getMessage().getFrom().getUserName());
            lastMessage = sendMessage;
            try {
                execute(sendMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
        sendMessage.setText(s);
        lastMessage = sendMessage;
        try {
            execute(sendMessage);
        } catch (Exception e) {
            e.printStackTrace();
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