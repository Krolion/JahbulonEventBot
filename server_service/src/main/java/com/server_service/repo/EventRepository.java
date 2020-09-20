package com.server_service.repo;

import com.server_service.model.Event;
import com.server_service.model.OrganizersChat;
import com.server_service.model.ParticipantsChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    Event findByOrganizersChatIdAndParticipantsChatId(Long organizersChatId, Long participantsChatId);

}
