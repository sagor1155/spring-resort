package edu.miu.cs.cs544.domain;

import edu.miu.cs.cs544.domain.enums.UserType;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(unique = true)
	private String userName;
	
	private String userPass;
	
	private Boolean active;

	@Enumerated(EnumType.STRING)
	private UserType userType;

	@Embedded
	private AuditData auditData = new AuditData();

	public User(String userName, String userPass, Boolean active, UserType userType) {
		this.userName = userName;
		this.userPass = userPass;
		this.active = active;
		this.userType = userType;
	}
}
