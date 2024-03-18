package edu.miu.cs.cs544.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CountryDTO {
    private Long id;

    private String code;

    private String name;

    private Integer population;

    public CountryDTO(Long id) {
        this.id = id;
    }

    public CountryDTO(String code, String name, Integer population) {
        this.code = code;
        this.name = name;
        this.population = population;
    }

    public CountryDTO(Long id, String code, String name, Integer population) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.population = population;
    }
}
