package it.pharmacywebassistant.service.implementation;

import it.pharmacywebassistant.model.Product;
import it.pharmacywebassistant.repository.ProductRepository;
import it.pharmacywebassistant.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service @Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Product> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return repository.findById(id);
    }

    @Override @Transactional
    public Product save(Product product) {
        return repository.save(product);
    }

    @Override @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override @Transactional
    public void deleteAll() {
        repository.deleteAll();
    }
}
