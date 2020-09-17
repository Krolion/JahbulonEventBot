package dbpackage.entity;

import lombok.*;
import javax.persistence.*;



@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UnansweredQuestion {

    @Id
    @GeneratedValue
    private int id;

    /*
    @Required
    @NotNull*/
    @ManyToOne
    private Event unansweredQuestion;

    private String questionText;

}
