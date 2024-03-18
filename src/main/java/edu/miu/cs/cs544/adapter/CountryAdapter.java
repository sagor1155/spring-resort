package edu.miu.cs.cs544.adapter;

import edu.miu.cs.cs544.domain.Country;
import edu.miu.cs.cs544.dto.CountryDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CountryAdapter {
    public CountryDTO entityToDto(Country country){
        return new CountryDTO(
                country.getId(),
                country.getCode(),
                country.getName(),
                country.getPopulation()
        );
    }
    public List<CountryDTO> entityToDtoAll(List<Country> countries){
        return countries.stream().map(this::entityToDto).collect(Collectors.toList());
    }
    public Country dtoToEntity(CountryDTO countryDTO){
        return new Country(
                countryDTO.getCode(),
                countryDTO.getName(),
                countryDTO.getPopulation()
        );
    }
    public List<Country> dtoToEntityAll(List<CountryDTO> countryDTOS){
        return countryDTOS.stream().map(this::dtoToEntity).collect(Collectors.toList());
    }
}
