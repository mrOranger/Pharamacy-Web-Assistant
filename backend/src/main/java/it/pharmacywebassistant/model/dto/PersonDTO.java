package it.pharmacywebassistant.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter @Setter @NoArgsConstructor
public abstract class PersonDTO {
    private String taxCode;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;

    public PersonDTO(String taxCode, String firstName, String lastName, Date dateOfBirth) {
        this.taxCode = taxCode;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }
}
