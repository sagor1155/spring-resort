package edu.miu.cs.cs544.service.util;

import edu.miu.cs.cs544.domain.enums.UserType;
import edu.miu.cs.cs544.dto.LoggedInUserDTO;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class SecurityUtils {

    public static LoggedInUserDTO getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String role = authentication.getAuthorities().iterator().next().getAuthority();
            return new LoggedInUserDTO(
                    authentication.getName(),
                    UserType.valueOf(role)
            );
        }
        return null;
    }

    public static String getLoggedInUserName() {
        Optional<LoggedInUserDTO> loggedInUser = Optional.ofNullable(getLoggedInUser());
        if (loggedInUser.isPresent()) {
            return loggedInUser.get().getName();
        }
        return "Undefined";
    }
}
