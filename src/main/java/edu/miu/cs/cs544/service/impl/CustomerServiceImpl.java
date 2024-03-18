package edu.miu.cs.cs544.service.impl;

import edu.miu.cs.cs544.adapter.AddressAdapter;
import edu.miu.cs.cs544.adapter.CustomerAdapter;
import edu.miu.cs.cs544.adapter.UserAdapter;
import edu.miu.cs.cs544.config.HibernateUtil;
import edu.miu.cs.cs544.domain.Address;
import edu.miu.cs.cs544.domain.AuditData;
import edu.miu.cs.cs544.domain.User;
import edu.miu.cs.cs544.domain.enums.UserType;
import edu.miu.cs.cs544.dto.AddressDTO;
import edu.miu.cs.cs544.dto.CustomerDTO;
import edu.miu.cs.cs544.dto.LoggedInUserDTO;
import edu.miu.cs.cs544.repository.CustomerRepository;
import edu.miu.cs.cs544.repository.UserRepository;
import edu.miu.cs.cs544.service.CustomerService;
import edu.miu.cs.cs544.service.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerAdapter customerAdapter;
    @Autowired
    UserAdapter userAdapter;

    @Autowired
    AddressAdapter addressAdapter;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    CustomerRepository customerRepository;


    @Autowired
    UserRepository userRepository;

    @Override
    public CustomerDTO addCustomer(CustomerDTO customerDTO) {
        try {
            var existing = customerRepository.findByEmail(customerDTO.getEmail());
            if(existing != null)
            {
                throw new IllegalArgumentException("Customer email already exist");
            }
            var user = userRepository.finByUsername(customerDTO.getUserDTO().getUserName());
            if(user.isPresent())
            {
                throw new IllegalArgumentException("Username already exist");
            }
            //
            var customer = customerAdapter.DtoToEntity(customerDTO);
            customerRepository.save(customer);

            var customerDTOResponse = customerAdapter.entityToDTO(customer);
            //
            return customerDTOResponse;
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Override
    public CustomerDTO getCustomerByEmail(String email) {
        try
        {
            checkAuthorization(email);
            //
            var customer = customerRepository.findCustomerByEmail(email);
            if(customer.isPresent()){
                return customerAdapter.entityToDTO(customer.get());
            }
            return null;
        }
        catch (RuntimeException e) {
            throw new RuntimeException("Failed to get the customer by email: " + e.getMessage());
        }
    }

    @Override
    public List<CustomerDTO> getCustomerByFirstName(String firstName) {
        try
        {
            checkAdminAuthorization();
            var customers = customerRepository.findCustomerByFirstName(firstName);
            if(customers.size() > 0){
                return customerAdapter.entityToDTOAll(customers);
            }
            return null;
        }
        catch (RuntimeException e) {
            throw new RuntimeException("Failed to get the customer by firstName: " + e.getMessage());
        }
    }

    @Override
    public List<CustomerDTO> getCustomerByLastName(String lastName) {
        try
        {
            checkAdminAuthorization();
            var customers = customerRepository.findCustomerByLastName(lastName);
            if(customers.size() > 0){
                return customerAdapter.entityToDTOAll(customers);
            }
            return null;
        }
        catch (RuntimeException e) {
            throw new RuntimeException("Failed to get the customer by lastName: " + e.getMessage());
        }
    }

    @Override
    public CustomerDTO updateCustomerNamesByEmail(String email, CustomerDTO customerDTO) {
        //
        try
        {
            checkAuthorization(email);
            //
            var customerOpt = customerRepository.findByEmail(email);
            if(customerOpt!=null)
            {
                customerOpt.setLastName(customerDTO.getLastName());
                customerOpt.setFirstName(customerDTO.getFirstName());
                //
                customerRepository.save(customerOpt);
                return customerAdapter.entityToDTO(customerRepository.findByEmail(email)) ;
            }
            else
                throw new IllegalArgumentException("Customer does not exist");

        }
        catch (RuntimeException e) {
            throw new RuntimeException("Failed to update customer: " + e.getMessage());
        }
        catch (Exception ex)
        {
            throw  ex;
        }
    }


    @Override
    public CustomerDTO deactivateCustomerByEmail(String email) {
        //
        try
        {
            checkAuthorization(email);
            //
            var customerOpt = customerRepository.findByEmail(email);
            if(customerOpt != null)
            {
                var user = customerOpt.getUser();
                user.setAuditData(new AuditData());
                user.setActive(false);
                userRepository.save(user);
                return customerAdapter.entityToDTO(customerRepository.findByEmail(email)) ;
            }
            else
                throw new IllegalArgumentException("Customer does not exist");
        }
        catch (RuntimeException e) {
            throw new RuntimeException("Failed to deactivate customer: " + e.getMessage());
        }
        catch (Exception ex)
        {
            throw  ex;
        }
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        checkAdminAuthorization();
        //
        return customerAdapter.entityToDTOAll( customerRepository.findAll() );
    }

    @Override
    public CustomerDTO updateCustomerBillingAddressByEmail(String email, AddressDTO addressDTO) {
        try
        {
            checkAuthorization(email);

            var customerOpt = customerRepository.findByEmail(email);
            if(customerOpt != null)
            {
                customerOpt.setBillingAddress( addressAdapter.DtoToEntity(addressDTO) );
                customerOpt.setAuditData(new AuditData());
                customerRepository.save(customerOpt);
                var return_data = customerAdapter.entityToDTO(customerRepository.findByEmail(email));
                return return_data;
            }
            else
                throw new IllegalArgumentException("Customer does not exist");
        }
        catch (RuntimeException e) {
            throw new RuntimeException("Failed to update [updateCustomerBillingAddressByEmail] : " + e.getMessage());
        }
        catch (Exception ex)
        {
            throw  ex;
        }
    }

    @Override
    public CustomerDTO updateCustomerPhysicalAddressByEmail(String email, AddressDTO addressDTO) {
        try
        {
            checkAuthorization(email);

            var customerOpt = customerRepository.findByEmail(email);
            if(customerOpt != null)
            {
                customerOpt.setPhysicalAddress(addressAdapter.DtoToEntity(addressDTO));
                return customerAdapter.entityToDTO(customerRepository.findByEmail(email));
            }
            else
                throw new IllegalArgumentException("Customer does not exist");
        }
        catch (RuntimeException e) {
            throw new RuntimeException("Failed to update [updateCustomerPhysicalAddressByEmail] : " + e.getMessage());
        }
        catch (Exception ex){
            throw ex;
        }
    }

    private void checkAuthorization(String email) {
        LoggedInUserDTO loggedInUserDTO = SecurityUtils.getLoggedInUser();
        if (loggedInUserDTO == null) {
            throw new IllegalArgumentException("User is not logged in");
        }
        if (loggedInUserDTO.getRole() == UserType.CLIENT && !loggedInUserDTO.getName().equals(email)) {
            throw new IllegalArgumentException("User is not authorized to update the customer");
        }
    }

    private void checkAdminAuthorization() {
        LoggedInUserDTO loggedInUserDTO = SecurityUtils.getLoggedInUser();
        if (loggedInUserDTO == null) {
            throw new IllegalArgumentException("User is not logged in");
        }
        if (loggedInUserDTO.getRole() != UserType.ADMIN) {
            throw new IllegalArgumentException("User is not authorized to execute the function");
        }
    }
}
