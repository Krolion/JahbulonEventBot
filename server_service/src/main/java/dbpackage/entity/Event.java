package dbpackage.entity;

import lombok.*;
import java.util.Set;
import javax.persistence.*;



@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event {

    @Id
    @GeneratedValue
    private int id;

    private int participationChatId;
    private int orgChatId;

    @OneToMany(mappedBy = "answeredQuestion")
    private Set<AnsweredQuestion> answeredQuestion;
    @OneToMany(mappedBy = "unansweredQuestion")
    private Set<UnansweredQuestion> unansweredQuestion;

}
