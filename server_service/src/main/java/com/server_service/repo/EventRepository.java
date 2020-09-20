package com.server_service.repo;

import com.server_service.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    //Optional<Event> findById(Long id);

}
