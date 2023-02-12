package com.example.udd.entities;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Application {
    @Id
    @Column(name = "id")
    private @Setter(AccessLevel.PRIVATE) UUID id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @ManyToOne
    private City city;

    @Enumerated(EnumType.STRING)
    private Qualifications qualifications;

    @OneToOne(cascade = CascadeType.ALL)
    private CV cv;

    @OneToOne(cascade = CascadeType.ALL)
    private CoverLetter coverLetter;

    public Application(String firstName, String lastName, City city, Qualifications qualifications, CV cv, CoverLetter coverLetter) {
        this(UUID.randomUUID(), firstName, lastName, city, qualifications, cv, coverLetter);
    }
}
