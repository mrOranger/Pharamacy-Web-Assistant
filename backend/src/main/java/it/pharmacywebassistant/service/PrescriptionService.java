package it.pharmacywebassistant.service;

import it.pharmacywebassistant.model.Doctor;
import it.pharmacywebassistant.model.Patient;
import it.pharmacywebassistant.model.Prescription;
import it.pharmacywebassistant.model.dto.PrescriptionDTO;

import java.util.List;
import java.util.Optional;

public interface PrescriptionService {

    public abstract List<PrescriptionDTO> findAll();

    public abstract List<PrescriptionDTO> findAllByPatientTaxCode(String taxCode);
    public abstract List<PrescriptionDTO> findAllByDoctorTaxCode(String taxCode);
    public abstract Optional<PrescriptionDTO> findById(Long id);
    public abstract PrescriptionDTO save(Prescription prescription);
    public abstract PrescriptionDTO savePatient(Long id, Patient patient);
    public abstract PrescriptionDTO saveDoctor(Long id, Doctor doctor);
    public abstract void deleteAll();
    public abstract void deleteById(Long id);

}
