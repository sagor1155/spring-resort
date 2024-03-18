package edu.miu.cs.cs544.adapter;

import edu.miu.cs.cs544.domain.Customer;
import edu.miu.cs.cs544.domain.Item;
import edu.miu.cs.cs544.domain.Reservation;
import edu.miu.cs.cs544.dto.ReservationDTO;
import edu.miu.cs.cs544.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ReservationAdapter {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ItemAdapter itemAdapter;

    public ReservationDTO entityToDTO(Reservation reservation) {
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setId(reservation.getId());
        Customer customer = reservation.getCustomer();
        reservationDTO.setCustomerEmail(customer.getEmail());
        reservationDTO.setItems(itemAdapter.entityToDTOAll(reservation.getItems()));
        return reservationDTO;
    }

    public Reservation dtoToEntity(ReservationDTO reservationDTO) {
        Reservation reservation = new Reservation();
        reservation.setId(reservationDTO.getId());
        Optional<Customer> customer = customerRepository.findCustomerByEmail(reservationDTO.getCustomerEmail());
        if (customer.isPresent()) {
            reservation.setCustomer(customer.get());
        } else {
            throw new IllegalArgumentException("Customer does not exist");
        }
        reservation.setItems(itemAdapter.dtoToEntityAll(reservationDTO.getItems()));
        return reservation;
    }

    public List<ReservationDTO> entityToDTOAll(List<Reservation> reservations) {
        return reservations.stream().map(this::entityToDTO).toList();
    }
}
