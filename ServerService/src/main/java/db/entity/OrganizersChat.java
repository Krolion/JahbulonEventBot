package db.entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
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

    @NotNull
    @Column(unique = true)
    private long orgChatId;

    @OneToMany(targetEntity = Event.class)
    private Set<Event> events;
}
