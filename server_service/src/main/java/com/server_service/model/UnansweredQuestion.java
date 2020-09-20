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
public class UnansweredQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Event event;

    @NotNull
    private String questionText;
}
