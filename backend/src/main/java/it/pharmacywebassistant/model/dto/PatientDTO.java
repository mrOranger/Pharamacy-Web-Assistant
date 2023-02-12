package it.pharmacywebassistant.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor
public final class PatientDTO extends PersonDTO {

    private List<PrescriptionDTO> prescriptionList = new ArrayList<>();
}
