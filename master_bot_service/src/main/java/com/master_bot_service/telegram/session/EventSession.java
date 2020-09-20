package com.master_bot_service.telegram.session;

import com.master_bot_service.data.Chats;
import com.master_bot_service.data.Event;
import com.master_bot_service.telegram.parser.UserMessageParser;
import com.master_bot_service.utils.Poster;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.objects.Update;

public class EventSession {
    private final String server = "http://localhost:8084/api/";
    public int stage = 0;
    final public int final_stage = 2;
    public String username;
    public Event event = new Event();
    private final UserMessageParser stopCommandParser = new UserMessageParser("stop");

    public EventSession(String username) {
        this.username = username;
    }

    public String continueSession(Update update) {
        if (stopCommandParser.parseMessage(update.getMessage().getText()).isCommand) {
            return "";
        }
        int chatId = 0;
        RestTemplate restTemplate = new RestTemplate();
        switch(stage) {
            case 0:
                try { chatId= Integer.parseInt(update.getMessage().getText()); }
                catch (Exception e) { return "Не является целым числом, введите снова."; }
                Chats pChats = restTemplate.getForObject("http://localhost:8084/api/pchats", Chats.class);
                final int chatIdStream1 = chatId;
                if (pChats.chats.stream().anyMatch(n -> n.chat_id == chatIdStream1)) {
                    event.setParticipantsChatId(chatId);
                    stage += 1;
                    return "Id чата участников установлено. Введите id чата организаторов.";
                }
                else {
                    return "Id не найден в базе. Введите снова.";
                }
            case 1:
                try { chatId= Integer.parseInt(update.getMessage().getText()); }
                catch (Exception e) { return "Не является целым числом, введите снова."; }
                Chats oChats = restTemplate.getForObject("http://localhost:8084/api/ochats", Chats.class);
                final int chatIdStream2 = chatId;
                if (oChats.chats.stream().anyMatch(n -> n.chat_id == chatIdStream2)) {
                    event.setOrgsChatId(chatId);
                    stage += 1;
                    String s = (String) Poster.builder().aClassObject(Event.class)
                            .aClassReturn(String.class)
                            .object(event)
                            .url(server + "new_event")
                            .build().post();
                    return "Id чата организаторов установлено.\n" + s;
                }
                else {
                    return "Id не найден в базе. Введите снова.";
                }
            default: {
                return "Неизвестная ошибка.";
            }
        }
    }
}
