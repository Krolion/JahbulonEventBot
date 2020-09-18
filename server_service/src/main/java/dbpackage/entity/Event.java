package dbpackage.entity;

import com.sun.istack.NotNull;
import lombok.*;

import java.util.List;
import java.util.Set;
import javax.persistence.*;

import static javax.persistence.CascadeType.ALL;


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
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AnsweredQuestion> answeredQuestion;

    @NotNull
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UnansweredQuestion> unansweredQuestion;

    @NotNull
    @OneToOne(cascade = ALL, fetch = FetchType.EAGER)
    private OrganizersChat organizersChat;

    @NotNull
    @OneToOne(cascade = ALL, fetch = FetchType.EAGER)
    private ParticipantsChat participantsChat;

}
