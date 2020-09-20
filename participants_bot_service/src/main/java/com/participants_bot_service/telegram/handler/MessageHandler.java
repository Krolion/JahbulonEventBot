package com.participants_bot_service.telegram.handler;

import com.participants_bot_service.telegram.QASlavePBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface MessageHandler {
    void handle(Update update, QASlavePBot qaSlavePBot);
}
