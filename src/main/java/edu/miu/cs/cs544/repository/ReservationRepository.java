package edu.miu.cs.cs544.repository;

import edu.miu.cs.cs544.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    List<Reservation> findAllByCustomerId(Integer customerId);

    @Query("SELECT r FROM Reservation r JOIN r.items i WHERE i.product.id = :productId AND :date BETWEEN i.checkinDate AND i.checkoutDate")
    List<Reservation> findReservationsByProductAndDate(@Param("productId") Integer productId, @Param("date") LocalDate date);

    @Query("SELECT r FROM Reservation r " +
            "JOIN r.items i " +
            "WHERE i.product.id = :productId " +
            "AND ((i.checkinDate > :checkin AND i.checkoutDate < :checkout) " +
            "OR (i.checkinDate BETWEEN :checkin AND :checkout) " +
            "OR (i.checkoutDate BETWEEN :checkin AND :checkout))")
    List<Reservation> findReservationsByProductAndDates(@Param("productId") Integer productId, @Param("checkin") LocalDate checkin, @Param("checkout") LocalDate checkout);
}
