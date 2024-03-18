package edu.miu.cs.cs544.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Customer extends AbstractEntity {

	@Id
	@GeneratedValue
	private Integer id;
	
	private String firstName;
	
	private String lastName;

	@Column(unique = true)
	private String email;

	@OneToMany(mappedBy = "customer")
	List<Reservation> reservations = new ArrayList<>();

	@Embedded
	private AuditData auditData = new AuditData();

	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="user_id")
	private User user;

	@ManyToOne(cascade = CascadeType.PERSIST, fetch=FetchType.EAGER)
	@JoinColumn(name="billing_address_id")
	private Address billingAddress;

	@ManyToOne(cascade = CascadeType.PERSIST, fetch=FetchType.EAGER)
	@JoinColumn(name="physical_address_id")
	private Address physicalAddress;

	public Customer(String firstName, String lastName, String email, User user, Address billingAddress, Address physicalAddress) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.user = user;
		this.billingAddress = billingAddress;
		this.physicalAddress = physicalAddress;
	}

	public Customer(String firstName, String lastName, String email, User user) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.user = user;
	}
}
