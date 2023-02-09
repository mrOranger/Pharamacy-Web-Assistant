package it.pharmacywebassistant.model.dto;

public final class CosmeticDto extends ProductDto {

    private String type;

    public CosmeticDto(String name, String description, Double cost, String type) {
        super(name, description, cost);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
