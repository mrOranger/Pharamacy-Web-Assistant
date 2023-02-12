package it.pharmacywebassistant.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor @AllArgsConstructor
public final class DoctorDTO extends PersonDTO {
    private DegreeDTO degree;
    private SpecializationDTO specialization;
    private List<PrescriptionDTO> prescriptionList = new ArrayList<>();
}
