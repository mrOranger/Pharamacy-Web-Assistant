package it.pharmacywebassistant.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


@Entity(name = "Doctor") @NoArgsConstructor @Table(name = "Doctors")
public final class Doctor extends Person implements Serializable {

    @Serial
    private static final long serialVersionUID = -9284711738271L;

    @NotNull(message = "{Doctor.Degree.NotNull}")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "degree", referencedColumnName = "id")
    private Degree degree;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "specialization", referencedColumnName = "id")
    private Specialization specialization;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "id")
    private List<Prescription> prescriptionList = new ArrayList<>();

    public Doctor(String taxCode, String firstName, String lastName, Date dateOfBirth, Address residence, Degree degree) {
        super(taxCode, firstName, lastName, dateOfBirth, residence);
        this.degree = degree;
    }

    public Degree getDegree() {
        return degree;
    }

    public void setDegree(Degree degree) {
        this.degree = degree;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    public List<Prescription> getPrescriptionList() {
        return prescriptionList;
    }

    public void setPrescriptionList(List<Prescription> prescriptionList) {
        this.prescriptionList = prescriptionList;
    }
}
