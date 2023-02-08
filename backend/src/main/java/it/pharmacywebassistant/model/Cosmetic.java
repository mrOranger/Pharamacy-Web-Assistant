package it.pharmacywebassistant.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Cosmetics") @Table(name = "Cosmetics")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public final class Cosmetic extends Product {

    @Column(name = "Type")
    private String type;
}
