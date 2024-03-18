package edu.miu.cs.cs544.adapter;

import edu.miu.cs.cs544.domain.Address;
import edu.miu.cs.cs544.domain.Customer;
import edu.miu.cs.cs544.domain.enums.AddressType;
import edu.miu.cs.cs544.dto.AddressDTO;
import edu.miu.cs.cs544.dto.CustomerDTO;
import edu.miu.cs.cs544.dto.StateDTO;
import edu.miu.cs.cs544.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddressAdapter {

    @Autowired
    StateAdapter stateAdapter;
    public AddressDTO entityToDTO(Address address){
        var stateDto =  stateAdapter.entityToDto(address.getState());
        return new AddressDTO(address.getLine1(), address.getLine2(), address.getCity(), address.getPostalCode(), address.getAddressType(), stateDto);
    }

    public Address DtoToEntity(AddressDTO address){
        return new Address(address.getLine1(), address.getLine2(), address.getCity(), address.getPostalCode(), address.getAddressType(), stateAdapter.dtoToEntity(address.getState().getId(), address.getState()));
    }

}
