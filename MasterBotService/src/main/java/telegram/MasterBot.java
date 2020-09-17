package telegram;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.inject.Singleton;

@Component
@Singleton
public class MasterBot extends TelegramLongPollingBot {

    public SendMessage lastMessage = null;
    public int a = 0;

    @Override
    public void onUpdateReceived(Update update) {
        System.out.println(update.getMessage().getFrom().getFirstName()
                + " " + update.getMessage().getText());

        SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());
        sendMessage.setText("Hello " + update.getMessage().getFrom().getFirstName() + " " + a);
        lastMessage = sendMessage;
        a += 1;

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage() {
        try {
            lastMessage.setText("Кто-то меня потрогал!");
            execute(lastMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "test25674Bot";
    }

    @Override
    public String getBotToken() {
        return "1386895726:AAHfFueXGvqAwouqs2XbN5I6mlUHKV8ZzG0";
    }
}
