package it.pharmacywebassistant.service;

import it.pharmacywebassistant.model.Cosmetic;
import it.pharmacywebassistant.model.dto.CosmeticDTO;

import java.util.List;
import java.util.Optional;

public interface CosmeticService {
    public abstract List<CosmeticDTO> findAll();
    public abstract Optional<CosmeticDTO> findById(Long id);

    public abstract CosmeticDTO save(Cosmetic product);
    public abstract void deleteById(Long id);
    public abstract void deleteAll();
}
