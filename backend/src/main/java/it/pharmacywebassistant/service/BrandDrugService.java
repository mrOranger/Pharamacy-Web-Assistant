package it.pharmacywebassistant.service;

import it.pharmacywebassistant.model.BrandDrug;
import it.pharmacywebassistant.repository.BrandDrugRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class BrandDrugService {

    @Autowired
    private BrandDrugRepository repository;

    public List<BrandDrug> findAll() {
        return repository.findAll();
    }

    public BrandDrug save(BrandDrug drug) {
        return repository.save(drug);
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}
