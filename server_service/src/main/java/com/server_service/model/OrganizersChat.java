package com.server_service.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrganizersChat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    private Long orgChatId;

    //@OneToOne(fetch = FetchType.LAZY)
    @OneToOne(mappedBy = "organizersChat")
    private Event event;
}
