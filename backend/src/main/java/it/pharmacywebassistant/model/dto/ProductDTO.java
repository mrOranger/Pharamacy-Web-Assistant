package it.pharmacywebassistant.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @Getter @Setter @AllArgsConstructor
public abstract class ProductDTO {

    private Long id;
    private String name;
    private String description;
    private Double cost;
    private CompanyDTO company;
}
