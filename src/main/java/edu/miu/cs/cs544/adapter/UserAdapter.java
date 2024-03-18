package edu.miu.cs.cs544.adapter;

import edu.miu.cs.cs544.domain.Customer;
import edu.miu.cs.cs544.domain.User;
import edu.miu.cs.cs544.dto.CustomerDTO;
import edu.miu.cs.cs544.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserAdapter {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserDTO entityToDTO(User user){
        return new UserDTO(user.getUserName(), "", user.getActive(), user.getUserType());
    }

    public List<UserDTO> entityToDTOAll(List<User> users){
        return users.stream().map(user -> entityToDTO(user)).collect(Collectors.toList());
    }

    public User DtoToEntity(UserDTO userDTO){
        var password = bCryptPasswordEncoder.encode(userDTO.getUserPass());
        return new User(userDTO.getUserName(), password, userDTO.getActive(), userDTO.getUserType());
    }
    public List<User> DtoToEntityAll(List<UserDTO> userDTOs){
        return userDTOs.stream().map(userDTO -> DtoToEntity(userDTO)).collect(Collectors.toList());
    }
}
