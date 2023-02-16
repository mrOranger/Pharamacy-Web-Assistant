package it.pharmacywebassistant.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity(name = "Durg") @Table(name = "Drugs")
@NoArgsConstructor @Getter @Setter
public final class Drug extends Product implements Serializable {

    public static final long serialVersionUID = -91821391239L;

    @Column(name = "Prescription")
    @NotNull(message = "{Drug.HasPrescription.NotNull}")
    private Boolean hasPrescription;

    public Drug(String name, String description, Double cost, Boolean hasPrescription) {
        super(name, description, cost);
        this.hasPrescription = hasPrescription;
    }
}
