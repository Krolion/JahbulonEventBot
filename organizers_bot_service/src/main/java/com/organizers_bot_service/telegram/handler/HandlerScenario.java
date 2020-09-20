package com.organizers_bot_service.telegram.handler;

import com.organizers_bot_service.telegram.QASlaveOBot;
import com.organizers_bot_service.telegram.handler.condition.HandlerCondition;
import com.organizers_bot_service.telegram.handler.condition.HasAnswerCommandCondition;
import com.organizers_bot_service.telegram.handler.condition.HasAnswerFinalCommandCondition;
import com.organizers_bot_service.telegram.handler.condition.IsNewChatMemberCondition;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;

@RequiredArgsConstructor
public enum HandlerScenario {
    ADDED_TO_CHAT(new IsNewChatMemberCondition(), new IsNewChatMemberHandler()),
    ANSWER_FINAL_COMMAND(new HasAnswerFinalCommandCondition(), new HasAnswerFinalCommandHandler()),
    ANSWER_COMMAND(new HasAnswerCommandCondition(), new HasAnswerCommandHandler()),
    PASS(null, null);

    @Getter
    private final HandlerCondition handlerCondition;
    @Getter
    private final MessageHandler messageHandler;

    public static HandlerScenario findScenario(Update update, QASlaveOBot qaSlaveOBot) {

        for (HandlerScenario handlerScenario : values()) {
            if (handlerScenario == HandlerScenario.PASS) {
                return HandlerScenario.PASS;
            }
            if (handlerScenario.getHandlerCondition().conditionMet(update, qaSlaveOBot)) {
                return handlerScenario;
            }
        }
        throw new UnsupportedOperationException("Operation is not supported");
    }
}
