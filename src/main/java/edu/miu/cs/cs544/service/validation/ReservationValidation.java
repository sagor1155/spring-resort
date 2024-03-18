package edu.miu.cs.cs544.service.validation;

import edu.miu.cs.cs544.domain.Product;
import edu.miu.cs.cs544.domain.Reservation;
import edu.miu.cs.cs544.repository.ProductRepository;
import edu.miu.cs.cs544.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class ReservationValidation {

    @Autowired
    private ReservationRepository reservationRepository;

    public void validateDates(String checkinDate, String checkoutDate) {
        LocalDate checkin = LocalDate.parse(checkinDate);
        LocalDate checkout = LocalDate.parse(checkoutDate);

        if (!checkin.isBefore(checkout)) {
            throw new IllegalArgumentException("Check-in date must be before check-out date");
        }
    }


    public void validateProductAvailability(Integer productId, String checkinDate, String checkoutDate) {
        if (isProductReservedDuringStay(productId, checkinDate, checkoutDate)) {
            throw new IllegalArgumentException("Product is already reserved on this date");
        }
    }

    public boolean isProductReservedDuringStay(Integer productId, String checkinDate, String checkoutDate) {
        LocalDate checkin = LocalDate.parse(checkinDate);
        LocalDate checkout = LocalDate.parse(checkoutDate);
        List<Reservation> reservations = reservationRepository.findReservationsByProductAndDates(productId, checkin, checkout);
        return !reservations.isEmpty();
    }
}