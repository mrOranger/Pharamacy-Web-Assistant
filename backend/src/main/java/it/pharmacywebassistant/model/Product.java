package it.pharmacywebassistant.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Products")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public abstract class Product {

    @Id @Column(name = "Name")
    private String name;
    @Column(name = "Description")
    private String description;
    @Column(name = "Cost")
    private Float cost;

}
