package billy.controllers;

import billy.data.Question;
import billy.model.telegram.QASlaveOBot;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.ArrayList;

/**
 * @author Evgeny Borisov
 */
@RestController
@RequestMapping("/api/")
public class BotController {

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
    public void redditAMA(@RequestBody Question question) {
        SendMessage sendMessage = new SendMessage();
        this.qaSlaveOBot.activeQuestions.add(question);
        this.qaSlaveOBot.myQuestions.put(question.text, question);
        this.qaSlaveOBot.sendMessage(sendMessage.setChatId(question.orgs_chat_id).setText(question.text));
    }


}





