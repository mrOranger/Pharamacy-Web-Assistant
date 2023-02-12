package it.pharmacywebassistant.model.dto;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class CompanyDTO implements Comparable<CompanyDTO> {

    private Long id;
    private String name;
    private AddressDTO address;

    public CompanyDTO(Long id, String name, AddressDTO address) {
        this.id = id;
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

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    @Override
    public int compareTo(CompanyDTO o) {
        return getId().compareTo(o.getId());
    }
}
