package edu.miu.cs.cs544.dto;

import edu.miu.cs.cs544.domain.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {
    private Integer id;
    private String customerEmail;
    private List<ItemDTO> items;
}