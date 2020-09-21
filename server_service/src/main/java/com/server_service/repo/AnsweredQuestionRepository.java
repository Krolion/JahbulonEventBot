package com.server_service.repo;

import com.server_service.model.AnsweredQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface AnsweredQuestionRepository  extends JpaRepository<AnsweredQuestion, Long> {
    AnsweredQuestion findByQuestionText(String text);
}