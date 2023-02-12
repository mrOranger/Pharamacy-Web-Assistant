package it.pharmacywebassistant.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;


@Data @AllArgsConstructor @NoArgsConstructor
public final class SpecializationDTO {
    private Long id;
    private Byte vote;
    private Date releaseDate;
    private String university;
    private String type;
    private DoctorDTO doctor;
}
