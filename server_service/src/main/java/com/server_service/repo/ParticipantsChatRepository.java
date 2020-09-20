package com.server_service.repo;

import com.server_service.model.ParticipantsChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantsChatRepository extends JpaRepository<ParticipantsChat, Long> {

}