package com.participants_bot_service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.participants_bot_service.data.QuestionWithAnswer;
import com.participants_bot_service.telegram.QASlavePBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@RestController
@RequestMapping("/api/")
public class QASlavePBotController {

    @Autowired
    public QASlavePBot qaSlavePBot;

    @GetMapping("hello")
    public String hello(){
        return "Hi, i'm a QASlavePBotController";
    }

    @GetMapping("send")
    public String send_message() {
        return "i'm working on it";
    }

    @GetMapping("last_update")
    public String last_update() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(qaSlavePBot.lastUpdate);
    }

    @GetMapping("last_message")
    public String last_message() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(qaSlavePBot.lastMessage);
    }

    @PostMapping("write_answer_to_participants_chat")
    public @ResponseBody
    String putQuestion(@RequestBody QuestionWithAnswer questionWithAnswer) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(questionWithAnswer.answer)
                .setChatId(questionWithAnswer.question.participants_chat_id)
                .setReplyToMessageId((int) questionWithAnswer.question.message_id);
        this.qaSlavePBot.sendMessage(sendMessage);
        return "200";
    }
}
