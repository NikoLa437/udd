package com.example.udd.entities.DTO;

import com.example.udd.entities.Qualifications;

import java.util.UUID;

public class ApplicationResponseDTO {
    public String Id;
    public String FirstName;
    public String LastName;
    public com.example.udd.entities.Qualifications Qualifications;
    public String City;

    public String Highlight;

    public ApplicationResponseDTO(String id, String firstName, String lastName, Qualifications qualifications, String city, String highlight) {
        FirstName = firstName;
        LastName = lastName;
        Qualifications = qualifications;
        City = city;
        Id= id;
        Highlight= highlight;
    }
}

