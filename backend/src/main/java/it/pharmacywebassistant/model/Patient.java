package it.pharmacywebassistant.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Date;

@Entity(name = "Patient") @Getter @Setter @NoArgsConstructor
public final class Patient extends Person implements Serializable {

    @Serial
    private static final long serialVersionUID = -18274019284732L;

    public Patient(String taxCode, String firstName, String lastName, Date dateOfBirth) {
        super(taxCode, firstName, lastName, dateOfBirth);
    }
}
