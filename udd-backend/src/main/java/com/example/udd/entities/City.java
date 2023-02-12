package com.example.udd.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class City {

    @Id
    @Column(name = "id")
    private @Setter(AccessLevel.PRIVATE) UUID id;

    @Column(nullable = false)
    private String name;

    private double latitude;

    private double longitude;
}
