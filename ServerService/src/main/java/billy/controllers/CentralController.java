package billy.controllers;


import billy.data.Chat;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/central/")
public class CentralController {

    private final List<Chat> myPChats = new ArrayList<Chat>();
    private final List<Chat> myOChats = new ArrayList<Chat>();

    @GetMapping("hello")
    public String hello(){
        return "Hi, i'm a CentralController";
    }

    @PostMapping("new_participants_chat")
    public @ResponseBody String newParticipantsChat(@RequestBody Chat chat) {
        myPChats.add(chat);
        System.out.println("Я добавил неоргов");
        return "It's all good";
    }

    @PostMapping("new_orgs_chat")
    public @ResponseBody String newOrgsChat(@RequestBody Chat chat) {
        myOChats.add(chat);
        System.out.println("Я добавил оргов");
        return "Всем привет! Я добавился.";
    }

    @GetMapping("pchats")
    public String printPChats() {
        return myPChats.toString();
    }

    @GetMapping("ochats")
    public String printOChats() {
        return myOChats.toString();
    }
}
