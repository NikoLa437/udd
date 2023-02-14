package com.example.udd.entities.DTO;

import java.util.UUID;

public class GeoLocationSearchDTO {
    public double Radius;
    public UUID CityId;

    public GeoLocationSearchDTO(UUID cityId, double radius) {
        CityId = cityId;
        Radius = radius;
    }
}
