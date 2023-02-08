package it.pharmacywebassistant.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Entity(name = "BrandDrug") @Table(name = "BrandDrugs")
@Getter @Setter @NoArgsConstructor
public final class BrandDrug {

    public BrandDrug(String name, String description, Float cost, Boolean needPrescription, Date expiresIn) {
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.expiresIn = expiresIn;
    }

    @Id @Column(name = "Name")
    private String name;
    @Column(name = "Description")
    private String description;
    @Column(name = "Cost")
    private Float cost;

    @Column(name = "Prescription")
    private Boolean needPrescription;

    @Column(name = "ExpiresIn")
    private Date expiresIn;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "brand", referencedColumnName = "Company")
    private Brand brand;

}
