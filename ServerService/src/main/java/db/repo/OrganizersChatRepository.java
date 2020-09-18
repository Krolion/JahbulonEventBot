package db.repo;


import db.entity.OrganizersChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizersChatRepository extends JpaRepository<OrganizersChat, Integer> {

}
