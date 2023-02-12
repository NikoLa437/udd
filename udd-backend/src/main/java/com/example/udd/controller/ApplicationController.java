package com.example.udd.controller;

import com.example.udd.entities.DTO.ApplicationInputDTO;
import com.example.udd.entities.Qualifications;
import com.example.udd.services.impl.ApplicationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.EnumSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@RestController
@RequestMapping(value = "application")
public class ApplicationController {

    @Autowired
    private ApplicationServiceImpl applicationService;
    @GetMapping("/cities")
    @CrossOrigin
    public ResponseEntity<?> findAllCities(){
        try {
            return new ResponseEntity<>(applicationService.getCities(),HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private List<String> GetDataSourceTypes()
    {
        List<String> enumNames = Stream.of(Qualifications.values())
                .map(Enum::name)
                .collect(Collectors.toList());

        return enumNames;
    }

    @GetMapping("/qualifications")
    @CrossOrigin
    public ResponseEntity<?> findAllQualificationLevels(){
        try {

            return new ResponseEntity<>(this.GetDataSourceTypes(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    @CrossOrigin
    public ResponseEntity<?> applyForJob(@RequestParam("FirstName") String firstName, @RequestParam("LastName") String lastName,
                                         @RequestParam("Qualifications") Qualifications qualifications, @RequestParam("CityId") UUID cityId,
                                         @RequestParam("CV") MultipartFile cv, @RequestParam("CL") MultipartFile cl){
        try {
            ApplicationInputDTO dto = new ApplicationInputDTO(firstName, lastName, qualifications, cityId);
            return new ResponseEntity<>(applicationService.createApplication(dto, cv, cl), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
