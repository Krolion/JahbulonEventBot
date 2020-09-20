package com.master_bot_service.utils;

import com.master_bot_service.telegram.parser.UserMessageParser;
import org.telegram.telegrambots.meta.api.objects.Update;

public class OptionalHandler {
    public static boolean isNewMember(Update update, String botUserName) {
        boolean flag = false;
        try {
            if (update.getMessage().getNewChatMembers().stream().anyMatch(n -> n.getUserName().equals(botUserName))) {
                flag = true;
            }
            if (!flag) { flag = update.getMessage().getGroupchatCreated(); }
            return flag;
        } catch (Exception ignored) {}
        return false;
    }

    public static boolean hasCommand(Update update, UserMessageParser messageParser) {
        boolean flag = false;
        try {
            flag = messageParser.parseMessage(update.getMessage().getText()).isCommand;
            return flag;
        } catch (Exception ignored) {}
        return false;
    }
}
