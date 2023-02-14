package it.pharmacywebassistant.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


@Entity(name = "Doctor") @Getter @Setter @AllArgsConstructor
public final class Doctor extends Person implements Serializable {

    @Serial
    private static final long serialVersionUID = -9284711738271L;

    @OneToMany(mappedBy = "doctor")
    @JsonManagedReference
    private List<Prescription> prescriptionList;

    public Doctor(String taxCode, String firstName, String lastName, Date dateOfBirth) {
        super(taxCode, firstName, lastName, dateOfBirth);
        this.prescriptionList = new ArrayList<>();
    }
}
