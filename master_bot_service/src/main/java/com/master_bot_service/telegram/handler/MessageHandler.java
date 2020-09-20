package com.master_bot_service.telegram.handler;

import com.master_bot_service.telegram.MasterBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface MessageHandler {
    void handle(Update update, MasterBot masterBot);
}
