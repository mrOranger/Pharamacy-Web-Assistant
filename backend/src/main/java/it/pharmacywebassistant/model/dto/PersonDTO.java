package it.pharmacywebassistant.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public abstract class PersonDTO {
    private String taxCode;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private AddressDTO residence;
}
