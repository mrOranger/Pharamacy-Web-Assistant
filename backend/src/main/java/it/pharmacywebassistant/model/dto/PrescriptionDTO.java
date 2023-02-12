package it.pharmacywebassistant.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

@Data @AllArgsConstructor @NoArgsConstructor
public final class PrescriptionDTO {

    private Long id;
    private Date date;
    private PersonDTO doctor;
    private PersonDTO patient;
    private Collection<DrugDTO> drugs = new ArrayList<>();
}
