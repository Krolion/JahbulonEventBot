package billy.model.telegram;

import billy.data.Chat;
import billy.data.Question;
import billy.model.telegram.parsers.UserMessageParser;
import lombok.SneakyThrows;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.inject.Singleton;

@Component
@Singleton
public class QASlavePBot extends TelegramLongPollingBot {

    private final String server = "http://localhost:8084/api/"; //TODO Удалить это и написать нормально
    public SendMessage lastMessage = null;
    public Object lastUpdate;
    private final UserMessageParser questionCommandParser = new UserMessageParser("question");

    @Override
    @SneakyThrows
    public void onUpdateReceived(Update update) {
        lastUpdate = update;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "application/json");
        boolean flag = false;
        try {
            if (update.getMessage().getNewChatMembers().stream().anyMatch(n -> n.getId() == this.getBotId())) {
                flag = true;
            }
            if (!flag) { flag = update.getMessage().getGroupchatCreated(); }
        } catch (Exception ignored) {} //TODO Написать это через Optional
        if (flag) {
            // Логика если бота добавили в группу или создали группу с ним
            SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());
            sendMessage.setText("Я не добавился, сорян.");
            HttpEntity<Chat> request = new HttpEntity<Chat>(Chat.builder()
                    .chat_id(update.getMessage().getChatId())
                    .build(), httpHeaders);
            String s = restTemplate.postForObject(server + "new_participants_chat", request, String.class);
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
            flag = questionCommandParser.parseMessage(update.getMessage().getText()).isCommand;
        } catch (Exception ignored) {} //TODO Написать это через Optional
        if (flag) {
            // Логика если есть команда /question в начале
            SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());
            sendMessage.setText("Вопрос не был загружен");
            HttpEntity<Question> request = new HttpEntity<Question>(
                    Question.builder().chat_id(update.getMessage().getChatId())
                    .message_id(update.getMessage().getMessageId())
                    .text(questionCommandParser.text).build(), httpHeaders);
            String s = restTemplate.postForObject(server + "new_question", request, String.class);
            sendMessage.setText(s);
            lastMessage = sendMessage;
            execute(sendMessage);
        }
    }

    public void sendMessage(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "test25674Bot";
    } //TODO Спрятать это в xml-файл

    public int getBotId() {
        return 1386895726;
    } //TODO Спрятать это в xml-файл

    @Override
    public String getBotToken() {
        return "1386895726:AAHfFueXGvqAwouqs2XbN5I6mlUHKV8ZzG0";
    } //TODO Спрятать это в xml-файл
}
