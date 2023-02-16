package it.pharmacywebassistant.service.implementation;

import it.pharmacywebassistant.mapper.ProductMapper;
import it.pharmacywebassistant.model.Product;
import it.pharmacywebassistant.model.dto.ProductDTO;
import it.pharmacywebassistant.repository.ProductRepository;
import it.pharmacywebassistant.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service @Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductRepository repository;

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
        return productMapper.apply(product);
    }
    public Optional<ProductDTO> convertToDto(Optional<Product> product) {
        return product.stream().map(productMapper).findFirst();
    }

    public List<ProductDTO> convertToDto(List<Product> products) {
        return products.stream().map(productMapper).toList();
    }
}
