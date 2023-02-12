package it.pharmacywebassistant.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity(name = "Durg") @Table(name = "Drugs")
@NoArgsConstructor
public final class Drug extends Product implements Serializable {

    public static final long serialVersionUID = -91821391239L;

    @Column(name = "Prescription")
    @NotNull(message = "{Drug.HasPrescription.NotNull}")
    private Boolean hasPrescription;

    public Drug(String name, String description, Double cost, Boolean hasPrescription) {
        super(name, description, cost);
        this.hasPrescription = hasPrescription;
    }

    public Boolean getHasPrescription() {
        return hasPrescription;
    }

    public void setHasPrescription(Boolean hasPrescription) {
        this.hasPrescription = hasPrescription;
    }

    @Override
    public String toString() {
        return this.getId() + " " + this.getName() + " " + this.getDescription() +  " " + this.getCost() + " " + this.getHasPrescription();
    }
}
