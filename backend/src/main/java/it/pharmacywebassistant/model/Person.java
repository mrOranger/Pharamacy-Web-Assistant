package it.pharmacywebassistant.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Date;

@Entity(name = "Person") @Table(name = "People")
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
public abstract class Person implements Serializable {

    @Id @Column(name = "taxCode")
    private String taxCode;

    @Serial
    private static final long serialVersionUID = -19820391810022L;

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

    @NotNull(message = "{Person.Residence.NotNull}")
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Residence", referencedColumnName = "id")
    @JsonBackReference
    private Address residence;

    public Person(String taxCode, String firstName, String lastName, Date dateOfBirth, Address residence) {
        this.taxCode = taxCode;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.residence = residence;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Address getResidence() {
        return residence;
    }

    public void setResidence(Address residence) {
        this.residence = residence;
    }
}
