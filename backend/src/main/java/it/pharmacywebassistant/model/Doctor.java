package it.pharmacywebassistant.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


@Entity(name = "Doctor") @Getter @Setter @NoArgsConstructor
public final class Doctor extends Person implements Serializable {

    @Serial
    private static final long serialVersionUID = -9284711738271L;

    @OneToMany(mappedBy = "doctor")
    @JsonManagedReference(value = "doctor")
    private List<Prescription> prescriptionList = new ArrayList<>();

    public Doctor(String taxCode, String firstName, String lastName, Date dateOfBirth) {
        super(taxCode, firstName, lastName, dateOfBirth);
    }
}
