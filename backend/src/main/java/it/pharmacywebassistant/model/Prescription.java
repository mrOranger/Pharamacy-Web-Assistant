package it.pharmacywebassistant.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity(name = "Prescription") @Table(name = "Prescriptions")
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class Prescription implements Serializable {

    private static final long serialVersionUID = -91029471829388L;

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "{Prescription.Patient.NotNull}")
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Patient", nullable = false)
    @JsonBackReference(value = "patient")
    private Patient patient;

    @NotNull(message = "{Prescription.Doctor.NotNull}")
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Doctor", nullable = false)
    @JsonBackReference(value = "doctor")
    private Doctor doctor;

}
