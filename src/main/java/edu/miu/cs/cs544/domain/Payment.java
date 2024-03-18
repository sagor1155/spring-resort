package edu.miu.cs.cs544.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Data
public class Payment {

    @Id
    private Integer id;

    private LocalDateTime paymentDateTime;

    private Double amount;

    @OneToOne
    private Reservation reservation;

    @OneToOne
    private Customer customer;

    @Embedded
    private AuditData auditData = new AuditData();


    @PrePersist
    public void prePersist() {
    	this.paymentDateTime = LocalDateTime.now();
    }

}
