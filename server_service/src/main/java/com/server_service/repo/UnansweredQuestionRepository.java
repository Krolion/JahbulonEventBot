package com.server_service.repo;

import com.server_service.model.UnansweredQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnansweredQuestionRepository  extends JpaRepository<UnansweredQuestion, Long> {
    UnansweredQuestion findByQuestionText(String text);
}