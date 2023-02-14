package it.pharmacywebassistant.repository;

import it.pharmacywebassistant.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, String> {

    public abstract List<Person> findByDType(String type);

}
