package com.master_bot_service.telegram.handler;

import com.master_bot_service.telegram.MasterBot;
import com.master_bot_service.telegram.session.EventSession;
import lombok.SneakyThrows;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class HasCreateEventCommandHandler implements MessageHandler {
    @Override
    @SneakyThrows
    public void handle(Update update, MasterBot masterBot) {
        SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());
        if (masterBot.createEventCommandParser.text.equals("")){
            EventSession eventSession = new EventSession(update.getMessage().getFrom().getUserName());
            masterBot.currentSessions.put(eventSession.username, eventSession);
            sendMessage.setText("Введите id чата участников.");
        }
        else {
            sendMessage.setText("В данной версии /create_event не нуждается в параметрах. " +
                    "Введите команду /create_event без других значений.");
        }
        masterBot.lastMessage = sendMessage;
        masterBot.execute(sendMessage);
    }
}
