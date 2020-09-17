package billy.controllers;

import billy.model.telegram.QAMasterBot;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Evgeny Borisov
 */
@RestController
@RequestMapping("/api/bot/")
public class MasterBotController {

    @Autowired
    public QAMasterBot qaMasterBot;

    @GetMapping("hello")
    public String hello(){
        return "Hi, i'm a BotController";
    }


}





