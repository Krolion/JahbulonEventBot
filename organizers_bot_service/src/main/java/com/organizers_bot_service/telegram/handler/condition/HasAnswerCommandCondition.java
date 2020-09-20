package com.organizers_bot_service.telegram.handler.condition;

import com.organizers_bot_service.telegram.QASlaveOBot;
import com.organizers_bot_service.utils.OptionalHandler;
import org.telegram.telegrambots.meta.api.objects.Update;

public class HasAnswerCommandCondition implements HandlerCondition {
    @Override
    public boolean conditionMet(Update update, QASlaveOBot qaSlaveOBot) {
        return OptionalHandler.hasCommand(update, qaSlaveOBot.answerCommandParser);
    }
}
