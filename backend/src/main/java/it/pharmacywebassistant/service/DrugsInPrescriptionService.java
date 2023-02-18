package it.pharmacywebassistant.service;

import java.util.List;
import java.util.Optional;

import it.pharmacywebassistant.model.Drug;
import it.pharmacywebassistant.model.Prescription;
import it.pharmacywebassistant.model.dto.DrugDTO;

public interface DrugsInPrescriptionService {
	
    public abstract List<DrugDTO> findAllDrugs(Long id);
    public abstract Optional<DrugDTO> findByDrugId(Long prescriptionId, Long drugId);
    public abstract Prescription save(Long prescriptionId, Drug drug);
    public abstract void deleteAllDrugs(Long prescriptionId);
    public abstract void deleteByDrugId(Long prescriptionId, Long drugId);
    
}
