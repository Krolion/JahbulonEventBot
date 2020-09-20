package com.master_bot_service.telegram.handler.condition;

import com.master_bot_service.telegram.MasterBot;
import com.master_bot_service.utils.OptionalHandler;
import org.telegram.telegrambots.meta.api.objects.Update;

public class HasCreateEventCommandCondition implements HandlerCondition {
    @Override
    public boolean conditionMet(Update update, MasterBot masterBot) {
        return OptionalHandler.hasCommand(update, masterBot.createEventCommandParser);
    }
}
