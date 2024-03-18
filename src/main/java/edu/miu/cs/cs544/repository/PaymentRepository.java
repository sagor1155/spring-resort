package edu.miu.cs.cs544.repository;

import edu.miu.cs.cs544.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    List<Payment> findByCustomerIdAndPaymentDateTime(Integer customerId, LocalDateTime paymentDateTime);

}