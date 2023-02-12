package com.example.udd.entities.DTO;

import com.example.udd.entities.Qualifications;

import java.util.UUID;

public class ApplicationInputDTO {
    public String FirstName;
    public String LastName;
    public Qualifications Qualifications;
    public UUID CityId;

    public String Username;
    public String Password;
    public String Email;
    public String Address;
    public String PhoneNumber;

    public ApplicationInputDTO(String firstName, String lastName, com.example.udd.entities.Qualifications qualifications, UUID cityId,
                               String userName, String password, String email, String address, String phoneNumber) {
        FirstName = firstName;
        LastName = lastName;
        Qualifications = qualifications;
        CityId = cityId;
        Username= userName;
        Password=password;
        Email=email;
        Address=address;
        PhoneNumber=phoneNumber;
    }
}
