package it.pharmacywebassistant.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Company") @Table(name = "Companies")
@NoArgsConstructor @Getter @Setter
public final class Company implements Serializable {

    public static final long serialVersionUID = -1701192301923L;

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "Name")
    @NotNull(message = "Company.Name.NotNull")
    @Size(max = 50, message = "Company.Name.Size")
    private String name;

    @NotNull(message = "Company.Address.NotNull")
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Address", nullable = false)
    @JsonBackReference
    private Address address;

    @OneToMany(mappedBy = "company")
    @JsonManagedReference
    private final List<Product> products = new ArrayList<>();

    public Company(String name, Address address) {
        this.name = name;
        this.address = address;
    }
}
