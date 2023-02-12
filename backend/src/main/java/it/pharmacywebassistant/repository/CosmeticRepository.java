package it.pharmacywebassistant.repository;


import it.pharmacywebassistant.model.Cosmetic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CosmeticRepository extends JpaRepository<Cosmetic, Long> {
}
