package edu.miu.cs.cs544.service;

import edu.miu.cs.cs544.domain.Country;
import edu.miu.cs.cs544.dto.CountryDTO;
import edu.miu.cs.cs544.repository.CountryRepository;
import edu.miu.cs.cs544.service.CountryService;
import edu.miu.cs.cs544.service.CustomerService;
import edu.miu.cs.cs544.service.impl.CountryServiceImpl;
import org.aspectj.lang.annotation.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;

@RunWith(SpringRunner.class)
public class CountryServiceTests {
    @TestConfiguration
    static class CountryServiceImplTestContextConfiguration {

        @Bean
        public CountryServiceImpl countryService() {
            return new CountryServiceImpl();
        }
    }
    //@Autowired
    private CountryService countryService;
    @MockBean
    private CountryRepository countryRepository;

    @Before("")
    public void setUp() {

        Long countryId = 123L;
        Country country = new Country("USA", "United States of America",339 );
        Optional<Country> countryOptional = Optional.of(country);

        Mockito.when(countryRepository.findById(countryId)).thenReturn(countryOptional);
    }

    @Test
    public void whenValidCustomerNumberThenCustomerShouldBeFound() {

        Long countryId = 123L;
        CountryDTO found = countryService.findById(countryId);

        //assertThat(found.getId()).isEqualTo(countryId);
    }
}
