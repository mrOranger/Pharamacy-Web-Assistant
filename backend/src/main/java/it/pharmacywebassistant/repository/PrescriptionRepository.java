package it.pharmacywebassistant.repository;

import it.pharmacywebassistant.model.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
}
