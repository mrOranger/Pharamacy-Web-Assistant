package it.pharmacywebassistant.model.dto;

public final class DrugDto extends ProductDto {

    private Boolean hasPrescription;

    public DrugDto(String name, String description, Double cost, Boolean hasPrescription) {
        super(name, description, cost);
        this.hasPrescription = hasPrescription;
    }

    public Boolean getHasPrescription() {
        return hasPrescription;
    }

    public void setHasPrescription(Boolean hasPrescription) {
        this.hasPrescription = hasPrescription;
    }
}
