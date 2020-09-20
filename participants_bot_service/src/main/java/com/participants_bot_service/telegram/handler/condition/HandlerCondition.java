package com.participants_bot_service.telegram.handler.condition;

import com.participants_bot_service.telegram.QASlavePBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface HandlerCondition {
    boolean conditionMet(Update update, QASlavePBot qaSlavePBot);
}
