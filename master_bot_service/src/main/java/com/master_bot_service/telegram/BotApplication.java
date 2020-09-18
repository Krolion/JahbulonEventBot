package com.master_bot_service.telegram;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BotApplication {

    @Autowired
    public MasterBot masterBot;

    public BotApplication() {
        System.out.println("Я родился!");
    }
}
