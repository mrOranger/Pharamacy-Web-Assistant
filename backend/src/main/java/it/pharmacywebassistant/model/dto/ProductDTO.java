package it.pharmacywebassistant.model.dto;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public abstract class ProductDTO implements Comparable<ProductDTO> {

    private Long id;
    private String name;
    private String description;
    private Double cost;
    private CompanyDTO company;

    public ProductDTO(Long id, String name, String description, Double cost, CompanyDTO company) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.company = company;
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

    public CompanyDTO getCompany() {
        return company;
    }

    public void setCompany(CompanyDTO company) {
        this.company = company;
    }

    @Override
    public int compareTo(ProductDTO o) {
        return getId().compareTo(o.getId());
    }
}
