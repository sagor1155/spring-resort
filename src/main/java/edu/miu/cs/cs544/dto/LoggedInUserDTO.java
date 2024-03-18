package edu.miu.cs.cs544.dto;

import edu.miu.cs.cs544.domain.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoggedInUserDTO {
    private String name;
    private UserType role;
}
