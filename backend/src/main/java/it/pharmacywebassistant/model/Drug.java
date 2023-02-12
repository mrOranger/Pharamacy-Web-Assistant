package it.pharmacywebassistant.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToMany(mappedBy = "drugs")
    private List<Prescription> prescriptionList = new ArrayList<>();

    public Boolean getHasPrescription() {
        return hasPrescription;
    }

    public void setHasPrescription(Boolean hasPrescription) {
        this.hasPrescription = hasPrescription;
    }

}
