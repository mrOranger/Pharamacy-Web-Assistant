package it.pharmacywebassistant.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.pharmacywebassistant.mapper.DrugMapper;
import it.pharmacywebassistant.model.Drug;
import it.pharmacywebassistant.model.Prescription;
import it.pharmacywebassistant.model.dto.DrugDTO;
import it.pharmacywebassistant.repository.PrescriptionRepository;
import it.pharmacywebassistant.service.DrugsInPrescriptionService;

@Service @Transactional(readOnly = true)
public class DrugsInPrescriptionServiceImpl implements DrugsInPrescriptionService {
	
	@Autowired
	private PrescriptionRepository repository;
	
	@Autowired 
	private DrugMapper mapper;
	

	@Override
	public List<DrugDTO> findAllDrugs(Long id) {
		Optional<Prescription> prescription = repository.findById(id);
		if(prescription.isEmpty()) {
			return null;
		}
		return prescription.get().getDrugs().stream().map(drug -> convertToDto(drug)).toList();
	}

	@Override
	public Optional<DrugDTO> findByDrugId(Long prescriptionId, Long drugId) {
		Optional<Prescription> prescription = repository.findById(prescriptionId);
		if(prescription.isEmpty()) {
			return null;
		}
		return prescription.get().getDrugs().stream()
				.filter(drug -> drug.getId() == drugId)
				.map(drug -> convertToDto(drug))
				.findFirst();
	}

	@Override @Transactional
	public Prescription save(Long prescriptionId, Drug drug) {
		Optional<Prescription> prescription = repository.findById(prescriptionId);
		if(prescription.isEmpty()) {
			return null;
		}
		prescription.get().getDrugs().add(drug);
		return repository.save(prescription.get());
	}

	@Override @Transactional
	public void deleteAllDrugs(Long prescriptionId) {
		Optional<Prescription> prescription = repository.findById(prescriptionId);
		if(prescription.isPresent()) {
			prescription.get().getDrugs().removeAll(prescription.get().getDrugs());
			repository.save(prescription.get());
		}
	}

	@Override @Transactional
	public void deleteByDrugId(Long prescriptionId, Long drugId) {
		Optional<Prescription> prescription = repository.findById(prescriptionId);
		if(prescription.isPresent()) {
			Optional<Drug> drugToRemove = prescription.get().getDrugs()
				.stream()
				.filter(drug -> !drug.getId().equals(drugId))
				.findFirst();
			if(drugToRemove.isPresent()) {
				prescription.get().getDrugs().remove(drugToRemove.get());
				repository.save(prescription.get());
			}
		}
	}
	
    public DrugDTO convertToDto(Drug drug) {
        return mapper.apply(drug);
    }
    public Optional<DrugDTO> convertToDto(Optional<Drug> drug) {
        return drug.stream().map(mapper).findFirst();
    }

    public List<DrugDTO> convertToDto(List<Drug> drugList) {
        return drugList.stream().map(mapper).toList();
    }

}
