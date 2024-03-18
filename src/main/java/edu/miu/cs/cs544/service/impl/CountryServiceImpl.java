package edu.miu.cs.cs544.service.impl;

import edu.miu.cs.cs544.adapter.CountryAdapter;
import edu.miu.cs.cs544.domain.Country;
import edu.miu.cs.cs544.dto.CountryDTO;
import edu.miu.cs.cs544.repository.CountryRepository;
import edu.miu.cs.cs544.service.CountryService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class CountryServiceImpl implements CountryService {

    @Autowired
    private CountryAdapter countryAdapter;

    @Autowired
    private CountryRepository countryRepository;

    @Override
    public CountryDTO addCountry(CountryDTO countryDTO) {
        try {
            Country country = countryRepository.save(countryAdapter.dtoToEntity(countryDTO));
            return countryAdapter.entityToDto(country);
        }catch (RuntimeException e){
            throw new RuntimeException("Failed to add this country");
        }
    }

    @Override
    public List<CountryDTO> findAllCountries() {
        return countryAdapter.entityToDtoAll(countryRepository.findAll());
    }

    @Override
    public CountryDTO findById(Long id) {
        Optional<Country> countryOptional = countryRepository.findById(id);
        Country country = countryOptional.orElseThrow(() -> new EntityNotFoundException("Country with id " + id + " not found"));
        return countryAdapter.entityToDto(country);
    }

    @Override
    public CountryDTO updateCountry(Long id, CountryDTO countryDTO) {
        try {
            Optional<Country> countryOptional = countryRepository.findById(id);
            if (countryOptional.isPresent()) {
                Country country = countryAdapter.dtoToEntity(countryDTO);
                country.setId(id);
                Country countryResponse = countryRepository.save(country);
                return countryAdapter.entityToDto(countryResponse);
            } else {
                throw new EntityNotFoundException("Country with id " + id + " not found");
            }
        }catch (RuntimeException e){
            throw new RuntimeException("Failed to update this country");
        }
    }

    @Override
    public String deleteById(Long id) {
        try {
            countryRepository.deleteById(id);
            return "Country deleted successfully";
        }catch (EntityNotFoundException e){
            throw new EntityNotFoundException("Failed to delete this member");
        }
    }

}
