package edu.miu.cs.cs544.service;

import edu.miu.cs.cs544.adapter.ItemAdapter;
import edu.miu.cs.cs544.adapter.ReservationAdapter;
import edu.miu.cs.cs544.domain.Customer;
import edu.miu.cs.cs544.domain.Item;
import edu.miu.cs.cs544.domain.Product;
import edu.miu.cs.cs544.domain.Reservation;
import edu.miu.cs.cs544.domain.enums.ReservationState;
import edu.miu.cs.cs544.dto.ItemDTO;
import edu.miu.cs.cs544.dto.ReservationDTO;
import edu.miu.cs.cs544.dto.ResponseDto;
import edu.miu.cs.cs544.repository.ItemRepository;
import edu.miu.cs.cs544.repository.ReservationRepository;
import edu.miu.cs.cs544.service.validation.CustomerValidation;
import edu.miu.cs.cs544.service.validation.ProductValidation;
import edu.miu.cs.cs544.service.validation.ReservationValidation;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ReservationAdapter reservationAdapter;

    @Autowired
    private ItemAdapter itemAdapter;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CustomerValidation customerValidation;

    @Autowired
    private ReservationValidation reservationValidation;

    @Autowired
    private ProductValidation productValidation;


    @Transactional
    public ResponseDto updateReservation(ReservationDTO reservationDTO) {
        Optional<Reservation> reservationOptional = reservationRepository.findById(reservationDTO.getId());
        Reservation reservation = reservationOptional.orElseThrow(() -> new IllegalArgumentException("Reservation does not exist"));
        customerValidation.checkAuthorization(reservation.getCustomer());

        reservationDTO.getItems().forEach(itemDTO -> {
            if (itemDTO.getId() == null) {
                createAndAddItemToReservation(itemDTO, reservation);
            } else {
                Optional<Item> item = itemRepository.findById(itemDTO.getId());
                if (item.isEmpty()) {
                    createAndAddItemToReservation(itemDTO, reservation);
                } else {
                    reservationValidation.validateDates(itemDTO.getCheckinDate(), itemDTO.getCheckoutDate());
                    item.get().setCheckinDate(LocalDate.parse(itemDTO.getCheckinDate()));
                    item.get().setCheckoutDate(LocalDate.parse(itemDTO.getCheckoutDate()));
                    item.get().setOccupants(itemDTO.getOccupants());
                    itemRepository.save(item.get());
                }
            }
        });

        reservationRepository.save(reservation);
        reservationDTO.setItems(itemAdapter.entityToDTOAll(reservation.getItems()));

        return ResponseDto.builder()
                .success(true)
                .message("Reservation updated successfully")
                .data(reservationDTO)
                .build();
    }

    @Transactional
    public ResponseDto createReservation(ReservationDTO reservationDTO) {
        Customer customer = customerValidation.validateCustomer(reservationDTO.getCustomerEmail());
        customerValidation.checkAuthorization(customer);
        Reservation reservation = createNewReservation(customer);

        for (ItemDTO itemDTO : reservationDTO.getItems()) {
            reservationValidation.validateDates(itemDTO.getCheckinDate(), itemDTO.getCheckoutDate());
            reservationValidation.validateProductAvailability(itemDTO.getProduct().getId(), itemDTO.getCheckinDate(), itemDTO.getCheckoutDate());
            createAndAddItemToReservation(itemDTO, reservation);
        }

        reservationRepository.save(reservation);

        return ResponseDto.builder()
                .success(true)
                .message("Reservation created successfully")
                .data(reservationDTO)
                .build();
    }

    private Reservation createNewReservation(Customer customer) {
        Reservation reservation = new Reservation();
        reservation.setCustomer(customer);
        reservation.setReservationState(ReservationState.NEW);
        return reservation;
    }

    private void createAndAddItemToReservation(ItemDTO itemDTO, Reservation reservation) {
        Product product = productValidation.validateProduct(itemDTO.getProduct().getId());
        if (product.getMaxOccupancy() < itemDTO.getOccupants()) {
            throw new IllegalArgumentException("Requested occupancy exceeds max occupancy");
        }

        Item item = new Item();
        item.setCheckinDate(LocalDate.parse(itemDTO.getCheckinDate()));
        item.setCheckoutDate(LocalDate.parse(itemDTO.getCheckoutDate()));
        item.setOccupants(itemDTO.getOccupants());
        item.setProduct(product);
        item.setReservation(reservation);
        itemRepository.save(item);
        reservation.addItem(item);
    }

    public ResponseDto getAllReservations() {
        List<ReservationDTO> reservations = reservationAdapter.entityToDTOAll(reservationRepository.findAll());
        return ResponseDto.builder()
                .success(true)
                .message("Reservations fetched successfully")
                .data(reservations)
                .build();
    }


    public ResponseDto cancelReservation(Integer id) {
        Optional<Reservation> reservationOptional = reservationRepository.findById(id);

        if(reservationOptional.isEmpty())
            throw new IllegalArgumentException("Reservation does not exist");

        Reservation reservation = reservationOptional.get();
        reservation.getItems().forEach(item ->{
            if(item.getCheckinDate().minusDays(7).isBefore(LocalDate.now()))
                throw new IllegalArgumentException("Reservation cannot be cancelled");
        });

        customerValidation.checkAuthorization(reservation.getCustomer());

        reservation.setReservationState(ReservationState.CANCELLED);
        reservationRepository.save(reservation);

        return ResponseDto.builder()
                .success(true)
                .message("Reservation cancelled successfully")
                .data(reservationAdapter.entityToDTO(reservation))
                .build();
    }
}
