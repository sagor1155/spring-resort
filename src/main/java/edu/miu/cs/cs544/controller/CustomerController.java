package edu.miu.cs.cs544.controller;

import edu.miu.cs.cs544.dto.AddressDTO;
import edu.miu.cs.cs544.dto.CustomerDTO;
import edu.miu.cs.cs544.dto.ResponseDto;
import edu.miu.cs.cs544.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> getCustomers(){
        return new ResponseEntity<List<CustomerDTO>>(customerService.getAllCustomers(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addCustomer(@RequestBody CustomerDTO customerDTO){
        try{
            var result = customerService.addCustomer(customerDTO);
            return new ResponseEntity<ResponseDto>(
                    new ResponseDto(
                        true,
                        "Customer added successfully",
                        result
                    ), HttpStatus.OK);
        }
        catch (Exception ex)
        {
            return new ResponseEntity<ResponseDto>(
                new ResponseDto(
                        true,
                        ex.getMessage(),
                        null
                ), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/email/{email}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENT')")
    public ResponseEntity<?> getCustomerByEmail(@PathVariable("email") String email){
        try {
            return new ResponseEntity<>(new ResponseDto(
                    true,
                    "Getting data successfully",
                    customerService.getCustomerByEmail(email)
                    ), HttpStatus.BAD_REQUEST);

        }
        catch (Exception ex)
        {
            return new ResponseEntity<ResponseDto>(
                    new ResponseDto(
                            false,
                            "Getting customer has error: " + ex.getMessage(),
                            null
                    )
                    , HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/firstname/{firstname}")
    public ResponseEntity<?> getCustomersByFirstname(@PathVariable("firstname") String firstname){
        try {
            return new ResponseEntity<>(
                    new ResponseDto(
                    true, "Getting data successfully",customerService.getCustomerByFirstName(firstname)
            ), HttpStatus.BAD_REQUEST);

        }
        catch (Exception ex)
        {
            return new ResponseEntity<ResponseDto>(
                    new ResponseDto(
                            false,
                            "Getting customers has error - getCustomersByFirstname: " + ex.getMessage(),
                            null
                    )
                    , HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/lastname/{lastname}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> getCustomersByLastname(@PathVariable("lastname") String lastname){
        try {
            return new ResponseEntity<>(
                    new ResponseDto(
                            true, "Getting data successfully",
                            customerService.getCustomerByLastName(lastname)
                    ), HttpStatus.BAD_REQUEST);

        }
        catch (Exception ex)
        {
            return new ResponseEntity<ResponseDto>(
                    new ResponseDto(
                            false,
                            "Getting customers has error - getCustomersByLastname: " + ex.getMessage(),
                            null
                    )
                    , HttpStatus.BAD_REQUEST);
        }
    }

    // more to implement: update name(s) / addresses
    @PutMapping("/email/{email}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CLIENT')")
    public ResponseEntity<?> updateCustomerByEmail(@PathVariable String email, @RequestBody CustomerDTO customerDTO){
       try {
           var result = customerService.updateCustomerNamesByEmail(email, customerDTO);
           return new ResponseEntity<ResponseDto>(
                   new ResponseDto(
                           true,
                           "Update for user [" + customerDTO.getEmail() + "] successfully",
                           result
                   )
                   , HttpStatus.OK);
       }
       catch (Exception ex)
       {
           return new ResponseEntity<ResponseDto>(
                   new ResponseDto(
                           false,
                           "Update for user[" + customerDTO.getEmail() + "] has error: " + ex.getMessage(),
                           null
                   )
                   , HttpStatus.BAD_REQUEST);
       }
    }

    @PutMapping("/billing/{email}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CLIENT')")
    public ResponseEntity<?> updateCustomerBillingByEmail(@PathVariable("email") String email, @RequestBody AddressDTO addressDTO){
        try {
            var result = customerService.updateCustomerBillingAddressByEmail(email, addressDTO);
            return new ResponseEntity<ResponseDto>(
                    new ResponseDto(
                            true,
                            "Update for user [" + email + "] successfully",
                            result
                    )
                    , HttpStatus.OK);
        }
        catch (Exception ex)
        {
            return new ResponseEntity<ResponseDto>(
                    new ResponseDto(
                            false,
                            "Update for user[" + email + "] has error: " + ex.getMessage(),
                            null
                    )
                    , HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/physical/{email}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CLIENT')")
    public ResponseEntity<?> updateCustomerPhysicalAddressByEmail(@PathVariable("email") String email,
                                                                  @RequestBody AddressDTO addressDTO){
        try {
            var result = customerService.updateCustomerPhysicalAddressByEmail(email, addressDTO);
            return new ResponseEntity<ResponseDto>(
                    new ResponseDto(
                            true,
                            "Update for user [" + email + "] successfully",
                            result
                    )
                    , HttpStatus.OK);
        }
        catch (Exception ex)
        {
            return new ResponseEntity<ResponseDto>(
                    new ResponseDto(
                            false,
                            "Update for user[" + email + "] has error: " + ex.getMessage(),
                            null
                    )
                    , HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/deactivate/{email}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> deactivateCustomerByEmail(@PathVariable("email") String email){
        try {
            var customer = customerService.getCustomerByEmail(email);
            if (customer == null) {
                return new ResponseEntity<ResponseDto>(new ResponseDto(false, "Customer with email = " + email + " is not available", null), HttpStatus.NOT_FOUND);
            }
            var result = customerService.deactivateCustomerByEmail(email);
            return new ResponseEntity<>(new ResponseDto(
                    true,
                    "Deactivating user [" + email + "] successfully",
                    result
            ), HttpStatus.OK);
        }
        catch (Exception ex){
            return new ResponseEntity<ResponseDto>(
                    new ResponseDto(
                            false,
                            "Deactivating user[" + email + "] has error: " + ex.getMessage(),
                            null
                    )
                    , HttpStatus.BAD_REQUEST);
        }
    }
}
