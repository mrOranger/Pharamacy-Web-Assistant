package it.pharmacywebassistant.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data @AllArgsConstructor @NoArgsConstructor
public abstract class PersonDTO {
    private String taxCode;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private AddressDTO residence;
}
