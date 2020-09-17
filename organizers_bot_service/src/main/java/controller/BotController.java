package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import telegram.OrganizersBot;

@RestController
@RequestMapping("/api/")
public class BotController {

    @Autowired
    public OrganizersBot organizersBot;

    @GetMapping("hello")
    public String hello(){
        return "Hi, i'm a BotController";
    }

    @GetMapping("send")
    public String send_message() {
        organizersBot.sendMessage();
        return "i'm working on it";
    }

    @GetMapping("last_update")
    public String last_update_o() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(organizersBot.lastUpdate);
    }

    @GetMapping("last_message")
    public String last_message() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(organizersBot.lastMessage);
    }
}
