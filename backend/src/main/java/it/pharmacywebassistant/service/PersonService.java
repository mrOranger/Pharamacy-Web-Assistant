package it.pharmacywebassistant.service;

import it.pharmacywebassistant.model.Person;
import it.pharmacywebassistant.model.dto.PersonDTO;

import java.util.List;
import java.util.Optional;

public interface PersonService {

    public abstract List<PersonDTO> findAll();
    public abstract Optional<PersonDTO> findByTaxCode(String taxCode);
    public abstract PersonDTO save(Person person);
    public abstract void deleteAll();
    public abstract void deleteByTaxCode(String taxCode);

}
