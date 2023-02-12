package com.example.udd.entities.DTO;

import com.example.udd.entities.Qualifications;

import java.util.UUID;

public class ApplicationInputDTO {
    public String FirstName;
    public String LastName;
    public Qualifications Qualifications;
    public UUID CityId;

    public ApplicationInputDTO(String firstName, String lastName, com.example.udd.entities.Qualifications qualifications, UUID cityId) {
        FirstName = firstName;
        LastName = lastName;
        Qualifications = qualifications;
        CityId = cityId;
    }
}
