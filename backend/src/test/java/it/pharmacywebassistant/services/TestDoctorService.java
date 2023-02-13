package it.pharmacywebassistant.services;

import it.pharmacywebassistant.model.Doctor;
import it.pharmacywebassistant.model.Person;
import it.pharmacywebassistant.service.PersonService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public final class TestDoctorService {

    @Autowired
    private PersonService service;

    private static Person doctor;

    @BeforeAll
    public static void beforeAll() {
        doctor = new Doctor("AB123", "Mario", "Rossi", Date.valueOf(LocalDate.now()));
    }

    @Test
    @Order(1)
    public void testGetAllDoctorsReturnsEmptyArray() {
        assertThat(service.findAll().size()).isEqualTo(0);
    }

    @Test @Order(2)
    public void testGetDoctorByTaxCodeReturnsEmptyOptional() {
        assertThat(service.findByTaxCode("AB123").isEmpty()).isEqualTo(true);
    }

    @Test @Order(3)
    public void testAddNewDoctorWithTaxCodeAB123() {
        assertThat(service.save(doctor).getTaxCode()).isEqualTo(doctor.getTaxCode());
    }

    @Test @Order(4)
    public void testGetAllDoctorsReturnsArrayOfSizeOne() {
        assertThat(service.findAll().size()).isEqualTo(1);
    }

    @Test @Order(5)
    public void testGetDoctorByTaxCodeReturnsPresentOptional() {
        assertThat(service.findByTaxCode(doctor.getTaxCode()).isPresent()).isEqualTo(true);
        assertThat(service.findByTaxCode(doctor.getTaxCode()).get().getTaxCode()).isEqualTo(doctor.getTaxCode());
        assertThat(service.findByTaxCode(doctor.getTaxCode()).get().getFirstName()).isEqualTo(doctor.getFirstName());
        assertThat(service.findByTaxCode(doctor.getTaxCode()).get().getLastName()).isEqualTo(doctor.getLastName());
        assertThat(service.findByTaxCode(doctor.getTaxCode()).get().getDateOfBirth()).isEqualTo(doctor.getDateOfBirth());
    }

    @Test @Order(6)
    public void testUpdateDoctorNameReturnsDoctorWithNewName() {
        doctor.setFirstName("Federico");
        assertThat(service.save(doctor).getFirstName()).isEqualTo(doctor.getFirstName());
    }

    @Test @Order(7)
    public void testUpdateDoctorLastNameReturnsDoctorWithNewLastName() {
        doctor.setLastName("Verdi");
        assertThat(service.save(doctor).getLastName()).isEqualTo(doctor.getLastName());
    }

    @Test @Order(8)
    public void testUpdateDoctorDateOfBirthReturnsDoctorWithNewDateOfBirth() {
        doctor.setDateOfBirth(Date.valueOf(LocalDate.now()));
        assertThat(service.save(doctor).getDateOfBirth()).isEqualTo(doctor.getDateOfBirth());
    }

    @Test @Order(9)
    public void testGetAllDoctorsReturnsArrayOfSizeOneWithUpdatedDoctor() {
        assertThat(service.findAll().size()).isEqualTo(1);
    }

    @Test @Order(10)
    public void testGetDoctorByIdReturnsUpdatedDoctor() {
        assertThat(service.findByTaxCode(doctor.getTaxCode()).isPresent()).isEqualTo(true);
        assertThat(service.findByTaxCode(doctor.getTaxCode()).get().getTaxCode()).isEqualTo(doctor.getTaxCode());
        assertThat(service.findByTaxCode(doctor.getTaxCode()).get().getFirstName()).isEqualTo(doctor.getFirstName());
        assertThat(service.findByTaxCode(doctor.getTaxCode()).get().getLastName()).isEqualTo(doctor.getLastName());
        assertThat(service.findByTaxCode(doctor.getTaxCode()).get().getDateOfBirth()).isEqualTo(doctor.getDateOfBirth());
    }

    @Test @Order(11)
    public void testDeleteDoctorAndDoctorListEmpty() {
        service.deleteByTaxCode(doctor.getTaxCode());
        assertThat(service.findAll().isEmpty()).isEqualTo(true);
    }

    @Test @Order(12)
    public void testAddNewDoctorWithTaxCodeAC456() {
        doctor.setTaxCode("AC456");
        assertThat(service.save(doctor).getTaxCode()).isEqualTo(doctor.getTaxCode());
    }

    @Test @Order(10)
    public void testGetDoctorByIdReturnsNewDoctor() {
        assertThat(service.findByTaxCode(doctor.getTaxCode()).isPresent()).isEqualTo(true);
        assertThat(service.findByTaxCode(doctor.getTaxCode()).get().getTaxCode()).isEqualTo(doctor.getTaxCode());
        assertThat(service.findByTaxCode(doctor.getTaxCode()).get().getFirstName()).isEqualTo(doctor.getFirstName());
        assertThat(service.findByTaxCode(doctor.getTaxCode()).get().getLastName()).isEqualTo(doctor.getLastName());
        assertThat(service.findByTaxCode(doctor.getTaxCode()).get().getDateOfBirth()).isEqualTo(doctor.getDateOfBirth());
    }


    @Test @Order(13)
    public void testDeleteAllDoctorsAndDoctorListEmpty() {
        service.deleteAll();
        assertThat(service.findAll().isEmpty()).isEqualTo(true);
    }
}
