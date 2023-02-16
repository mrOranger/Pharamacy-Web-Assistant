package it.pharmacywebassistant.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity(name = "Cosmetic") @Table(name = "Cosmetics")
@NoArgsConstructor @Getter @Setter
public final class Cosmetic extends Product implements Serializable {

    public static final long serialVersionUID = -938481928340L;
    @Column(name = "Type")
    @NotNull(message = "{Cosmetic.Type.NotNull}")
    @Size(max = 100, message = "{Cosmetic.Type.Size}")
    private String type;

    public Cosmetic(String name, String description, Double cost, String type) {
        super(name, description, cost);
        this.type = type;
    }
}
