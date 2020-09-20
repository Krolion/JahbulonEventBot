package com.participants_bot_service.telegram.handler;

import com.participants_bot_service.telegram.QASlavePBot;
import com.participants_bot_service.telegram.handler.condition.HandlerCondition;
import com.participants_bot_service.telegram.handler.condition.HasQuestionCommandCondition;
import com.participants_bot_service.telegram.handler.condition.IsNewChatMemberCondition;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;

@RequiredArgsConstructor
public enum HandlerScenario {
    ADDED_TO_CHAT(new IsNewChatMemberCondition(), new IsNewChatMemberHandler()),
    QUESTION_COMMAND(new HasQuestionCommandCondition(), new HasQuestionCommandHandler()),
    PASS(null, null);

    @Getter
    private final HandlerCondition handlerCondition;
    @Getter
    private final MessageHandler messageHandler;

    public static HandlerScenario findScenario(Update update, QASlavePBot qaSlavePBot) {

        for (HandlerScenario handlerScenario : values()) {
            if (handlerScenario == HandlerScenario.PASS) {
                return HandlerScenario.PASS;
            }
            if (handlerScenario.getHandlerCondition().conditionMet(update, qaSlavePBot)) {
                return handlerScenario;
            }
        }
        throw new UnsupportedOperationException("Operation is not supported");
    }
}
