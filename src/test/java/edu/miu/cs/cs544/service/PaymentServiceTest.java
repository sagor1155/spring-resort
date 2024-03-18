package edu.miu.cs.cs544.service;

import edu.miu.cs.cs544.domain.Item;
import edu.miu.cs.cs544.domain.Product;
import edu.miu.cs.cs544.domain.Reservation;
import edu.miu.cs.cs544.domain.enums.ReservationState;
import edu.miu.cs.cs544.dto.PaymentDTO;
import edu.miu.cs.cs544.dto.ResponseDto;
import edu.miu.cs.cs544.repository.ReservationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class PaymentServiceTest {

    @MockBean
    private ReservationRepository reservationRepository;

    @Autowired
    private PaymentService paymentService;

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void makePayment_validPaymentDetails_paymentMade() {
        // Arrange
        Integer reservationId = 1;
        Reservation reservation = new Reservation();
        reservation.setId(reservationId);
        reservation.setReservationState(ReservationState.NEW);

        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setReservationId(reservationId);
        paymentDTO.setAmount(100.0);

        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        // Act
        ResponseDto responseDto = paymentService.makePayment(paymentDTO);

        // Assert
        assertTrue(responseDto.isSuccess());
        assertEquals("Payment made successfully", responseDto.getMessage());
        assertEquals(paymentDTO, responseDto.getData());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void makePayment_inValidPaymentDetails_PaymentNotMade(){
        Integer reservationId = 1;
        Reservation reservation = new Reservation();
        reservation.setId(reservationId);
        reservation.setReservationState(ReservationState.NEW);

        Product product = new Product();
        product.setId(1);
        product.setRatePerNight(100.0);

        Item item = new Item();
        item.setId(1);
        item.setReservation(reservation);
        item.setProduct(product);

        reservation.addItem(item);

        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setReservationId(reservationId);
        paymentDTO.setAmount(50.0);

        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        assertThrows(IllegalArgumentException.class, () -> paymentService.makePayment(paymentDTO));
    }
}