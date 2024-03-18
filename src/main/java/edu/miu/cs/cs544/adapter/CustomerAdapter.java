package edu.miu.cs.cs544.adapter;

import edu.miu.cs.cs544.domain.AuditData;
import edu.miu.cs.cs544.domain.Customer;
import edu.miu.cs.cs544.domain.User;
import edu.miu.cs.cs544.dto.AddressDTO;
import edu.miu.cs.cs544.dto.CustomerDTO;
import edu.miu.cs.cs544.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomerAdapter {

    @Autowired
    UserAdapter userAdapter;
    @Autowired
    AddressAdapter addressAdapter;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public CustomerDTO entityToDTO(Customer customer){
        UserDTO userDTO = userAdapter.entityToDTO(customer.getUser());
        AddressDTO billingDTO = addressAdapter.entityToDTO(customer.getBillingAddress());
        AddressDTO shippingDTO = addressAdapter.entityToDTO(customer.getPhysicalAddress());

        return new CustomerDTO( customer.getFirstName(), customer.getLastName(), customer.getEmail(), userDTO,
                billingDTO,
                shippingDTO
                );
    }

    public List<CustomerDTO> entityToDTOAll(List<Customer> customers){
        return customers.stream().map(customer -> entityToDTO(customer)).collect(Collectors.toList());
    }

    public Customer DtoToEntity(CustomerDTO customerDTO){
        //
        User user =  userAdapter.DtoToEntity(customerDTO.getUserDTO());
        var customer =  new Customer(
                customerDTO.getFirstName(),
                customerDTO.getLastName(),
                customerDTO.getEmail(),
                user,
                addressAdapter.DtoToEntity(customerDTO.getBillingAddress()),
                addressAdapter.DtoToEntity(customerDTO.getPhysicalAddress())
        );
        return customer;
    }

    public List<Customer> DtoToEntityAll(List<CustomerDTO> customerDTOs){
        return customerDTOs.stream().map(customerDTO -> DtoToEntity(customerDTO)).collect(Collectors.toList());
    }


}
