package com.organizers_bot_service.telegram.handler;

import com.organizers_bot_service.data.QuestionWithAnswer;
import com.organizers_bot_service.telegram.QASlaveOBot;
import com.organizers_bot_service.utils.Poster;
import lombok.SneakyThrows;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class HasAnswerFinalCommandHandler implements MessageHandler {
    @Override
    @SneakyThrows
    public void handle(Update update, QASlaveOBot qaSlaveOBot) {
        SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());
        if (update.getMessage().isReply()) {
            if (qaSlaveOBot.myQuestions.containsKey(update.getMessage().getReplyToMessage().getText())) {
                QuestionWithAnswer questionWithAnswer = QuestionWithAnswer.builder()
                        .question(qaSlaveOBot.myQuestions.get(update.getMessage().getReplyToMessage().getText()))
                        .answer(qaSlaveOBot.answerCommandParser.text).build();
                String s = (String) Poster.builder().aClassObject(QuestionWithAnswer.class)
                        .aClassReturn(String.class)
                        .object(questionWithAnswer)
                        .url("http://localhost:8084/api/save_question_with_answer")
                        .build().post();
                sendMessage.setText(s + update.getMessage().getFrom().getUserName() + ".");
            }
        }
        else {
            sendMessage.setText("Воспользуйтесь функцией Reply для ответа на вопрос.");
        }
        qaSlaveOBot.lastMessage = sendMessage;
        qaSlaveOBot.execute(sendMessage);
    }
}
