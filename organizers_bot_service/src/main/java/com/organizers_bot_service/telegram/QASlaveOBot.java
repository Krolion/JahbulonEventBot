package com.organizers_bot_service.telegram;

import com.organizers_bot_service.data.Chat;
import com.organizers_bot_service.data.Question;
import com.organizers_bot_service.data.QuestionWithAnswer;
import com.organizers_bot_service.telegram.parser.UserMessageParser;
import com.organizers_bot_service.utils.Poster;
import lombok.SneakyThrows;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.HashMap;

@Component
@Singleton
public class QASlaveOBot extends TelegramLongPollingBot {

    private final QASlaveOBotCredentials credentials = new QASlaveOBotCredentials();
    private final String server = "http://localhost:8084/api/"; //TODO Удалить это и написать нормально
    public int a = 0;
    public SendMessage lastMessage;
    public Update lastUpdate;
    public ArrayList<Question> activeQuestions = new ArrayList<Question>();
    public HashMap<String, Question> myQuestions = new HashMap<String, Question>();
    private final UserMessageParser answerCommandParser = new UserMessageParser("answer");
    private final UserMessageParser answerFinalCommandParser = new UserMessageParser("answer_final");


    @Override
    @SneakyThrows
    public void onUpdateReceived(Update update) {
        lastUpdate = update;
        boolean flag = false;
        try {
            if (update.getMessage().getNewChatMembers().stream().anyMatch(n -> n.getUserName().equals(this.getBotUsername()))) {
                flag = true;
            }
            if (!flag) { flag = update.getMessage().getGroupchatCreated(); }
        } catch (Exception ignored) {}
        if (flag) {
            // Логика если бота добавили в группу или создали группу с ним
            SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());
            sendMessage.setText("Я не добавился, сорян.");
            String s = (String) Poster.builder().aClassObject(Chat.class)
                    .aClassReturn(String.class)
                    .object(Chat.builder()
                            .chat_id(update.getMessage().getChatId())
                            .build())
                    .url(server + "new_orgs_chat")
                    .build().post();
            sendMessage.setText(s);
            lastMessage = sendMessage;
            try {
                execute(sendMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
        try {
            flag = answerFinalCommandParser.parseMessage(update.getMessage().getText()).isCommand;
        } catch (Exception ignored) {}
        if (flag) {
            // Логика если есть команда /answer в начале
            SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());
            if (update.getMessage().isReply()) {
                if (myQuestions.containsKey(update.getMessage().getReplyToMessage().getText())) {
                    QuestionWithAnswer questionWithAnswer = QuestionWithAnswer.builder()
                            .question(myQuestions.get(update.getMessage().getReplyToMessage().getText()))
                            .answer(answerCommandParser.text).build();
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
            lastMessage = sendMessage;
            execute(sendMessage);
            return;
        }
        try {
            flag = answerCommandParser.parseMessage(update.getMessage().getText()).isCommand;
        } catch (Exception ignored) {}
        if (flag) {
            // Логика если есть команда /answer_final в начале
            SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());
            if (update.getMessage().isReply()) {
                if (myQuestions.containsKey(update.getMessage().getReplyToMessage().getText())) {
                    QuestionWithAnswer questionWithAnswer = QuestionWithAnswer.builder()
                            .question(myQuestions.get(update.getMessage().getReplyToMessage().getText()))
                            .answer(answerCommandParser.text).build();
                    String s = (String) Poster.builder().aClassObject(QuestionWithAnswer.class)
                            .aClassReturn(String.class)
                            .object(questionWithAnswer)
                            .url("http://localhost:8085/api/write_answer_to_participants_chat")
                            .build().post();
                    sendMessage.setText(s + update.getMessage().getFrom().getUserName() + ".");
                }
            }
            else {
                sendMessage.setText("Воспользуйтесь функцией Reply для ответа на вопрос.");
            }
            lastMessage = sendMessage;
            execute(sendMessage);
        }
    }

    @SneakyThrows
    public void sendMessage(SendMessage sendMessage) {
        execute(sendMessage);
    }

    @Override
    public String getBotUsername() {
        return credentials.getBotUsername();
    }

    @Override
    public String getBotToken() {
        return credentials.getBotToken();
    }

}
