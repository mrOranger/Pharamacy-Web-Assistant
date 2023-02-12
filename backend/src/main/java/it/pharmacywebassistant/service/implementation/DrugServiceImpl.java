package it.pharmacywebassistant.service.implementation;

import it.pharmacywebassistant.model.Drug;
import it.pharmacywebassistant.model.dto.DrugDTO;
import it.pharmacywebassistant.repository.DrugRepository;
import it.pharmacywebassistant.service.DrugService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional(readOnly = true) @Service
public class DrugServiceImpl implements DrugService {

    @Autowired
    private DrugRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<DrugDTO> findAll() {
        return convertToDto(repository.findAll());
    }

    @Override
    public Optional<DrugDTO> findById(Long id) {
        return convertToDto(repository.findById(id));
    }

    @Override @Transactional
    public DrugDTO save(Drug product) {
        return convertToDto(repository.save(product));
    }

    @Override @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override @Transactional
    public void deleteAll() {
        repository.deleteAll();
    }

    public DrugDTO convertToDto(Drug product) {
        DrugDTO productDTO = null;

        if(product != null) {
            productDTO = this.modelMapper.map(product, DrugDTO.class);
        }

        return productDTO;
    }
    public Optional<DrugDTO> convertToDto(Optional<Drug> product) {

        if(product.isPresent()) {
            return Optional.of(this.modelMapper.map(product, DrugDTO.class));
        }

        return Optional.empty();
    }

    public List<DrugDTO> convertToDto(List<Drug> products) {
        return products.stream()
                .map((source) -> this.modelMapper.map(source, DrugDTO.class))
                .collect(Collectors.toList());
    }
}
