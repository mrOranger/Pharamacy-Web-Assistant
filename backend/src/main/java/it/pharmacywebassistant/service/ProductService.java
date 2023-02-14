package it.pharmacywebassistant.service;

import it.pharmacywebassistant.model.Product;
import it.pharmacywebassistant.model.dto.ProductDTO;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    public abstract List<ProductDTO> findAll();
    public abstract Optional<ProductDTO> findById(Long id);

    public abstract ProductDTO save(Product product);
    public abstract void deleteById(Long id);
    public abstract void deleteAll();
}
