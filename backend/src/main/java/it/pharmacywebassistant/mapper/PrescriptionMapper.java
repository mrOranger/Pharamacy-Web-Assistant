package it.pharmacywebassistant.mapper;

import it.pharmacywebassistant.model.Prescription;
import it.pharmacywebassistant.model.dto.DoctorDTO;
import it.pharmacywebassistant.model.dto.PatientDTO;
import it.pharmacywebassistant.model.dto.PersonDTO;
import it.pharmacywebassistant.model.dto.PrescriptionDTO;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class PrescriptionMapper implements Function<Prescription, PrescriptionDTO> {

    @Override
    public PrescriptionDTO apply(Prescription prescription) {
        final PersonMapper personMapper = new PersonMapper();
        final PersonDTO patientDTO = personMapper.apply(prescription.getPatient());
        final PersonDTO doctorDTO = personMapper.apply(prescription.getDoctor());
        return new PrescriptionDTO(prescription.getId(), (PatientDTO) patientDTO, (DoctorDTO) doctorDTO);
    }
}
