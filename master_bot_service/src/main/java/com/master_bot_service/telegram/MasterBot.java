package com.master_bot_service.telegram;

import com.master_bot_service.data.Chats;
import com.master_bot_service.telegram.parser.UserMessageParser;
import com.master_bot_service.data.Event;
import lombok.SneakyThrows;
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
public class MasterBot extends TelegramLongPollingBot {


    private final String server = "http://localhost:8084/api/"; //TODO Удалить это и написать нормально
    public SendMessage lastMessage = null;
    public int a = 0;
    public Object lastUpdate;
    private final UserMessageParser createEventCommandParser = new UserMessageParser("create_event");
    private final UserMessageParser chatListCommandParser = new UserMessageParser("chat_list");

    @Override
    @SneakyThrows
    public void onUpdateReceived(Update update) {
        lastUpdate = update;
        boolean flag = false;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "application/json");
        try {
            flag = chatListCommandParser.parseMessage(update.getMessage().getText()).isCommand;
        } catch (Exception ignored) {}
        if (flag) {
            // Логика если есть команда /question в начале
            Chats pChats = restTemplate.getForObject(server + "ochats", Chats.class);
            Chats oChats = restTemplate.getForObject(server + "pchats", Chats.class);
            SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());
            assert oChats != null;
            assert pChats != null;
            sendMessage.setText("Participants chats \n" + pChats.chats.toString()
                    + "\nOrgs chat\n" + oChats.chats.toString());
            lastMessage = sendMessage;
            execute(sendMessage);
            return;
        }
        try {
            flag = createEventCommandParser.parseMessage(update.getMessage().getText()).isCommand;
        } catch (Exception ignored) {}
        if (flag) {
            long orgsChatId = -1;
            long participantsChatId = -1;
            SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());
            sendMessage.setText("Ошибочка вышла");
            try {
                StringBuilder orgsChatIdString = new StringBuilder();
                StringBuilder participantsChatIdString = new StringBuilder();
                int f = 0;
                for (int i = 0; i < createEventCommandParser.text.length(); ++i) {
                    if (' ' == createEventCommandParser.text.charAt(i)) {
                        f++;
                        i++;
                    }
                    if (f == 0) {
                        orgsChatIdString.append(createEventCommandParser.text.charAt(i));
                    }
                    if (f == 1) {
                        participantsChatIdString.append(createEventCommandParser.text.charAt(i));
                    }
                }
                orgsChatId = Integer.parseInt(orgsChatIdString.toString());
                participantsChatId = Integer.parseInt(participantsChatIdString.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            HttpEntity<Event> request = new HttpEntity<Event>(Event.builder().orgsChatId(orgsChatId)
                    .participantsChatId(participantsChatId).eventId(-1).build(), httpHeaders);
            String s = restTemplate.postForObject(server + "new_event", request, String.class);
            sendMessage.setText(s);
            lastMessage = sendMessage;
            try {
                execute(sendMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public String getBotUsername() {
        return "test25675Bot";
    } //TODO Спрятать это в xml-файл

    @Override
    public String getBotToken() {
        return "1201843114:AAGoejQVOezT3W5S-5AP9A7usC5xHIfMln4";
    } //TODO Спрятать это в xml-файл
}