package it.pharmacywebassistant.repository;

import it.pharmacywebassistant.model.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

    public abstract List<Prescription> findByPatientTaxCode(String taxCode);
    public abstract List<Prescription> findByDoctorTaxCode(String taxCode);
}
