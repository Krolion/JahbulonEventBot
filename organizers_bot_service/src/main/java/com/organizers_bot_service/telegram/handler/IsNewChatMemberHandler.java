package com.organizers_bot_service.telegram.handler;

import com.organizers_bot_service.data.Chat;
import com.organizers_bot_service.telegram.QASlaveOBot;
import com.organizers_bot_service.utils.Poster;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class IsNewChatMemberHandler implements MessageHandler {
    @Override
    public void handle(Update update, QASlaveOBot qaSlaveOBot) {
        SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());
        sendMessage.setText("Я не добавился, сорян.");
        String s = (String) Poster.builder().aClassObject(Chat.class)
                .aClassReturn(String.class)
                .object(Chat.builder()
                        .chat_id(update.getMessage().getChatId())
                        .build())
                .url(qaSlaveOBot.server + "new_orgs_chat")
                .build().post();
        sendMessage.setText(s);
        qaSlaveOBot.lastMessage = sendMessage;
        try {
            qaSlaveOBot.execute(sendMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
