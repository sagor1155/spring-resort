package edu.miu.cs.cs544.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.miu.cs.cs544.controller.CountryController;
import edu.miu.cs.cs544.domain.Country;
import edu.miu.cs.cs544.dto.CountryDTO;
import edu.miu.cs.cs544.service.CountryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
@RunWith(SpringRunner.class)
@WebMvcTest(CountryController.class)

public class CountryControllerTest {
    @Autowired
    MockMvc mock;
    @MockBean
    CountryService countryService;

    @Test
    public void testGetCountryById() throws Exception {
        Long a = 123L;
        Mockito.when(countryService.findById(123L)).thenReturn(new CountryDTO(123L, "USA", "United States of America", 339));

        mock.perform(get("/countries/" + a.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(123L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("USA"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("United States of America"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.population").value(339));
    }

    @Test
    public void testAddCountry() throws Exception {
        CountryDTO country = new CountryDTO("USA", "United States of America", 339);
        mock.perform(MockMvcRequestBuilders.post("/countries")
                        .content(asJsonString(country))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(countryService, times(1)).addCountry(country);
    }
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testGetAllCountries() throws Exception {
        List<CountryDTO> countries= new ArrayList<>();
        countries.add(new CountryDTO(123L, "USA", "United States of America", 339));
        countries.add(new CountryDTO(456L, "VN", "Vietnam", 99));
        Mockito.when(countryService.findAllCountries()).thenReturn(countries);
        mock.perform(MockMvcRequestBuilders.get("/countries"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(123L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].code").value("USA"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("United States of America"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].population").value(339));
        verify(countryService, times(1)).findAllCountries();
    }

    @Test public void testUpdateCountry() throws Exception {
        CountryDTO countryDTO = new CountryDTO(123L, "USA", "United States of America", 339);
        mock.perform(MockMvcRequestBuilders
                        .put("/countries")
                        .content(asJsonString(countryDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
        verify(countryService, times(1)).updateCountry(123L, countryDTO);
    }

//    @Test public void testDeleteCountryById() throws Exception {
//        mock.perform(MockMvcRequestBuilders
//                .delete("/countries/{id}",1))
//                .andExpect(status().isOk());
//        verify(countryService, times(1)).deleteById(123L);
//    }
}