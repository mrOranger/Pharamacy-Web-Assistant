package it.pharmacywebassistant.services;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public final class TestPatientService {

    @BeforeEach
    public void beforeEach() {

    }

    @Test @Order(1)
    public void testGetAllPatientsReturnsEmptyArray() {

    }

    @Test @Order(2)
    public void testGetPatientByTaxCodeReturnsEmptyOptional() {

    }

    @Test @Order(3)
    public void testAddNewPatientWithTaxCodeAB123() {

    }

    @Test @Order(4)
    public void testGetAllPatientsReturnsArrayOfSizeOne() {

    }

    @Test @Order(5)
    public void testGetPatientByTaxCodeReturnsPresentOptional() {

    }

    @Test @Order(6)
    public void testUpdatePatientNameReturnsPatientWithNewName() {

    }

    @Test @Order(7)
    public void testUpdatePatientLastNameReturnsPatientWithNewLastName() {

    }

    @Test @Order(8)
    public void testUpdatePatientDateOfBirthReturnsPatientWithNewDateOfBirth() {

    }

    @Test @Order(9)
    public void testGetAllPatientsReturnsArrayOfSizeOneWithUpdatedPatient() {

    }

    @Test @Order(10)
    public void testGetPatientByIdReturnsUpdatedPatient() {

    }

    @Test @Order(11)
    public void testDeletePatientAndPatientListEmpty() {

    }

    @Test @Order(12)
    public void testAddNewPatientWithTaxCodeAC456() {

    }

    @Test @Order(13)
    public void testDeleteAllPatientsAndPatientListEmpty() {

    }
}
