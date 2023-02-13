package it.pharmacywebassistant.repository;

import it.pharmacywebassistant.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, String> {
}
