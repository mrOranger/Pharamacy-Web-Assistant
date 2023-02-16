package it.pharmacywebassistant.repository;

import it.pharmacywebassistant.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
