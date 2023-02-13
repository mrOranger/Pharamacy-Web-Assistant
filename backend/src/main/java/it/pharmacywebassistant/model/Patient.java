package it.pharmacywebassistant.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Patient") @NoArgsConstructor @Table(name = "Patients")
public final class Patient extends Person implements Serializable {

    @Serial
    private static final long serialVersionUID = -18274019284732L;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "id")
    @JsonManagedReference
    private List<Prescription> prescriptionList = new ArrayList<>();

    public Patient(String taxCode, String firstName, String lastName, Date dateOfBirth, Address residence) {
        super(taxCode, firstName, lastName, dateOfBirth, residence);
    }

    public List<Prescription> getPrescriptionList() {
        return prescriptionList;
    }

    public void setPrescriptionList(List<Prescription> prescriptionList) {
        this.prescriptionList = prescriptionList;
    }
}
