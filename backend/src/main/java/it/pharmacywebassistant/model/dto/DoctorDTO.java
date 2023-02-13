package it.pharmacywebassistant.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter @Setter @NoArgsConstructor
public final class DoctorDTO extends PersonDTO {

    public DoctorDTO(String taxCode, String firstName, String lastName, Date dateOfBirth) {
        super(taxCode, firstName, lastName, dateOfBirth);
    }
}
