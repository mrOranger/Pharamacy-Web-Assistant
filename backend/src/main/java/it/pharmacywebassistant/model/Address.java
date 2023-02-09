package it.pharmacywebassistant.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity(name = "Address") @Table(name = "Addresses")
@NoArgsConstructor
public final class Address implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "street")
    @NotNull(message = "{Address.StreetName.NotNull}")
    @Size(max = 100, message = "{Address.StreetName.Size}")
    private String streetName;

    @Column(name = "code")
    @NotNull(message = "{Address.StreetCode.NotNull}")
    @Size(min = 1, message = "{Address.StreetCode.Size}")
    private Long streetCode;

    @Column(name = "city")
    @NotNull(message = "{Address.City.NotNull}")
    @Size(max = 100, message = "{Address.City.Size}")
    private String city;

    @Column(name = "nation")
    @NotNull(message = "{Address.Nation.NotNull}")
    @Size(max = 100, message = "{Address.Nation.Size}")
    private String nation;

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
