package dbpackage.repo;


import dbpackage.entity.ParticipantsChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantsChatRepository extends JpaRepository<ParticipantsChat, Integer> {

}