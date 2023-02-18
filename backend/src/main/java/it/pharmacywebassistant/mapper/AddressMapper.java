package it.pharmacywebassistant.mapper;

import java.util.function.Function;

import org.springframework.stereotype.Service;

import it.pharmacywebassistant.model.Address;
import it.pharmacywebassistant.model.dto.AddressDTO;

@Service
public class AddressMapper implements Function<Address, AddressDTO>{

	@Override
	public AddressDTO apply(Address address) {
        return new AddressDTO(
                address.getId(),
                address.getStreetName(),
                address.getStreetCode(),
                address.getCity(),
                address.getNation());
	}

}
