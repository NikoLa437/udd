package com.example.udd.controller;

import com.example.udd.entities.Qualifications;
import com.example.udd.services.impl.ApplicationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.EnumSet;
import java.util.List;
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
    public ResponseEntity<?> applyForJob(@RequestParam("Name") String name){
        try {
            return new ResponseEntity<>(name, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
