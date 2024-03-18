package edu.miu.cs.cs544.service;

import edu.miu.cs.cs544.domain.Country;
import edu.miu.cs.cs544.dto.CountryDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CountryService {
    public CountryDTO addCountry(CountryDTO countryDTO);
    public List<CountryDTO> findAllCountries();
    public CountryDTO findById(Long id);
    public CountryDTO updateCountry(Long id, CountryDTO countryDTO);
    public String deleteById(Long id);
}
