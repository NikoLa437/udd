package com.example.udd.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "application")
public class ApplicationController {
    @GetMapping("/cities")
    @CrossOrigin
    public ResponseEntity<?> findAllCities(){
        try {
            return new ResponseEntity<>("Cities",HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/qualifications")
    @CrossOrigin
    public ResponseEntity<?> findAllQualificationLevels(){
        try {
            return new ResponseEntity<>("qualification", HttpStatus.OK);
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
