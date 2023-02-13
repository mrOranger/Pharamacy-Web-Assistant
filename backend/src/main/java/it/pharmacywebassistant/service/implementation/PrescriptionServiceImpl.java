package it.pharmacywebassistant.service.implementation;

import it.pharmacywebassistant.model.Drug;
import it.pharmacywebassistant.model.Prescription;
import it.pharmacywebassistant.model.dto.DrugDTO;
import it.pharmacywebassistant.model.dto.PrescriptionDTO;
import it.pharmacywebassistant.repository.PrescriptionRepository;
import it.pharmacywebassistant.service.PrescriptionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service @Transactional(readOnly = true)
public class PrescriptionServiceImpl implements PrescriptionService {

    @Autowired
    private PrescriptionRepository repository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<PrescriptionDTO> findAll() {
        return convertToDto(repository.findAll());
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
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public PrescriptionDTO convertToDto(Prescription prescription) {
        PrescriptionDTO prescriptionDTO = null;

        if(prescription != null) {
            prescriptionDTO = this.modelMapper.map(prescription, PrescriptionDTO.class);
        }

        return prescriptionDTO;
    }

    public Optional<PrescriptionDTO> convertToDto(Optional<Prescription> prescription) {

        if(prescription.isPresent()) {
            return Optional.of(this.modelMapper.map(prescription, PrescriptionDTO.class));
        }

        return Optional.empty();
    }

    public List<PrescriptionDTO> convertToDto(List<Prescription> prescriptions) {
        return prescriptions.stream()
                .map((source) -> this.modelMapper.map(source, PrescriptionDTO.class))
                .collect(Collectors.toList());
    }
}
