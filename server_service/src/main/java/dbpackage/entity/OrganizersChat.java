package dbpackage.entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrganizersChat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

/*    @NotNull
    @Column(unique = true)
    private int orgChatId; */

}
