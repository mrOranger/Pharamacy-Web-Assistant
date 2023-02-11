package it.pharmacywebassistant.service.implementation;

import it.pharmacywebassistant.model.Product;
import it.pharmacywebassistant.model.dto.ProductDTO;
import it.pharmacywebassistant.repository.ProductRepository;
import it.pharmacywebassistant.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service @Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ProductDTO> findAll() {
        return convertToDto(repository.findAll());
    }

    @Override
    public Optional<ProductDTO> findById(Long id) {
        return convertToDto(repository.findById(id));
    }

    @Override @Transactional
    public ProductDTO save(Product product) {
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

    public ProductDTO convertToDto(Product product) {
        ProductDTO productDTO = null;

        if(product != null) {
            productDTO = this.modelMapper.map(product, ProductDTO.class);
        }

        return productDTO;
    }
    public Optional<ProductDTO> convertToDto(Optional<Product> product) {

        if(product.isPresent()) {
            return Optional.of(this.modelMapper.map(product, ProductDTO.class));
        }

        return Optional.empty();
    }


    public List<ProductDTO> convertToDto(List<Product> products) {
        return products.stream()
                .map((source) -> this.modelMapper.map(source, ProductDTO.class))
                .collect(Collectors.toList());
    }
}
