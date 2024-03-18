package edu.miu.cs.cs544.service;

import edu.miu.cs.cs544.adapter.ItemAdapter;
import edu.miu.cs.cs544.adapter.ReservationAdapter;
import edu.miu.cs.cs544.domain.Customer;
import edu.miu.cs.cs544.domain.Reservation;
import edu.miu.cs.cs544.domain.enums.ReservationState;
import edu.miu.cs.cs544.dto.CustomerDTO;
import edu.miu.cs.cs544.dto.ItemDTO;
import edu.miu.cs.cs544.dto.ReservationDTO;
import edu.miu.cs.cs544.dto.ResponseDto;
import edu.miu.cs.cs544.repository.CustomerRepository;
import edu.miu.cs.cs544.repository.ItemRepository;
import edu.miu.cs.cs544.repository.ProductRepository;
import edu.miu.cs.cs544.repository.ReservationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest
@ActiveProfiles("test")
@WithMockUser(username = "admin", authorities = {"ADMIN"})
class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;

    @MockBean
    private ReservationRepository reservationRepository;

    @MockBean
    private ReservationAdapter reservationAdapter;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private ItemAdapter itemAdapter;

    @MockBean
    private ItemRepository itemRepository;

    @MockBean
    private CustomerRepository customerRepository;

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void updateReservation_validReservationDetails_reservationUpdated() {
        // Arrange
        Customer customer = new Customer();
        customer.setId(1);
        customer.setEmail("test@gmail.com");

        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setId(1);
        //reservationDTO.setCustomer(customer);

        Reservation reservation = new Reservation();
        reservation.setId(1);
        reservation.setCustomer(customer);


        when(reservationRepository.findById(reservationDTO.getId())).thenReturn(Optional.of(reservation));
        when(reservationAdapter.dtoToEntity(reservationDTO)).thenReturn(reservation);
        when(reservationRepository.save(reservation)).thenReturn(reservation);

        // Act
        ResponseDto responseDto = reservationService.updateReservation(reservationDTO);

        // Assert
        assertTrue(responseDto.isSuccess());
        assertEquals("Reservation updated successfully", responseDto.getMessage());
        assertEquals(reservationDTO, responseDto.getData());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void createReservation_validReservationDetails_reservationCreated() {
        // Arrange
        CustomerDTO customer = new CustomerDTO();
        customer.setEmail("test@gmail.com");

        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setOccupants(1);
        itemDTO.setCheckinDate("2021-05-01");
        itemDTO.setCheckoutDate("2021-05-02");

        List<ItemDTO> items = List.of(itemDTO);
        items.add(itemDTO);


        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setCustomerEmail(customer.getEmail());
        reservationDTO.setItems(items);


        //when(customerRepository.findCustomerByEmail(customer.getEmail())).thenReturn(Optional.of(customer));
        //when(reservationAdapter.dtoToEntity(reservationDTO)).thenReturn(reservation);
        //when(reservationRepository.save(reservation)).thenReturn(reservation);

        // Act
        ResponseDto responseDto = reservationService.createReservation(reservationDTO);

        // Assert
        assertTrue(responseDto.isSuccess());
        assertEquals("Reservation created successfully", responseDto.getMessage());
        assertEquals(reservationDTO, responseDto.getData());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void cancelReservation_validReservation_reservationCancelled() {
        // Arrange
        Integer reservationId = 1;
        Reservation reservation = new Reservation();
        reservation.setId(reservationId);
        reservation.setReservationState(ReservationState.NEW);

        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));

        // Act
        ResponseDto responseDto = reservationService.cancelReservation(reservationId);

        // Assert
        verify(reservationRepository).save(argThat(savedReservation ->
                savedReservation.getReservationState() == ReservationState.CANCELLED));

        assertTrue(responseDto.isSuccess());
        assertEquals("Reservation cancelled successfully", responseDto.getMessage());
        assertEquals(reservationAdapter.entityToDTO(reservation), responseDto.getData());
    }

}