package com.server_service.controller;

import com.server_service.google_calendar_package.CalendarQuickstart;
import com.server_service.model.AnsweredQuestion;
import com.server_service.model.ParticipantsChat;
import com.server_service.model.UnansweredQuestion;
import com.server_service.repo.*;
import com.server_service.service.converter.DBConverter;
import com.server_service.service.data.*;
import com.server_service.service.utils.Poster;
import net.ricecode.similarity.JaroWinklerStrategy;
import net.ricecode.similarity.SimilarityStrategy;
import net.ricecode.similarity.StringSimilarityService;
import net.ricecode.similarity.StringSimilarityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/")
public class CentralController {

    @Autowired
    private DBConverter dbConverter;

    @Autowired
    private AnsweredQuestionRepository answeredQuestionRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private OrganizersChatRepository organizersChatRepository;
    @Autowired
    private ParticipantsChatRepository participantsChatRepository;
    @Autowired
    private UnansweredQuestionRepository unansweredQuestionRepository;
/*
    //TODO Сменить все листы на рабочие базы данных
    private final List<Chat> myPChats = new ArrayList<Chat>();
    private final List<Chat> myOChats = new ArrayList<Chat>();
    private final List<Event> currentEvents = new ArrayList<Event>();
    private final List<Question> unansweredQuestions = new ArrayList<Question>();
    private final List<QuestionWithAnswer> answeredQuestions = new ArrayList<QuestionWithAnswer>();*/

    @GetMapping("hello")
    public String hello(){
        return "Hi, i'm a CentralController";
    }

    @PostMapping("check_participants_chat_id")
    public @ResponseBody boolean check_participants_chat_id(@RequestBody int participants_chat_id) {
        return participantsChatRepository.findAll().stream().anyMatch(x -> x.getParticipationChatId() == participants_chat_id);
    }

    @PostMapping("check_orgs_chat_id")
    public @ResponseBody boolean check_orgs_chat_id(@RequestBody int participants_chat_id) {
        return organizersChatRepository.findAll().stream().anyMatch(x -> x.getOrgChatId() == participants_chat_id);
    }

    @PostMapping("new_participants_chat")
    public @ResponseBody String newParticipantsChat(@RequestBody Chat chat) {
        participantsChatRepository.save(dbConverter.toParticipantsChat(chat));
        return "Всем привет! Я добавился. ID чата в системе: " + chat.getChat_id();
    }

    @PostMapping("new_orgs_chat")
    public @ResponseBody String newOrgsChat(@RequestBody Chat chat) {
        organizersChatRepository.save(dbConverter.toOrganizersChat(chat));
        return "Всем привет! Я добавился. ID чата в системе: " + chat.getChat_id();
    }

    @PostMapping("new_event")
    public @ResponseBody String setEvent(@RequestBody Event event) {

        boolean havePChat =
                participantsChatRepository
                        .findByParticipationChatId(event.getParticipantsChatId()) != null;

        boolean haveOChat =
                organizersChatRepository
                        .findByOrgChatId(event.getOrgsChatId()) != null;

        if (haveOChat & havePChat) {
            eventRepository.save(dbConverter.toEvent(event));
            return "Добавлено новое мероприятие. Ссылка на мероприятие в Google Calendar: " + calendarQuickstart.GenerateEventLink();
        }
        else {
            String message = "";
            if (!haveOChat) message += "participantsChatId ";
            if (!havePChat) message += "orgsChatId ";
            return "(" + message + ")" + " не обнаружены";
        }
    }

    @GetMapping("aq")
    public @ResponseBody List<QuestionWithAnswer> getQuestionWithAnswer() {

        var questions = answeredQuestionRepository.findAll();
        var result = new ArrayList<QuestionWithAnswer>();

        for (com.server_service.model.AnsweredQuestion item : questions) {
            var q = answeredQuestionRepository.findByQuestionText(item.getQuestionText());
            var question = Question.builder()
                    .text(q.getQuestionText())
                    //.participants_chat_id(item.getEvent().getParticipantsChat().getParticipationChatId())
                    //.orgs_chat_id(item.getEvent().getOrganizersChat().getOrgChatId())
                            .build();

            result.add(QuestionWithAnswer.builder()
                    .question(question)
                    .answer(item.getAnswerText())
                    .build());
        }

        return result;
    }

    @GetMapping("pchats")
    public @ResponseBody Chats getPChats() {

        var chats = participantsChatRepository.findAll();
        var result = new ArrayList<Chat>();

        for (com.server_service.model.ParticipantsChat item : chats) {

            result.add(Chat.builder()
                    .chat_id(item.getParticipationChatId())
                    .build());
        }

        return Chats.builder().chats(result).build();
    }

    @GetMapping("ochats")
    public @ResponseBody Chats getOChats() {

        var chats = organizersChatRepository.findAll();
        var result = new ArrayList<Chat>();

        for (com.server_service.model.OrganizersChat item : chats) {

            result.add(Chat.builder()
                    .chat_id(item.getOrgChatId())
                    .build());
        }

        return Chats.builder().chats(result).build();
    }

    @GetMapping("cevents")
    public @ResponseBody List<Event> getCurrentEvents() {

        var events = eventRepository.findAll();
        var result = new ArrayList<Event>();

        for (com.server_service.model.Event item : events) {
            result.add(Event.builder()
                    .eventId(item.getId())
                    .participantsChatId(item.getParticipantsChat().getParticipationChatId())
                    .orgsChatId(item.getOrganizersChat().getOrgChatId())
                    .build());
        }

        return result;
    }

    @GetMapping("uq")
    public @ResponseBody List<Question> getUnansweredQuestions() {
        var questions = unansweredQuestionRepository.findAll();
        var result = new ArrayList<Question>();

        for (UnansweredQuestion item : questions)
        {
            result.add(Question.builder()
                    .message_id(item.getMessageId())
                    .participants_chat_id(item.getEvent().getParticipantsChat().getParticipationChatId())
                    .orgs_chat_id(item.getEvent().getOrganizersChat().getOrgChatId())
                    .text(item.getQuestionText())
                    .build());
        }

        return result;
    }

    @PostMapping("new_question")
    public @ResponseBody String setNewQuestion(@RequestBody Question question) {
        var participantChat = participantsChatRepository.findByParticipationChatId(question.getParticipants_chat_id());

        if (participantChat != null && participantChat.getEvent() != null) {
            var myEvent = participantChat.getEvent();

            SimilarityStrategy similarityStrategy = new JaroWinklerStrategy();
            StringSimilarityService stringSimilarityService = new StringSimilarityServiceImpl(similarityStrategy);
            double maxScore = -1;
            QuestionWithAnswer questionTMP = null;
            var questions = answeredQuestionRepository.findAll();
            for (AnsweredQuestion answeredQuestion : questions) {
                if (maxScore < stringSimilarityService.score(question.text, answeredQuestion.getQuestionText())) {
                    maxScore = stringSimilarityService.score(question.text, answeredQuestion.getQuestionText());
                    var thatQuestion = answeredQuestionRepository.findByQuestionText(question.getText());

                    var secondQuestion = Question.builder()
                            .text(thatQuestion.getQuestionText())
                            //.orgs_chat_id(thatQuestion.getEvent().getOrganizersChat().getOrgChatId())
                            //.participants_chat_id(thatQuestion.getEvent().getParticipantsChat().getParticipationChatId())
                            .message_id(question.getMessage_id())
                            .build();

                    questionTMP = QuestionWithAnswer.builder()
                            .question(secondQuestion)
                            .answer(answeredQuestion.getAnswerText())
                            .build();
                }
            }

            if (maxScore < 0.5) {
                //question.setOrgs_chat_id(event.get().orgsChatId);
                question.setOrgs_chat_id(myEvent.getOrganizersChat().getOrgChatId());
                unansweredQuestionRepository.save(dbConverter.toUnansweredQuestion(question));
                //unansweredQuestions.add(question);

                var s = (String) Poster.builder().aClassObject(Question.class)
                        .aClassReturn(String.class)
                        .object(question)
                        .url("http://localhost:8086/api/post_question")
                        .build().post();
                return s;
            } else {
                return questionTMP.answer;
            }
        } else {
            return "Ваш чат не участвует ни в одном текущем мероприятии.";
        }
    }

    @PostMapping("save_question_with_answer")
    public @ResponseBody String saveQuestionWithAnswer(@RequestBody QuestionWithAnswer questionWithAnswer) {
        var myquestion = unansweredQuestionRepository
                .findByQuestionText(questionWithAnswer.getQuestion().text);

        if (myquestion != null) {
            var answeredQuestion = dbConverter.toAnsweredQuestion(questionWithAnswer);
            answeredQuestionRepository.save(answeredQuestion);

            unansweredQuestionRepository.delete(myquestion);

            var s = (String) Poster.builder().aClassObject(QuestionWithAnswer.class)
                    .aClassReturn(String.class)
                    .object(questionWithAnswer)
                    .url("http://localhost:8085/api/write_answer_to_participants_chat")
                    .build()
                    .post();

            return s;
        }
        else {
            return "Ваш чат не участвует ни в одном текущем мероприятии, ";
        }
    }
/*
    @GetMapping("hell")
    public String hell(){
        var pChat = Chat.builder().chat_id(10).build();
        var oChat = Chat.builder().chat_id(20).build();

        var pc = dbConverter.toParticipantsChat(pChat);
        dbConverter.participantsChatRepository.save(pc);

        var oc = dbConverter.toOrganizersChat(oChat);
        dbConverter.organizersChatRepository.save(oc);

        var event = Event.builder().participantsChatId(10).orgsChatId(20).build();
        var e = dbConverter.toEvent(event);
        //e.setParticipantsChat(null);
        //e.setParticipantsChat(null);
        //e.getParticipantsChat().setEvent(e);
        //e.getOrganizersChat().setEvent(e);
        dbConverter.eventRepository.save(e);

        return "It's fun";
    }*/

    private CalendarQuickstart calendarQuickstart = new CalendarQuickstart();
}
