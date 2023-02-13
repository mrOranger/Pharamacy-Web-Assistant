package it.pharmacywebassistant.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;


@Entity(name = "Doctor") @Getter @Setter @AllArgsConstructor
public final class Doctor extends Person implements Serializable {

    @Serial
    private static final long serialVersionUID = -9284711738271L;
}
