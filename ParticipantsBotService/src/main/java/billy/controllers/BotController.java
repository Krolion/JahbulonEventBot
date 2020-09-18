package billy.controllers;

import billy.data.QuestionWithAnswer;
import billy.model.telegram.QASlavePBot;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

/**
 * @author Evgeny Borisov
 */
@RestController
@RequestMapping("/api/")
public class BotController {

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

    @PostMapping("put_question")
    public @ResponseBody String putQuestion(@RequestBody QuestionWithAnswer questionWithAnswer) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(questionWithAnswer.answer)
                .setChatId(questionWithAnswer.question.participants_chat_id)
                .setReplyToMessageId((int) questionWithAnswer.question.message_id);
        this.qaSlavePBot.sendMessage(sendMessage);
        return "200";
    }
}





