package it.pharmacywebassistant.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @Getter @Setter @AllArgsConstructor
public final class DrugDTO extends ProductDTO {

    private Boolean hasPrescription;
}
