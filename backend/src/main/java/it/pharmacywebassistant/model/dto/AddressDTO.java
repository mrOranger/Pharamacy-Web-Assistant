package it.pharmacywebassistant.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor @NoArgsConstructor @Getter @Setter
public final class AddressDTO {

    private Long id;
    private String streetName;
    private Long streetCode;
    private String city;
    private String nation;
}
