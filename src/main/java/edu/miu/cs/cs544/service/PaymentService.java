package edu.miu.cs.cs544.service;

import edu.miu.cs.cs544.domain.Reservation;
import edu.miu.cs.cs544.domain.enums.ReservationState;
import edu.miu.cs.cs544.dto.PaymentDTO;
import edu.miu.cs.cs544.dto.ResponseDto;
import edu.miu.cs.cs544.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private ReservationRepository reservationRepository;

    public ResponseDto makePayment(PaymentDTO paymentDTO) {
        Reservation reservation = getReservation(paymentDTO.getReservationId());
        checkReservationState(reservation);
        validatePayment(paymentDTO, reservation);
        processPayment(reservation);

        return ResponseDto.builder()
                .success(true)
                .message("Payment made successfully")
                .data(paymentDTO)
                .build();
    }

    private Reservation getReservation(Integer id) {
        Optional<Reservation> reservationOptional = reservationRepository.findById(id);
        if(reservationOptional.isEmpty())
            throw new IllegalArgumentException("Reservation does not exist");
        return reservationOptional.get();
    }

    private void checkReservationState(Reservation reservation) {
        if (reservation.getReservationState().equals(ReservationState.PLACED)) {
            throw new IllegalArgumentException("Reservation is already paid");
        }

        if (reservation.getReservationState().equals(ReservationState.CANCELLED)) {
            throw new IllegalArgumentException("Reservation is cancelled");
        }
    }

    private void validatePayment(PaymentDTO paymentDTO, Reservation reservation) {
        double total = reservation.getItems().stream()
                .mapToDouble(item -> item.getProduct().getRatePerNight()).sum();

        if (paymentDTO.getAmount() < total) {
            throw new IllegalArgumentException("Payment amount is less than the total amount");
        }
    }

    private void processPayment(Reservation reservation) {
        reservation.setReservationState(ReservationState.PLACED);
        reservationRepository.save(reservation);
    }
}