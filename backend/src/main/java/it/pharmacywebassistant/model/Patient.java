package it.pharmacywebassistant.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Entity(name = "Patient") @Getter @Setter @AllArgsConstructor
public final class Patient extends Person implements Serializable {

    @Serial
    private static final long serialVersionUID = -18274019284732L;
}
