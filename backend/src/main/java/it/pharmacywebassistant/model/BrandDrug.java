package it.pharmacywebassistant.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Entity(name = "BrandDrug") @Table(name = "BrandDrugs")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public final class BrandDrug extends Drug {

    @Column(name = "ExpiresIn")
    private Date expiresIn;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "brand", referencedColumnName = "Company")
    private Brand brand;

}
