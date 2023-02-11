package it.pharmacywebassistant.service.implementation;

import it.pharmacywebassistant.model.Cosmetic;
import it.pharmacywebassistant.model.dto.CosmeticDTO;
import it.pharmacywebassistant.repository.CosmeticRepository;
import it.pharmacywebassistant.service.CosmeticService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional(readOnly = true) @Service
public class CosmeticServiceImpl implements CosmeticService {

    @Autowired
    private CosmeticRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<CosmeticDTO> findAll() {
        return convertToDto(repository.findAll());
    }

    @Override
    public Optional<CosmeticDTO> findById(Long id) {
        return convertToDto(repository.findById(id));
    }

    @Override @Transactional
    public CosmeticDTO save(Cosmetic product) {
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

    public CosmeticDTO convertToDto(Cosmetic product) {
        CosmeticDTO productDTO = null;

        if(product != null) {
            productDTO = this.modelMapper.map(product, CosmeticDTO.class);
        }

        return productDTO;
    }
    public Optional<CosmeticDTO> convertToDto(Optional<Cosmetic> product) {

        if(product.isPresent()) {
            return Optional.of(this.modelMapper.map(product, CosmeticDTO.class));
        }

        return Optional.empty();
    }

    public List<CosmeticDTO> convertToDto(List<Cosmetic> products) {
        return products.stream()
                .map((source) -> this.modelMapper.map(source, CosmeticDTO.class))
                .collect(Collectors.toList());
    }
}
