package billy.controllers;


import billy.data.Chat;
import billy.data.Chats;
import billy.data.Event;
import billy.data.Question;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class CentralController {

    //TODO Сменить все листы на рабочие базы данных
    private final List<Chat> myPChats = new ArrayList<Chat>();
    private final List<Chat> myOChats = new ArrayList<Chat>();
    private final List<Event> currentEvents = new ArrayList<Event>();
    private final List<Question> unansweredQuestions = new ArrayList<Question>();

    @GetMapping("hello")
    public String hello(){
        return "Hi, i'm a CentralController";
    }

    @PostMapping("new_participants_chat")
    public @ResponseBody String newParticipantsChat(@RequestBody Chat chat) {
        myPChats.add(chat);
        System.out.println("Я добавил неоргов"); //TODO Сменить вывод в консоль на более информативные записи в лог
        return "Всем привет! Я добавился.";
    }

    @PostMapping("new_orgs_chat")
    public @ResponseBody String newOrgsChat(@RequestBody Chat chat) {
        myOChats.add(chat);
        System.out.println("Я добавил оргов");
        return "Всем привет! Я добавился.";
    }

    @PostMapping("new_event")
    public @ResponseBody String setEvent(@RequestBody Event event) {
        event.setEventId(currentEvents.size());
        boolean havePChat = myPChats.stream().anyMatch(n -> n.chat_id == event.participantsChatId);
        boolean haveOChat = myOChats.stream().anyMatch(n -> n.chat_id == event.orgsChatId);
        if (haveOChat & havePChat) {
            currentEvents.add(event);
            return "Добавлено новое мероприятие, id - " + (currentEvents.size() - 1);
        }
        else {
            String message = "";
            if (!haveOChat) message += "participantsChatId ";
            if (!havePChat) message += "orgsChatId ";
            return "(" + message + ")" + " не обнаружены";
        }
    }

    @GetMapping("pchats")
    public @ResponseBody Chats getPChats() {
        return Chats.builder().chats(myPChats).build();
    }

    @GetMapping("ochats")
    public @ResponseBody Chats getOChats() {
        return Chats.builder().chats(myOChats).build();
    }

    @GetMapping("cevents")
    public @ResponseBody List<Event> getCurrentEvents() { return currentEvents; }

    @GetMapping("uq")
    public @ResponseBody List<Question> getUnansweredQuestions() { return unansweredQuestions; }

    @PostMapping("new_question")
    public @ResponseBody String setNewQuestion(@RequestBody Question question) {
        unansweredQuestions.add(question);
        return "На ваш вопрос будет найден ответ в ближайшее время!";
    }
}
