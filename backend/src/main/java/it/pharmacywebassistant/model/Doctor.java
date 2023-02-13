package it.pharmacywebassistant.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Date;


@Entity(name = "Doctor") @Getter @Setter @AllArgsConstructor
public final class Doctor extends Person implements Serializable {

    @Serial
    private static final long serialVersionUID = -9284711738271L;

    public Doctor(String taxCode, String firstName, String lastName, Date dateOfBirth) {
        super(taxCode, firstName, lastName, dateOfBirth);
    }
}
