package it.pharmacywebassistant.service.implementation;

import it.pharmacywebassistant.model.Person;
import it.pharmacywebassistant.model.dto.PersonDTO;
import it.pharmacywebassistant.repository.PersonRepository;
import it.pharmacywebassistant.service.PersonService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service @Transactional(readOnly = true)
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<PersonDTO> findAll() {
        return convertToDto(repository.findAll());
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
        PersonDTO personDTO = null;

        if(person != null) {
            personDTO = this.modelMapper.map(person, PersonDTO.class);
        }

        return personDTO;
    }
    public Optional<PersonDTO> convertToDto(Optional<Person> person) {

        if(person.isPresent()) {
            return Optional.of(this.modelMapper.map(person, PersonDTO.class));
        }

        return Optional.empty();
    }

    public List<PersonDTO> convertToDto(List<Person> personList) {
        return personList.stream()
                .map((source) -> this.modelMapper.map(source, PersonDTO.class))
                .collect(Collectors.toList());
    }
}
