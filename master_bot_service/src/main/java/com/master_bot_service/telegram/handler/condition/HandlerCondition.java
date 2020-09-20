package com.master_bot_service.telegram.handler.condition;

import com.master_bot_service.telegram.MasterBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface HandlerCondition {
    boolean conditionMet(Update update, MasterBot masterBot);
}
