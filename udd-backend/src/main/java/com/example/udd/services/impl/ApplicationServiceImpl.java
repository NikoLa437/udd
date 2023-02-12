package com.example.udd.services.impl;

import com.example.udd.entities.City;
import com.example.udd.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationServiceImpl {
    @Autowired
    private CityRepository cityRepository;

    public List<City> getCities() {
        return cityRepository.findAll();
    }
}
