package com.master_bot_service.telegram.handler;

import com.master_bot_service.telegram.MasterBot;
import lombok.SneakyThrows;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class HasActiveSessionHandler implements MessageHandler {
    @Override
    @SneakyThrows
    public void handle(Update update, MasterBot masterBot) {
        SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());
        String s = masterBot.currentSessions.get(update.getMessage().getFrom().getUserName()).continueSession(update);
        if (masterBot.currentSessions.get(update.getMessage().getFrom().getUserName()).stage ==
                masterBot.currentSessions.get(update.getMessage().getFrom().getUserName()).final_stage) {
            masterBot.currentSessions.remove(update.getMessage().getFrom().getUserName());
        }
        if (s.equals("")) {
            sendMessage.setText("Сессия остановлена. Все изменения удалены.");
            masterBot.currentSessions.remove(update.getMessage().getFrom().getUserName());
            masterBot.lastMessage = sendMessage;
            masterBot.execute(sendMessage);
            return;
        }
        sendMessage.setText(s);
        masterBot.lastMessage = sendMessage;
        masterBot.execute(sendMessage);
    }
}
