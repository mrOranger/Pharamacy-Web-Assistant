package it.pharmacywebassistant.model.dto;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class CosmeticDTO extends ProductDTO {

    private String type;

    public CosmeticDTO(Long id, String name, String description, Double cost, CompanyDTO company, String type) {
        super(id, name, description, cost, company);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
