package com.master_bot_service.telegram.handler;

import com.master_bot_service.data.Chats;
import com.master_bot_service.telegram.MasterBot;
import lombok.SneakyThrows;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;


public class HasChatListCommandHandler implements MessageHandler {
    @Override
    @SneakyThrows
    public void handle(Update update, MasterBot masterBot) {
        SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());
        RestTemplate restTemplate = new RestTemplate();
        Chats pChats = restTemplate.getForObject(masterBot.server + "pchats", Chats.class);
        Chats oChats = restTemplate.getForObject(masterBot.server + "ochats", Chats.class);
        assert oChats != null;
        assert pChats != null;
        sendMessage.setText("Participants chats \n" + pChats.chats.toString()
                + "\nOrgs chat\n" + oChats.chats.toString());
        masterBot.lastMessage = sendMessage;
        masterBot.execute(sendMessage);
    }
}
