package servicepackage.controller;

import servicepackage.data.Event;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import servicepackage.data.Chat;
import servicepackage.data.Chats;
import servicepackage.data.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        Optional<Event> event = currentEvents.stream().filter(n -> n.participantsChatId == question.participants_chat_id)
                .findFirst();
        if (event.isPresent()) {
            question.setOrgs_chat_id(event.get().orgsChatId);
            unansweredQuestions.add(question);
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Content-Type", "application/json");
            HttpEntity<Question> request = new HttpEntity<Question>(question, httpHeaders);
            restTemplate.postForObject("http://localhost:8086/api/post_question", request, String.class);
            return "На ваш вопрос будет найден ответ в ближайшее время!";
        }
        else {
            return "Ваш чат не участвует ни в одном текущем мероприятии";
        }
    }
}