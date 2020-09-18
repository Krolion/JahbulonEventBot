package dbpackage.entity;

import com.sun.istack.NotNull;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @ManyToOne
    private OrganizersChat organizersChat;
    
    @NotNull
    @ManyToOne
    private ParticipantsChat participantsChat;

    @OneToMany(targetEntity = AnsweredQuestion.class)
    private Set<AnsweredQuestion> answeredQuestion;
    @OneToMany(targetEntity = UnansweredQuestion.class)
    private Set<UnansweredQuestion> unansweredQuestion;

}
