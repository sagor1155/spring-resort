package edu.miu.cs.cs544.repository;

import edu.miu.cs.cs544.domain.Item;
import edu.miu.cs.cs544.domain.Product;
import edu.miu.cs.cs544.domain.Reservation;
import edu.miu.cs.cs544.domain.enums.ReservationState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@WithMockUser(username = "admin", authorities = {"ADMIN"})
class ReservationRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    void findReservationsByProductAndDates() {
        /**
         * This test is to check if the query returns the correct reservation when the dates are in range
         */

        // Arrange
        Product product = new Product();
        product.setName("Test Product");
        product.setDescription("Test Description");
        entityManager.persist(product);

        Reservation reservation = new Reservation();
        reservation.setReservationState(null);
        reservation.setCustomer(null);
        entityManager.persist(reservation);

        Item item = new Item();
        item.setProduct(product);
        item.setReservation(reservation);
        item.setCheckinDate(LocalDate.now());
        item.setCheckoutDate(LocalDate.now().plusDays(1));
        entityManager.persist(item);

        // Act
        List<Reservation> reservations = reservationRepository.findReservationsByProductAndDates(product.getId(), LocalDate.now(), LocalDate.now().plusDays(1));

        // Assert
        assertFalse(reservations.isEmpty());
        assertEquals(reservation.getId(), reservations.get(0).getId());
    }

    @Test
    void findReservationsByProductAndDates_outOfRange() {
        /**
         * This test is to check if the query returns an empty list when the dates are out of range
         */

        // Arrange
        Product product = new Product();
        product.setName("Test Product");
        product.setDescription("Test Description");
        entityManager.persist(product);

        Reservation reservation = new Reservation();
        reservation.setReservationState(ReservationState.NEW);
        reservation.setCustomer(null);

        entityManager.persist(reservation);

        Item item = new Item();
        item.setProduct(product);
        item.setReservation(reservation);
        item.setCheckinDate(LocalDate.now());
        item.setCheckoutDate(LocalDate.now().plusDays(1));
        entityManager.persist(item);

        // Act
        List<Reservation> reservations = reservationRepository.findReservationsByProductAndDates(product.getId(), LocalDate.now().plusDays(2), LocalDate.now().plusDays(3));

        // Assert
        assertTrue(reservations.isEmpty());
    }
}
