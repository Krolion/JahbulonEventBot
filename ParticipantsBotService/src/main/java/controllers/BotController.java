package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import telegram.BotApplication;

@RestController
@RequestMapping("/api/bot/")
public class BotController {

    public BotApplication botApplication;

    public BotController(BotApplication botApplication) {
        this.botApplication = botApplication;
    }

    @GetMapping("hello")
    public String hello(){
        return "Hi, i'm a BotController";
    }

    @GetMapping("send")
    public String send_message() {/*
        botApplication.qaSlaveOBotThread.bot.sendMessage();
        botApplication.qaSlavePBotThread.bot.sendMessage();*/
        return "i'm working on it";
    }

    @GetMapping("last_update")
    public String last_update() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(/*botApplication.qaSlaveOBotThread.bot.lastUpdate*/ null);
    }

    @GetMapping("last_message")
    public String last_message() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(/*botApplication.qaSlaveOBotThread.bot.lastMessage*/ null);
    }
}
