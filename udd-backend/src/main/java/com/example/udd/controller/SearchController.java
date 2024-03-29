package com.example.udd.controller;

import com.example.udd.entities.DTO.ApplicationInputDTO;
import com.example.udd.entities.DTO.GeoLocationSearchDTO;
import com.example.udd.entities.DTO.Query;
import com.example.udd.entities.Qualifications;
import com.example.udd.services.impl.ApplicationServiceImpl;
import com.example.udd.services.impl.SearchServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "search")
public class SearchController {
    @Autowired
    private SearchServiceImpl searchService;
    @PostMapping("/complex")
    @CrossOrigin
    public ResponseEntity<?> complexSearch(@RequestBody List<Query> complexQuery){
        try {
            return new ResponseEntity<>(searchService.complexSearch(complexQuery), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/geolocation")
    @CrossOrigin
    public ResponseEntity<?> geoLocationSearch(@RequestBody GeoLocationSearchDTO geoLocationSearchDTO){
        try {
            return new ResponseEntity<>(searchService.geoLocationSearch(geoLocationSearchDTO), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
