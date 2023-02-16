package it.pharmacywebassistant.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PrescriptionDTO {
	
	private Long id;
	private PatientDTO patient;
	private DoctorDTO doctor;

}
