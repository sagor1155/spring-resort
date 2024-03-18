package edu.miu.cs.cs544.domain;

import edu.miu.cs.cs544.domain.enums.ProductType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Product {

	@Id
	@GeneratedValue
	private Integer id;
	
	private String name; 
	
	private String description;
	
	private String excerpt;

	@Enumerated(EnumType.STRING)
	private ProductType type;

	@Column(name = "max_occupancy")
	private Integer maxOccupancy;

	@Column(name = "rate_per_night")
	private Double ratePerNight;

	@Embedded
	private AuditData auditData = new AuditData();

	public Product(String name, String description, String excerpt, ProductType type, Integer maxOccupancy) {
		this.name = name;
		this.description = description;
		this.excerpt = excerpt;
		this.type = type;
		this.maxOccupancy = maxOccupancy;
	}
}
