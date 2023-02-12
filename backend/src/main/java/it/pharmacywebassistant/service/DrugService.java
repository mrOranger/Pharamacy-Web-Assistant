package it.pharmacywebassistant.service;

import it.pharmacywebassistant.model.Drug;
import it.pharmacywebassistant.model.dto.DrugDTO;

import java.util.List;
import java.util.Optional;

public interface DrugService {
    public abstract List<DrugDTO> findAll();
    public abstract Optional<DrugDTO> findById(Long id);

    public abstract DrugDTO save(Drug product);
    public abstract void deleteById(Long id);
    public abstract void deleteAll();
}
