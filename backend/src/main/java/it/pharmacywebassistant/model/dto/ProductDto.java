package it.pharmacywebassistant.model.dto;

public abstract class ProductDto {

    private Long id;
    private String name;
    private String description;
    private Double cost;
    private CompanyDto company;

    public ProductDto(String name, String description, Double cost) {
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

    public CompanyDto getCompany() {
        return company;
    }
    public void setCompany(CompanyDto company) {
        this.company = company;
    }
}
