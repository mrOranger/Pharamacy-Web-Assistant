package it.pharmacywebassistant.service;

import it.pharmacywebassistant.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    public abstract List<Product> findAll();
    public abstract Optional<Product> findById(Long id);

    public abstract Product save(Product product);
    public abstract void deleteById(Long id);
    public abstract void deleteAll();

}
