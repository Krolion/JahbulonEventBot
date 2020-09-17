package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import telegram.BotApplication;
import telegram.MasterBot;

@RestController
@RequestMapping("/api/bot/")
public class BotController {

    @Autowired
    public MasterBot masterBot;

    @GetMapping("hello")
    public String hello(){
        return "Hi, i'm a BotController";
    }
}
