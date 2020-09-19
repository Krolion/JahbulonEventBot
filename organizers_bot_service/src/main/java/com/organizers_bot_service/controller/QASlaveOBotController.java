package com.organizers_bot_service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.organizers_bot_service.data.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.organizers_bot_service.telegram.QASlaveOBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@RestController
@RequestMapping("/api/")
public class QASlaveOBotController {

    @Autowired
    public QASlaveOBot qaSlaveOBot;

    @GetMapping("hello")
    public String hello(){
        return "Hi, i'm a QASlaveOBotController";
    }

    @GetMapping("last_update")
    public String last_update() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(qaSlaveOBot.lastUpdate);
    }

    @GetMapping("last_message")
    public String last_message() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(qaSlaveOBot.lastMessage);
    }

    @PostMapping("post_question")
    public @ResponseBody String redditAMA(@RequestBody Question question) {
        SendMessage sendMessage = new SendMessage();
        this.qaSlaveOBot.activeQuestions.add(question);
        this.qaSlaveOBot.myQuestions.put(question.text, question);
        this.qaSlaveOBot.sendMessage(sendMessage.setChatId(question.orgs_chat_id).setText(question.text));
        return "";
    }


}
