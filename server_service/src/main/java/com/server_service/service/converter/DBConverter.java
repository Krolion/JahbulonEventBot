package com.server_service.service.converter;

import com.server_service.model.*;
import com.server_service.repo.EventRepository;
import com.server_service.repo.OrganizersChatRepository;
import com.server_service.repo.ParticipantsChatRepository;
import com.server_service.service.data.Chat;
import com.server_service.service.data.Question;
import com.server_service.service.data.QuestionWithAnswer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DBConverter {
    @Autowired
    public EventRepository eventRepository;
    @Autowired
    public ParticipantsChatRepository participantsChatRepository;
    @Autowired
    public OrganizersChatRepository organizersChatRepository;

    public Event toEvent(com.server_service.service.data.Event event) {
        return Event.builder()
                .participantsChat(participantsChatRepository
                        .findByParticipationChatId(event
                                .getParticipantsChatId()))
                .organizersChat(organizersChatRepository
                        .findByOrgChatId(event
                                .getOrgsChatId()))
                .welcomeMessage(event.getMessage())
                .htmlGoogleCalendarLink(event.getHtmlLink())
                .build();
    }

    public ParticipantsChat toParticipantsChat(Chat chat) {
        return ParticipantsChat.builder().participationChatId(chat.getChat_id()).build();
    }

    public OrganizersChat toOrganizersChat(Chat chat) {
        return OrganizersChat.builder().orgChatId(chat.getChat_id()).build();
    }

    public UnansweredQuestion toUnansweredQuestion(Question question) {
        return UnansweredQuestion.builder()
                .event(eventRepository.findByOrganizersChatIdAndParticipantsChatId(
                                participantsChatRepository
                                        .findByParticipationChatId(question
                                                .participants_chat_id)
                                        .getParticipationChatId(),
                                organizersChatRepository
                                        .findByOrgChatId(question
                                                .orgs_chat_id)
                                        .getOrgChatId()
                        ))
                .questionText(question.text)
                .build();
    }

    public AnsweredQuestion toAnsweredQuestion(QuestionWithAnswer question) {
        return AnsweredQuestion.builder()
                .event(eventRepository.findByOrganizersChatIdAndParticipantsChatId(
                        participantsChatRepository
                                .findByParticipationChatId(question
                                        .getQuestion()
                                        .participants_chat_id)
                                .getParticipationChatId(),
                        organizersChatRepository
                                .findByOrgChatId(question
                                        .getQuestion()
                                        .orgs_chat_id)
                                .getOrgChatId()
                ))
                .questionText(question.getQuestion().text)
                .answerText(question.answer)
                .build();
    }
}
