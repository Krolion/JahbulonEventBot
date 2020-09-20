package com.participants_bot_service.telegram.handler;

import com.participants_bot_service.data.Chat;
import com.participants_bot_service.telegram.QASlavePBot;
import com.participants_bot_service.utils.Poster;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class IsNewChatMemberHandler implements MessageHandler {
    @Override
    public void handle(Update update, QASlavePBot qaSlavePBot) {
        SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());
        sendMessage.setText("Я не добавился, сорян.");
        String s = (String) Poster.builder().aClassObject(Chat.class)
                .aClassReturn(String.class)
                .object(Chat.builder()
                        .chat_id(update.getMessage().getChatId())
                        .build())
                .url(qaSlavePBot.server + "new_participants_chat")
                .build().post();
        sendMessage.setText(s);
        qaSlavePBot.lastMessage = sendMessage;
        try {
            qaSlavePBot.execute(sendMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
