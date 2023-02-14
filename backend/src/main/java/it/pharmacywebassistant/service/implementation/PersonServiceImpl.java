package it.pharmacywebassistant.service.implementation;

import it.pharmacywebassistant.mapper.PersonMapper;
import it.pharmacywebassistant.model.Person;
import it.pharmacywebassistant.model.dto.DoctorDTO;
import it.pharmacywebassistant.model.dto.PatientDTO;
import it.pharmacywebassistant.model.dto.PersonDTO;
import it.pharmacywebassistant.repository.PersonRepository;
import it.pharmacywebassistant.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service @Transactional(readOnly = true)
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonMapper personMapper;

    @Autowired
    private PersonRepository repository;

    @Override
    public List<PersonDTO> findAllPatients() {
        return convertToDto(repository.findAll())
                .stream()
                .filter(person -> person instanceof PatientDTO)
                .toList();
    }

    @Override
    public List<PersonDTO> findAllDoctors() {
        return convertToDto(repository.findAll())
                .stream()
                .filter(person -> person instanceof DoctorDTO)
                .toList();
    }

    @Override
    public Optional<PersonDTO> findByTaxCode(String taxCode) {
        return convertToDto(repository.findById(taxCode));
    }

    @Override @Transactional
    public PersonDTO save(Person person) {
        return convertToDto(repository.save(person));
    }

    @Override @Transactional
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override @Transactional
    public void deleteByTaxCode(String taxCode) {
        repository.deleteById(taxCode);
    }

    public PersonDTO convertToDto(Person person) {
        return personMapper.apply(person);
    }
    public Optional<PersonDTO> convertToDto(Optional<Person> person) {
        return person.stream().map(personMapper).findFirst();
    }

    public List<PersonDTO> convertToDto(List<Person> personList) {
        return personList.stream().map(personMapper).toList();
    }
}
