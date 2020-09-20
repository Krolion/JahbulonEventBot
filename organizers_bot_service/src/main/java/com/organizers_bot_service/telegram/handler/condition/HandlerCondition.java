package com.organizers_bot_service.telegram.handler.condition;

import com.organizers_bot_service.telegram.QASlaveOBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface HandlerCondition {
    boolean conditionMet(Update update, QASlaveOBot qaSlaveOBot);
}
