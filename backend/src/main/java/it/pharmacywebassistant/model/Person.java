package it.pharmacywebassistant.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Date;

@Entity(name = "Person") @Table(name = "People")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public abstract class Person implements Serializable {

    @Serial
    private static final long serialVersionUID = -19820391810022L;

    @Id @Column(name = "taxCode")
    private String taxCode;

    @Column(name = "firstName")
    @NotNull(message = "{Person.FirstName.NotNull}")
    @Size(max = 50, message = "{Person.FirstName.Size}")
    private String firstName;

    @Column(name = "lastName")
    @NotNull(message = "{Person.LastName.NotNull}")
    @Size(max = 50, message = "{Person.LastName.Size}")
    private String lastName;

    @Column(name = "dateOfBirth")
    @NotNull(message = "{Person.DateOfBirth.NotNull}")
    private Date dateOfBirth;
}
