package edu.miu.cs.cs544.service;

import edu.miu.cs.cs544.domain.User;
import edu.miu.cs.cs544.domain.enums.UserType;
import edu.miu.cs.cs544.dto.LoggedInUserDTO;
import edu.miu.cs.cs544.dto.ResponseDto;
import edu.miu.cs.cs544.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public ResponseDto createUser(User user) {
        try {
            Optional<User> userInDb = userRepository.finByUsername(user.getUserName());
            if (userInDb.isPresent()) {
                return new ResponseDto(false, "User already exists!", user.getUserName());
            }
            User userResponse  = userRepository.save(user);
            return new ResponseDto(true, "Created User successfully", userResponse.getUserName());
        } catch (Exception ex) {
            System.out.println("[Exception] " + ex.getMessage());
            return new ResponseDto(false, ex.getMessage(), null);
        }
    }

    public LoggedInUserDTO getLoggedInUser() {
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
}
