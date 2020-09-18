package dbpackage.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParticipantsChat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

  /*  @Column(unique = true)
    private int participationChatId; */


}
