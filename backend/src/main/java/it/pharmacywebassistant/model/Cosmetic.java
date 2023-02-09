package it.pharmacywebassistant.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity(name = "Cosmetic") @Table(name = "Cosmetics")
@NoArgsConstructor
public final class Cosmetic extends Product implements Serializable {

    @Column(name = "Type")
    @NotNull(message = "{Cosmetic.Type.NotNull}")
    @Size(max = 100, message = "{Cosmetic.Type.Size}")
    private String type;

    public Cosmetic(String name, String description, Double cost, String type) {
        super(name, description, cost);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
