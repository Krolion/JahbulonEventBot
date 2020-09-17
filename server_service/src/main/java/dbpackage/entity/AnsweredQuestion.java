package dbpackage.entity;

import lombok.*;
import javax.persistence.*;



@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnsweredQuestion{

    @Id
    @GeneratedValue
    private int id;

    /*
    @Required
    */

    @NonNull
    @ManyToOne
    private Event answeredQuestion;

    private String questionText;
    private String answerText;

}
