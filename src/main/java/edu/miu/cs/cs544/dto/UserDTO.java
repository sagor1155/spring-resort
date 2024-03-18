package edu.miu.cs.cs544.dto;

import edu.miu.cs.cs544.domain.enums.UserType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {
    private String userName;

    private String userPass;

    private Boolean active;

    @Enumerated(EnumType.STRING)
    private UserType userType;
}
