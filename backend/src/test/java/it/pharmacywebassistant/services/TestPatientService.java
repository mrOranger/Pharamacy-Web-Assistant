package it.pharmacywebassistant.services;

import it.pharmacywebassistant.model.Patient;
import it.pharmacywebassistant.model.Person;
import it.pharmacywebassistant.service.PersonService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public final class TestPatientService {

    @Autowired
    private PersonService service;

    private static Person patient;

    @BeforeAll
    public static void beforeAll() {
        patient = new Patient("AB123", "Mario", "Rossi", Date.valueOf(LocalDate.now()));
    }

    @Test @Order(1)
    public void testGetAllPatientsReturnsEmptyArray() {
        assertThat(service.findAll().size()).isEqualTo(0);
    }

    @Test @Order(2)
    public void testGetPatientByTaxCodeReturnsEmptyOptional() {
        assertThat(service.findByTaxCode("AB123").isEmpty());
    }

    @Test @Order(3)
    public void testAddNewPatientWithTaxCodeAB123() {
        assertThat(service.save(patient).getTaxCode()).isEqualTo("AB123");
    }

    @Test @Order(4)
    public void testGetAllPatientsReturnsArrayOfSizeOne() {
        assertThat(service.findAll().size()).isEqualTo(1);
    }

    @Test @Order(5)
    public void testGetPatientByTaxCodeReturnsPresentOptional() {
        assertThat(service.findByTaxCode(patient.getTaxCode()).isPresent());
        assertThat(service.findByTaxCode(patient.getTaxCode()).get().getTaxCode()).isEqualTo(patient.getTaxCode());
        assertThat(service.findByTaxCode(patient.getTaxCode()).get().getFirstName()).isEqualTo(patient.getFirstName());
        assertThat(service.findByTaxCode(patient.getTaxCode()).get().getLastName()).isEqualTo(patient.getLastName());
        assertThat(service.findByTaxCode(patient.getTaxCode()).get().getDateOfBirth()).isEqualTo(patient.getDateOfBirth());
    }

    @Test @Order(6)
    public void testUpdatePatientNameReturnsPatientWithNewName() {
        patient.setFirstName("Federico");
        assertThat(service.save(patient).getFirstName()).isEqualTo(patient.getFirstName());
    }

    @Test @Order(7)
    public void testUpdatePatientLastNameReturnsPatientWithNewLastName() {
        patient.setLastName("Verdi");
        assertThat(service.save(patient).getLastName()).isEqualTo(patient.getLastName());
    }

    @Test @Order(8)
    public void testUpdatePatientDateOfBirthReturnsPatientWithNewDateOfBirth() {
        patient.setDateOfBirth(Date.valueOf(LocalDate.now()));
        assertThat(service.save(patient).getDateOfBirth()).isEqualTo(patient.getDateOfBirth());
    }

    @Test @Order(9)
    public void testGetAllPatientsReturnsArrayOfSizeOneWithUpdatedPatient() {
        assertThat(service.findAll().size()).isEqualTo(1);
    }

    @Test @Order(10)
    public void testGetPatientByIdReturnsUpdatedPatient() {
        assertThat(service.findByTaxCode(patient.getTaxCode()).isPresent());
        assertThat(service.findByTaxCode(patient.getTaxCode()).get().getTaxCode()).isEqualTo(patient.getTaxCode());
        assertThat(service.findByTaxCode(patient.getTaxCode()).get().getFirstName()).isEqualTo(patient.getFirstName());
        assertThat(service.findByTaxCode(patient.getTaxCode()).get().getLastName()).isEqualTo(patient.getLastName());
        assertThat(service.findByTaxCode(patient.getTaxCode()).get().getDateOfBirth()).isEqualTo(patient.getDateOfBirth());
    }

    @Test @Order(11)
    public void testDeletePatientAndPatientListEmpty() {
        service.deleteByTaxCode("AB123");
        assertThat(service.findAll().isEmpty());
    }

    @Test @Order(12)
    public void testAddNewPatientWithTaxCodeAC456() {
        patient.setTaxCode("AC456");
        assertThat(service.save(patient).getTaxCode()).isEqualTo(patient.getTaxCode());
    }

    @Test @Order(10)
    public void testGetPatientByIdReturnsNewPatient() {
        assertThat(service.findByTaxCode(patient.getTaxCode()).isPresent());
        assertThat(service.findByTaxCode(patient.getTaxCode()).get().getTaxCode()).isEqualTo(patient.getTaxCode());
        assertThat(service.findByTaxCode(patient.getTaxCode()).get().getFirstName()).isEqualTo(patient.getFirstName());
        assertThat(service.findByTaxCode(patient.getTaxCode()).get().getLastName()).isEqualTo(patient.getLastName());
        assertThat(service.findByTaxCode(patient.getTaxCode()).get().getDateOfBirth()).isEqualTo(patient.getDateOfBirth());
    }


    @Test @Order(13)
    public void testDeleteAllPatientsAndPatientListEmpty() {
        service.deleteAll();
        assertThat(service.findAll().isEmpty());
    }
}
