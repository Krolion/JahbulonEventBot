package com.server_service.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@NotNull
    /*
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId*/
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "organizersChat_id")
    private OrganizersChat organizersChat;
    
    //@NotNull
    /*
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId*/
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "participantsChat_id")
    private ParticipantsChat participantsChat;

    @OneToMany(
            mappedBy = "event",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<AnsweredQuestion> answeredQuestion;

    @OneToMany(
            mappedBy = "event",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<UnansweredQuestion> unansweredQuestion;

    private String welcomeMessage;
    private String htmlGoogleCalendarLink;
}
