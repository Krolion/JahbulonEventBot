package dbpackage.repo;

import dbpackage.entity.AnsweredQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface AnsweredQuestionRepository  extends JpaRepository<AnsweredQuestion, Integer> {

}