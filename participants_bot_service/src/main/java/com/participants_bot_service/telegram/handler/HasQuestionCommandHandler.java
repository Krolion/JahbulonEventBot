package com.participants_bot_service.telegram.handler;

import com.participants_bot_service.data.Question;
import com.participants_bot_service.telegram.QASlavePBot;
import com.participants_bot_service.utils.Poster;
import lombok.SneakyThrows;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class HasQuestionCommandHandler implements MessageHandler {
    @Override
    @SneakyThrows
    public void handle(Update update, QASlavePBot qaSlavePBot) {
        SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());
        sendMessage.setText("Вопрос не был загружен");
        String s = (String) Poster.builder().aClassObject(Question.class)
                .aClassReturn(String.class)
                .object(Question.builder().participants_chat_id(update.getMessage().getChatId())
                        .orgs_chat_id(0)
                        .message_id(update.getMessage().getMessageId())
                        .text(qaSlavePBot.questionCommandParser.text).build())
                .url(qaSlavePBot.server + "new_question")
                .build().post();
        sendMessage.setText(s);
        qaSlavePBot.lastMessage = sendMessage;
        qaSlavePBot.execute(sendMessage);
    }
}
