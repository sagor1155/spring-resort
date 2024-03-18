package edu.miu.cs.cs544.domain;

import edu.miu.cs.cs544.domain.enums.AddressType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
public class Address {

	@Id
	@GeneratedValue
	private Integer id;

	private String line1;
	
	private String line2;
	
	private String city;
	
	private String postalCode;

	@Enumerated(EnumType.STRING)
	private AddressType addressType;

	@Embedded
	private AuditData auditData;

	@ManyToOne(fetch = FetchType.EAGER)
	private State state;

	public Address(String line1, String line2, String city, String postalCode, AddressType addressType, AuditData auditData, State state) {
		this.line1 = line1;
		this.line2 = line2;
		this.city = city;
		this.postalCode = postalCode;
		this.addressType = addressType;
		this.auditData = auditData;
		this.state = state;
	}

	public Address(String line1, String line2, String city, String postalCode, AddressType addressType, State state) {
		this.line1 = line1;
		this.line2 = line2;
		this.city = city;
		this.postalCode = postalCode;
		this.addressType = addressType;
		this.state = state;
	}
}
