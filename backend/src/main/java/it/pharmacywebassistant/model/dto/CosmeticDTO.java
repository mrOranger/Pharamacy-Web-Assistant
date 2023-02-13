package it.pharmacywebassistant.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public final class CosmeticDTO extends ProductDTO {

    private String type;
}
