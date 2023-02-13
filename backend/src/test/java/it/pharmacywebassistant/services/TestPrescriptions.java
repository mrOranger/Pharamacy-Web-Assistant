package it.pharmacywebassistant.services;


import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public final class TestPrescriptions {

    @BeforeAll
    public void beforeAll() {

    }

    @Test @Order(1)
    public void testGetAllPrescriptionsReturnsArrayOfSizeZero() {

    }

    @Test @Order(2)
    public void testGetPrescriptionByIdReturnsEmptyOptional() {

    }

    @Test @Order(3)
    public void testSaveNewPrescriptionReturnsANewPrescriptionWithIdOne() {

    }

    @Test @Order(4)
    public void testGetAllPrescriptionsReturnsArrayContainingPrescriptionWithIdOne() {

    }

    @Test @Order(5)
    public void testGetPrescriptionByIdReturnsOptionalWithPrescriptionWithIdOne() {

    }

    @Test @Order(6)
    public void testSavePrescriptionReturnsAPrescriptionWithIdTwo() {

    }

    @Test @Order(7)
    public void testGetAllPrescriptionsReturnsArrayContainingPrescriptionWithIdTwo() {

    }

    @Test @Order(8)
    public void testGetPrescriptionByIdReturnsOptionalWithPrescriptionWithIdTwo() {

    }

    @Test @Order(9)
    public void testDeletePrescriptionWithIdTwo() {

    }

    @Test @Order(10)
    public void testGetAllPrescriptionsReturnsArrayOfSizeZeroAgain() {

    }

    @Test @Order(11)
    public void testGetPrescriptionByIdReturnsEmptyOptionalAgain() {

    }
}
