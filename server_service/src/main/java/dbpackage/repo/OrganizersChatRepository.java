package dbpackage.repo;

import dbpackage.entity.OrganizersChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizersChatRepository extends JpaRepository<OrganizersChat, Integer> {

}
