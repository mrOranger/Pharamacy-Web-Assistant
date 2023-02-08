package it.pharmacywebassistant.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Entity(name = "Cosmetics") @Table(name = "Cosmetics")
@Getter @Setter @NoArgsConstructor
public final class Cosmetic {

    public Cosmetic(String name, String description, Float cost, String type) {
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.type = type;
    }

    @Id
    @Column(name = "Name")
    private String name;
    @Column(name = "Description")
    private String description;
    @Column(name = "Cost")
    private Float cost;

    @Column(name = "Prescription")
    private Boolean needPrescription;

    @Column(name = "ExpiresIn")
    private Date expiresIn;

    @Column(name = "Type")
    private String type;
}
