package edu.miu.cs.cs544.repository;

import edu.miu.cs.cs544.adapter.CustomerAdapter;
import edu.miu.cs.cs544.adapter.UserAdapter;
import edu.miu.cs.cs544.domain.enums.UserType;
import edu.miu.cs.cs544.dto.CustomerDTO;
import edu.miu.cs.cs544.dto.UserDTO;
import edu.miu.cs.cs544.repository.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerAdapter customerAdapter;

    @TestConfiguration
    static class CustomerServiceImplTestContextConfiguration {
        @Bean
        public CustomerAdapter customerAdapter() {
            return new CustomerAdapter();
        }
        @Bean
        public UserAdapter userAdapter() {
            return new UserAdapter();
        }
    }

    @Test
    public void whenFindByCustomerEmail_thenReturnCustomer() {
        //given
        UserDTO user = new UserDTO("anv", "123", true, UserType.CLIENT);
        CustomerDTO customerDTO = new CustomerDTO(
                "An",
                "Vu",
                "anvu.sg@gmail.com",
                user
        );
        var customer = customerAdapter.DtoToEntity(customerDTO);
        entityManager.persist(customer);
        entityManager.flush();
        //
        var found = customerRepository.findCustomerByEmail( customerDTO.getEmail() );
        //
        assertEquals(customer.getEmail(), found.get().getEmail());
    }


}
