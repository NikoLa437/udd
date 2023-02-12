package com.example.udd.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CoverLetter {
    @Id
    @Column(name = "id")
    private @Setter(AccessLevel.PRIVATE) UUID id;

    @Column(nullable = false)
    private String cvSourcePath;

    public CoverLetter(String sourcePath) {
        this(UUID.randomUUID(), sourcePath);
    }
}
