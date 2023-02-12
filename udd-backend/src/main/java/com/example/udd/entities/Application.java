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

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String phoneNumber;
    @ManyToOne
    private City city;

    @Enumerated(EnumType.STRING)
    private Qualifications qualifications;

    @OneToOne(cascade = CascadeType.ALL)
    private CV cv;

    @OneToOne(cascade = CascadeType.ALL)
    private CoverLetter coverLetter;

    public Application(String firstName, String lastName, City city, Qualifications qualifications, CV cv, CoverLetter coverLetter,
                       String userName, String password, String email, String address, String phoneNumber) {
        this(UUID.randomUUID(), firstName, lastName, userName, email, password, address, phoneNumber, city, qualifications, cv, coverLetter);
    }
}
