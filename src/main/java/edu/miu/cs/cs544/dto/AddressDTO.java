package edu.miu.cs.cs544.dto;

import edu.miu.cs.cs544.domain.Address;
import edu.miu.cs.cs544.domain.AuditData;
import edu.miu.cs.cs544.domain.Reservation;
import edu.miu.cs.cs544.domain.State;
import edu.miu.cs.cs544.domain.enums.AddressType;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddressDTO {

    private String line1;

    private String line2;

    private String city;

    private String postalCode;

    private AddressType addressType;

    private StateDTO state;

}

