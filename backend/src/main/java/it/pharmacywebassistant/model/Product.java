package it.pharmacywebassistant.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.io.Serial;
import java.io.Serializable;

@Entity(name = "Product") @Table(name = "Products")
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
