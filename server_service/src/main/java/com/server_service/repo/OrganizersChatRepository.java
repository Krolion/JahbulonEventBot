package com.server_service.repo;

import com.server_service.model.OrganizersChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface OrganizersChatRepository extends JpaRepository<OrganizersChat, Long> {

}
