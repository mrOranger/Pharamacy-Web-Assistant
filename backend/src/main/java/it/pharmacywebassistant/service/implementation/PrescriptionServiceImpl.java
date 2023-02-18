package it.pharmacywebassistant.service.implementation;

import it.pharmacywebassistant.mapper.PrescriptionMapper;
import it.pharmacywebassistant.model.Doctor;
import it.pharmacywebassistant.model.Patient;
import it.pharmacywebassistant.model.Prescription;
import it.pharmacywebassistant.model.dto.PrescriptionDTO;
import it.pharmacywebassistant.repository.PrescriptionRepository;
import it.pharmacywebassistant.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service @Transactional(readOnly = true)
public class PrescriptionServiceImpl implements PrescriptionService {

    @Autowired
    private PrescriptionRepository repository;

    @Autowired
    private PrescriptionMapper prescriptionMapper;

    @Override
    public List<PrescriptionDTO> findAll() {
        return convertToDto(repository.findAll());
    }

    @Override
    public List<PrescriptionDTO> findAllByPatientTaxCode(String taxCode) {
        return convertToDto(repository.findByPatientTaxCode(taxCode));
    }

    @Override
    public List<PrescriptionDTO> findAllByDoctorTaxCode(String taxCode) {
        return convertToDto(repository.findByDoctorTaxCode(taxCode));
    }

    @Override
    public Optional<PrescriptionDTO> findById(Long id) {
        return convertToDto(repository.findById(id));
    }

    @Override @Transactional
    public PrescriptionDTO save(Prescription prescription) {
        return convertToDto(repository.save(prescription));
    }
    
	@Override @Transactional
	public PrescriptionDTO savePatient(Long id, Patient patient) {
		Prescription prescription = repository.findById(id).get();
		prescription.setPatient(patient);
        return convertToDto(repository.save(prescription));
	}
	
	@Override @Transactional
	public PrescriptionDTO saveDoctor(Long id, Doctor doctor) {
		Prescription prescription = repository.findById(id).get();
		prescription.setDoctor(doctor);
        return convertToDto(repository.save(prescription));
	}

    @Override @Transactional
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public PrescriptionDTO convertToDto(Prescription prescription) {
        return prescriptionMapper.apply(prescription);
    }
    public Optional<PrescriptionDTO> convertToDto(Optional<Prescription> prescription) {
        return prescription.stream().map(prescriptionMapper).findFirst();
    }

    public List<PrescriptionDTO> convertToDto(List<Prescription> prescriptionList) {
        return prescriptionList.stream().map(prescriptionMapper).toList();
    }
}
