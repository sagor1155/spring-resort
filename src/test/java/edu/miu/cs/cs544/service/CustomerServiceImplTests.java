package edu.miu.cs.cs544.service;

import edu.miu.cs.cs544.adapter.CustomerAdapter;
import edu.miu.cs.cs544.adapter.UserAdapter;
import edu.miu.cs.cs544.domain.Customer;
import edu.miu.cs.cs544.domain.User;
import edu.miu.cs.cs544.domain.enums.UserType;
import edu.miu.cs.cs544.dto.CustomerDTO;
import edu.miu.cs.cs544.dto.UserDTO;
import edu.miu.cs.cs544.repository.CustomerRepository;
import edu.miu.cs.cs544.service.impl.CustomerServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerServiceImplTests {

    @Autowired
    CustomerRepository customerRepository;

    @TestConfiguration
    static class CustomerServiceImplTestContextConfiguration {
        @Bean
        public CustomerServiceImpl customerService() {
            return new CustomerServiceImpl();
        }

        @Bean
        UserAdapter userAdapter(){
            return new UserAdapter();
        }

        @Bean
        CustomerAdapter customerAdapter(){
            return new CustomerAdapter();
        }

    }

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    CustomerAdapter  customerAdapter;

    @Autowired
    UserAdapter userAdapter;

    @MockBean
    CustomerRepository repository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    CustomerServiceImpl customerServiceImpl;


    Optional<Customer> customer;
    Optional<User> user;
    UserDTO userDto;
    CustomerDTO customerDto;
    @Before
    public void setup() {
        userDto = new UserDTO("newuser",  "newuser",  true, UserType.CLIENT);
        user = Optional.of( userAdapter.DtoToEntity(userDto));
        customerDto = new CustomerDTO("firstname","lastname", "email@customer.com", userDto);
        customer = Optional.of( new Customer("firstname","lastname", "email@customer.com", user.get()));
        Mockito.when(repository.findCustomerByEmail("email@customer.com")).thenReturn(customer);
    }

    @Test
    public void addCustomer_success() {

    }

    @Test
    public void getCustomer_success() {
        //prepare
        entityManager.persist(customer.get());
        entityManager.flush();
        //
        var customerDTO = customerServiceImpl.getCustomerByEmail(customer.get().getEmail());
        assertEquals( customerDTO.getFirstName(), customer.get().getFirstName() );
    }

    @Test
    public void updateCustomer_success() {
        //prepare
        entityManager.persist(customer.get());
        entityManager.flush();
        //
        var customerDTO = customerServiceImpl.getCustomerByEmail(customer.get().getEmail());
        assertEquals( customerDTO.getFirstName(), customer.get().getFirstName() );
    }





}
