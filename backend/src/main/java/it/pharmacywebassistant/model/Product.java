package it.pharmacywebassistant.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import java.io.Serial;
import java.io.Serializable;

@Entity(name = "Product") @Table(name = "Products")
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor @Getter @Setter
public abstract class Product implements Serializable {

    @Serial
    private static final long serialVersionUID = -3040216250095742667L;

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "Name")
    @NotNull(message = "{Product.Name.NotNull}")
    @Size(max = 50, message = "{Product.Name.Size}")
    private String name;

    @Column(name = "Description")
    @NotNull(message = "{Product.Description.NotNull}")
    @Size(max = 200, message = "{Product.Description.Size}")
    private String description;

    @Column(name = "Cost")
    @NotNull(message = "{Product.Cost.NotNull}")
    @Range(min = 1, message = "{Product.Cost.Size}")
    private Double cost;

    @NotNull(message = "{Product.Company.NotNull}")
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Company", referencedColumnName = "id")
    @JsonBackReference
    private Company company;

    public Product(String name, String description, Double cost) {
        this.name = name;
        this.description = description;
        this.cost = cost;
    }
}
