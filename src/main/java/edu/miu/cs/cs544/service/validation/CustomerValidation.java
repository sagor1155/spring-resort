package edu.miu.cs.cs544.service.validation;

import edu.miu.cs.cs544.domain.Customer;
import edu.miu.cs.cs544.domain.enums.UserType;
import edu.miu.cs.cs544.dto.LoggedInUserDTO;
import edu.miu.cs.cs544.repository.CustomerRepository;
import edu.miu.cs.cs544.service.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerValidation {

    @Autowired
    private CustomerRepository customerRepository;

    public void checkAuthorization(Customer customer) {

        LoggedInUserDTO loggedInUserDTO = SecurityUtils.getLoggedInUser();
        if (loggedInUserDTO == null) {
            throw new IllegalArgumentException("User is not logged in");
        }

        if (loggedInUserDTO.getRole() == UserType.CLIENT && !loggedInUserDTO.getName().equals(customer.getUser().getUserName())) {
            throw new IllegalArgumentException("User is not authorized to perform this action");
        }
    }

    public Customer validateCustomer(String email) {
        return customerRepository.findCustomerByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Customer does not exist"));
    }
}
