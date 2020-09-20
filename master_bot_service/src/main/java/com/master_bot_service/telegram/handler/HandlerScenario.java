package com.master_bot_service.telegram.handler;

import com.master_bot_service.telegram.MasterBot;
import com.master_bot_service.telegram.handler.condition.HandlerCondition;
import com.master_bot_service.telegram.handler.condition.HasActiveSessionCondition;
import com.master_bot_service.telegram.handler.condition.HasChatListCommandCondition;
import com.master_bot_service.telegram.handler.condition.HasCreateEventCommandCondition;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;

@RequiredArgsConstructor
public enum HandlerScenario {
    CONTINUE_SESSION(new HasActiveSessionCondition(), new HasActiveSessionHandler()),
    CHAT_LIST_COMMAND(new HasChatListCommandCondition(), new HasChatListCommandHandler()),
    CREATE_EVENT_COMMAND(new HasCreateEventCommandCondition(), new HasCreateEventCommandHandler()),
    PASS(null, null);

    @Getter
    private final HandlerCondition handlerCondition;
    @Getter
    private final MessageHandler messageHandler;

    public static HandlerScenario findScenario(Update update, MasterBot masterBot) {

        for (HandlerScenario handlerScenario : values()) {
            if (handlerScenario == HandlerScenario.PASS) {
                return HandlerScenario.PASS;
            }
            if (handlerScenario.getHandlerCondition().conditionMet(update, masterBot)) {
                return handlerScenario;
            }
        }
        throw new UnsupportedOperationException("Operation is not supported");
    }
}
