package dbpackage.repo;


import dbpackage.entity.UnansweredQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UnansweredQuestionRepository  extends JpaRepository<UnansweredQuestion, Integer> {

}