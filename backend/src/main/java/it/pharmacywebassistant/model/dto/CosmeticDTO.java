package it.pharmacywebassistant.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @Getter @Setter
public final class CosmeticDTO extends ProductDTO {

    private String type;

    public CosmeticDTO(Long id, String name, String description, Double cost, CompanyDTO company, String type) {
        super(id, name, description, cost, company);
        this.type = type;
    }
}
