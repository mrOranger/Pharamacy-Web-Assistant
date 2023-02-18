package it.pharmacywebassistant.model.dto;

import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class PrescriptionDTO {
	
	private Long id;
	private PatientDTO patient;
	private DoctorDTO doctor;
	private Set<DrugDTO> drugs;
	
	public PrescriptionDTO(Long id, PatientDTO patient, DoctorDTO doctor) {
		this.id = id;
		this.patient = patient;
		this.doctor = doctor;
	}
}
