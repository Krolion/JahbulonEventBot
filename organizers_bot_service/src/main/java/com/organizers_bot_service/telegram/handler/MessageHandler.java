package com.organizers_bot_service.telegram.handler;

import com.organizers_bot_service.telegram.QASlaveOBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface MessageHandler {
    void handle(Update update, QASlaveOBot qaSlaveOBot);
}
