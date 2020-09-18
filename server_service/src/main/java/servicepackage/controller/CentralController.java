package servicepackage.controller;

import dbpackage.entity.Event;
import dbpackage.entity.OrganizersChat;
import dbpackage.entity.ParticipantsChat;
import dbpackage.entity.UnansweredQuestion;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class CentralController {

    //TODO Сменить все листы на рабочие базы данных
    private final List<ParticipantsChat> myPChats = new ArrayList<>();
    private final List<OrganizersChat> myOChats = new ArrayList<>();
    private final List<Event> currentEvents = new ArrayList<>();
    private final List<UnansweredQuestion> unansweredQuestions = new ArrayList<>();

    @GetMapping("hello")
    public String hello(){
        return "Hi, i'm a CentralController";
    }

    @PostMapping("new_participants_chat")
    public @ResponseBody String newParticipantsChat(@RequestBody ParticipantsChat chat) {
        myPChats.add(chat);
        System.out.println("Я добавил неоргов"); //TODO Сменить вывод в консоль на более информативные записи в лог
        return "Всем привет! Я добавился.";
    }

    @PostMapping("new_orgs_chat")
    public @ResponseBody String newOrgsChat(@RequestBody OrganizersChat chat) {
        myOChats.add(chat);
        System.out.println("Я добавил оргов");
        return "Всем привет! Я добавился.";
    }

    @PostMapping("new_event")
    public @ResponseBody String setEvent(@RequestBody Event event) {
        //event.setEventId(currentEvents.size());
        boolean havePChat = myPChats.stream().anyMatch(n -> n == event.getParticipantsChat());
        boolean haveOChat = myOChats.stream().anyMatch(n -> n == event.getOrganizersChat());
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
    public @ResponseBody ParticipantsChat getPChats() {
        return ParticipantsChat.builder().build();//TODO: .chats(myPChats).build();
    }

    @GetMapping("ochats")
    public @ResponseBody OrganizersChat getOChats() {
        return OrganizersChat.builder().build();//TODO: .chats(myOChats).build();
    }

    @GetMapping("cevents")
    public @ResponseBody List<Event> getCurrentEvents() { return currentEvents; }

    @GetMapping("uq")
    public @ResponseBody List<UnansweredQuestion> getUnansweredQuestions() { return unansweredQuestions; }

    @PostMapping("new_question")
    public @ResponseBody String setNewQuestion(@RequestBody UnansweredQuestion question) {
        unansweredQuestions.add(question);
        return "На ваш вопрос будет найден ответ в ближайшее время!";
    }
}
