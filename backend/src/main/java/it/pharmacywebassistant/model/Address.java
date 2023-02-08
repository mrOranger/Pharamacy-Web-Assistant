package it.pharmacywebassistant.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "Address") @Table(name = "Addresses")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public final class Address {

    @Id @Column(name = "Code")
    private String code;

    @Column(name = "Street")
    private String street;

    @Column(name = "StreetCode")
    private Integer streetCode;

    @Column(name = "City")
    private String city;

    @Column(name = "Country")
    private String country;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "address")
    private List<Brand> brands = new ArrayList<>();
}
