package it.pharmacywebassistant.model.dto;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class AddressDTO implements Comparable<AddressDTO>{

    private Long id;
    private String streetName;
    private Long streetCode;
    private String city;
    private String nation;

    public AddressDTO(Long id, String streetName, Long streetCode, String city, String nation) {
        this.id = id;
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

    @Override
    public int compareTo(AddressDTO o) {
        return getId().compareTo(o.getId());
    }
}
