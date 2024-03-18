package edu.miu.cs.cs544.controller;

import edu.miu.cs.cs544.adapter.CustomerAdapter;
import edu.miu.cs.cs544.adapter.UserAdapter;
import edu.miu.cs.cs544.domain.enums.UserType;
import edu.miu.cs.cs544.dto.CustomerDTO;
import edu.miu.cs.cs544.dto.UserDTO;
import edu.miu.cs.cs544.repository.UserRepository;
import edu.miu.cs.cs544.security.JwtService;
import edu.miu.cs.cs544.security.MemberDetailsService;
import edu.miu.cs.cs544.security.SecurityConfig;
import edu.miu.cs.cs544.service.impl.CustomerServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@Import(SecurityConfig.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTests {

    @Autowired
    MockMvc mock;
    @MockBean
    CustomerServiceImpl customerService;

    @TestConfiguration
    static class CustomerServiceImplTestContextConfiguration {
        @Bean
        JwtService jwtService(){
            return new JwtService();
        }
        @Bean
        MemberDetailsService memberDetailsService(){
            return new MemberDetailsService();
        }

        @MockBean
        UserRepository userRepository;
    }
    @Test
    public void testGetCustomerByEmail() throws Exception{
        String email = "customer@gmail.com";
        Mockito.when(customerService.getCustomerByEmail("customer@gmail.com")).thenReturn(new CustomerDTO(
                "customer",
                "rms",
                "customer@gmail.com",
                new UserDTO(
                        "customer",
                        "123",
                        true,
                        UserType.CLIENT
                )
        ));
        mock.perform(get("/customers/" + email).with(
                                jwt())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetCustomerByEmailMock() throws Exception{
        String email = "customer@gmail.com";
        Mockito.when(customerService.getCustomerByEmail("customer@gmail.com")).thenReturn(new CustomerDTO(
                "customer",
                "rms",
                "customer@gmail.com",
                new UserDTO(
                        "customer",
                        "123",
                        true,
                        UserType.CLIENT
                )
        ));
//        mock.perform(get("/customers/" + email).with(
//                                jwt())
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());

        mock
            .perform(get("/customers"+ email).with(jwt()).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
    }

//    mvc
//            .perform(get("/endpoint").with(jwt()));
}
