package edu.miu.cs.cs544.dto;

import edu.miu.cs.cs544.domain.Country;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StateDTO {

    private Integer id;

    private String code;

    private String name;

    private Country country;

    public StateDTO(String code, String name, Country country) {
        this.code = code;
        this.name = name;
        this.country = country;
    }
}
