package it.pharmacywebassistant.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "Brand") @Table(name = "Brands")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public final class Brand {

    @Id @Column(name = "Company")
    private String companyName;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "address", referencedColumnName = "code")
    private Address address;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "brand")
    private List<BrandDrug> drugs = new ArrayList<>();

}
