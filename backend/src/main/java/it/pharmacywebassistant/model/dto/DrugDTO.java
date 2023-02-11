package it.pharmacywebassistant.model.dto;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class DrugDTO extends ProductDTO {

    private Boolean hasPrescription;

    public DrugDTO(Long id, String name, String description, Double cost, CompanyDTO company, Boolean hasPrescription) {
        super(id, name, description, cost, company);
        this.hasPrescription = hasPrescription;
    }

    public Boolean getHasPrescription() {
        return hasPrescription;
    }

    public void setHasPrescription(Boolean hasPrescription) {
        this.hasPrescription = hasPrescription;
    }
}
