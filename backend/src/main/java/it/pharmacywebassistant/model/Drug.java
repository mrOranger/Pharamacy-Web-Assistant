package it.pharmacywebassistant.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Drug")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public abstract class Drug extends Product {
    @Column(name = "Prescription")
    private Boolean needPrescription;
}
