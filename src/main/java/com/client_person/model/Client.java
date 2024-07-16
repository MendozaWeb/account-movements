package com.client_person.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String clientId;

    private String password;

    private boolean status;

    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;


}

