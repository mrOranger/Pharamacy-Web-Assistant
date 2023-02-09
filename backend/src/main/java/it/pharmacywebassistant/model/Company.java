package it.pharmacywebassistant.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity(name = "Company") @Table(name = "Companies")
@NoArgsConstructor
public final class Company implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "Name")
    @NotNull(message = "Company.Name.NotNull")
    @Size(max = 50, message = "Company.Name.Size")
    private String name;

    @NotNull(message = "Company.Address.NotNull")
    @ManyToOne
    private Address address;

    public Company(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
