package com.participants_bot_service.telegram.handler.condition;

import com.participants_bot_service.telegram.QASlavePBot;
import com.participants_bot_service.utils.OptionalHandler;
import org.telegram.telegrambots.meta.api.objects.Update;

public class IsNewChatMemberCondition implements HandlerCondition {
    @Override
    public boolean conditionMet(Update update, QASlavePBot qaSlavePBot) {
        return OptionalHandler.isNewMember(update, qaSlavePBot.getBotUsername());
    }
}
