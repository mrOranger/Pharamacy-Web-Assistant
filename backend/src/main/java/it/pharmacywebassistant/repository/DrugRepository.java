package it.pharmacywebassistant.repository;

import it.pharmacywebassistant.model.Drug;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrugRepository extends JpaRepository<Drug, Long> {
}
