package it.pharmacywebassistant.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Address") @Table(name = "Addresses")
@NoArgsConstructor
public final class Address implements Serializable {

    public static final long serialVersionUID = -17282381012903L;

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "street")
    @NotNull(message = "{Address.StreetName.NotNull}")
    @Size(max = 100, message = "{Address.StreetName.Size}")
    private String streetName;

    @Column(name = "code")
    @NotNull(message = "{Address.StreetCode.NotNull}")
    @Range(min = 1, message = "{Address.StreetCode.Size}")
    private Long streetCode;

    @Column(name = "city")
    @NotNull(message = "{Address.City.NotNull}")
    @Size(max = 100, message = "{Address.City.Size}")
    private String city;

    @Column(name = "nation")
    @NotNull(message = "{Address.Nation.NotNull}")
    @Size(max = 100, message = "{Address.Nation.Size}")
    private String nation;

    @OneToMany(mappedBy = "address")
    @JsonManagedReference
    private final List<Company> companies = new ArrayList<>();

    public Address(String streetName, Long streetCode, String city, String nation) {
        this.streetName = streetName;
        this.streetCode = streetCode;
        this.city = city;
        this.nation = nation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public Long getStreetCode() {
        return streetCode;
    }

    public void setStreetCode(Long streetCode) {
        this.streetCode = streetCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }
}
