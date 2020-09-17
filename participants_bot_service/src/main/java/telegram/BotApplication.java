package telegram;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BotApplication {

    @Autowired
    public ParticipantsBot participantsBot;

    public BotApplication() {
        System.out.println("Я родился!");
    }
}
