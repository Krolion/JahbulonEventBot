package dbpackage.entity;

import com.sun.istack.NotNull;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @NotNull
    private String questionText;
    private String answerText;

}
