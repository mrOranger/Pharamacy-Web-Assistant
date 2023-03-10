package it.pharmacywebassistant.mapper;

import it.pharmacywebassistant.model.Patient;
import it.pharmacywebassistant.model.Person;
import it.pharmacywebassistant.model.dto.DoctorDTO;
import it.pharmacywebassistant.model.dto.PatientDTO;
import it.pharmacywebassistant.model.dto.PersonDTO;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class PersonMapper implements Function<Person, PersonDTO> {
    @Override
    public PersonDTO apply(Person person) {
        if(person instanceof Patient) {
            return convertPatientInPatientDTO(person);
        } else {
            return convertDoctorInDoctorDTO(person);
        }
    }

    private PatientDTO convertPatientInPatientDTO(Person person) {
        return new PatientDTO(person.getTaxCode(), person.getFirstName(), person.getLastName(), person.getDateOfBirth());
    }

    private DoctorDTO convertDoctorInDoctorDTO(Person person) {
        return new DoctorDTO(person.getTaxCode(), person.getFirstName(), person.getLastName(), person.getDateOfBirth());
    }
}
