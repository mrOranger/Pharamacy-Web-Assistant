package it.pharmacywebassistant.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

@Entity(name = "Prescription") @Table(name = "Prescriptions")
@NoArgsConstructor
public final class Prescription implements Serializable {

    @Serial
    private static final long serialVersionUID = -81928471729385L;

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "{Prescription.Date.NotNull}")
    @Column(name = "date")
    private Date date;

    @NotNull(message = "{Prescription.Doctor.NotNull}")
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "doctor", referencedColumnName = "taxCode")
    @JsonBackReference
    private Person doctor;


    @NotNull(message = "{Prescription.Patient.NotNull}")
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "patient", referencedColumnName = "taxCode")
    @JsonBackReference
    private Person patient;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "Prescripted_Drugs",
            joinColumns = { @JoinColumn(name = "Prescription") },
            inverseJoinColumns = { @JoinColumn(name = "Drug") }
    )
    @JsonManagedReference
    private Collection<Drug> drugs = new ArrayList<>();

    public Prescription(Long id, Date date, Doctor doctor, Patient patient) {
        this.id = id;
        this.date = date;
        this.doctor = doctor;
        this.patient = patient;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Person getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Person getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Collection<Drug> getDrugs() {
        return drugs;
    }

    public void setDrugs(Collection<Drug> drugs) {
        this.drugs = drugs;
    }
}
