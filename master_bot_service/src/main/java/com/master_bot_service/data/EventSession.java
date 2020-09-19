package com.master_bot_service.data;

import com.master_bot_service.telegram.MasterBot;
import com.master_bot_service.telegram.parser.UserMessageParser;
import org.telegram.telegrambots.meta.api.objects.Update;

public class EventSession {
    public int stage = 0;
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
        switch(stage) {
            case 0:
                try {
                    event.setOrgsChatId(Integer.parseInt(update.getMessage().getText()));
                }
                catch (Exception e) {
                    return "Не является целым числом, введите число";
                }
                stage += 1;
                return "Id чата участников установлено";
            case 1:
                break;
            default:
        }
        return "Unfinished";
    }
}
