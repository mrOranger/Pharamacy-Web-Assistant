package it.pharmacywebassistant.service.implementation;

import it.pharmacywebassistant.model.Prescription;
import it.pharmacywebassistant.model.dto.PrescriptionDTO;
import it.pharmacywebassistant.service.PrescriptionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service @Transactional(readOnly = true)
public class PrescriptionServiceImpl implements PrescriptionService {
    @Override
    public List<PrescriptionDTO> findAll() {
        return null;
    }

    @Override
    public Optional<PrescriptionDTO> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public PrescriptionDTO save(Prescription prescription) {
        return null;
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void deleteById(Long id) {

    }
}
