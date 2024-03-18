package edu.miu.cs.cs544.domain;

import java.util.ArrayList;
import java.util.List;

import edu.miu.cs.cs544.domain.enums.ReservationState;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Reservation {

	@Id
	@GeneratedValue
	private Integer id;

	@ManyToOne
	private Customer customer;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "reservation", fetch = FetchType.EAGER)
	private List<Item> items = new ArrayList<>();

	@Embedded
	private AuditData auditData = new AuditData();

	@Enumerated(EnumType.STRING)
	public ReservationState reservationState;

	public Reservation(Customer customer, AuditData auditData) {
		this.customer = customer;
		this.auditData = auditData;
		this.items = new ArrayList<>();
	}
	public void addItem(Item item){
		items.add(item);
	}

}
