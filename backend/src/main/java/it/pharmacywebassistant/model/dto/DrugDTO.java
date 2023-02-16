package it.pharmacywebassistant.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @Getter @Setter
public final class DrugDTO extends ProductDTO {

    private Boolean hasPrescription;

    public DrugDTO(Long id, String name, String description, Double cost, CompanyDTO company, Boolean hasPrescription) {
        super(id, name, description, cost, company);
        this.hasPrescription = hasPrescription;
    }
}
