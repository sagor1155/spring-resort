package edu.miu.cs.cs544.controller;

import edu.miu.cs.cs544.domain.Country;
import edu.miu.cs.cs544.dto.CountryDTO;
import edu.miu.cs.cs544.service.CountryService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/countries")
public class CountryController {

    @Autowired
    private CountryService countryService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> addCountry(@RequestBody CountryDTO countryDTO){
        CountryDTO country = countryService.addCountry(countryDTO);
        return new ResponseEntity<>(country, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllCountries(){
        return new ResponseEntity<List<CountryDTO>>(countryService.findAllCountries(), HttpStatus.OK);
    }

    @GetMapping("/{countryId}")
    public ResponseEntity<?> getCountry(@PathVariable Long countryId){
        return new ResponseEntity<CountryDTO>(countryService.findById(countryId), HttpStatus.OK);
    }

    @PutMapping("/{countryId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> updateCountry(@PathVariable("countryId") Long countryId, @RequestBody CountryDTO countryDTO){
        CountryDTO countryDTOResponse = countryService.updateCountry(countryId, countryDTO);
        return new ResponseEntity<CountryDTO>(countryDTOResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{countryId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteCountry(@PathVariable Long countryId){
        return new ResponseEntity<String>(countryService.deleteById(countryId), HttpStatus.OK);
    }
}
