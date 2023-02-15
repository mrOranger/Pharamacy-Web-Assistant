package it.pharmacywebassistant.controllers;

import it.pharmacywebassistant.PharmacyWebAssistantApplication;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = PharmacyWebAssistantApplication.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public final class TestPrescriptionController {

    @BeforeAll
    public static void beforeAll() {

    }

    @Test @Order(1)
    public void testGetAllPrescriptionsReturnsMessageWithStatusNotFound() {

    }

    @Test @Order(2)
    public void testGetAllPrescriptionsOfAPatientReturnsMessageWithStatusCodeNotFound() {

    }

    @Test @Order(3)
    public void testGetAllPrescriptionsOfADoctorReturnsMessageWithStatusCodeNotFound() {

    }

    @Test @Order(4)
    public void testGetPrescriptionByIdReturnsMessageWithStatusCodeNotFound() {

    }

    @Test @Order(5)
    public void testPostNewPrescriptionReturnsMessageWithStatusCodeOk() {

    }

    @Test @Order(6)
    public void testPostNewPrescriptionReturnsMessageWithStatusCodeBadRequest() {

    }

    @Test @Order(7)
    public void testPostNewPrescriptionReturnsMessageWithStatusCodeConflict() {

    }

    @Test @Order(8)
    public void testPutPrescriptionReturnsMessageWithStatusCodeOk() {

    }

    @Test @Order(9)
    public void testPutPrescriptionReturnsMessageWithStatusCodeBadRequest() {

    }

    @Test @Order(10)
    public void testPutPrescriptionReturnsMessageWithStatusCodeNotFound() {

    }

    @Test @Order(11)
    public void testPutPatientOfAPrescriptionReturnsMessageWithStatusCodeOk() {

    }

    @Test @Order(12)
    public void testPutPatientOfAPrescriptionReturnsMessageWithStatusCodeBadRequest() {

    }

    @Test @Order(13)
    public void testPutPatientOfAPrescriptionReturnsMessageWithStatusCodeNotFound() {

    }

    @Test @Order(14)
    public void testPutDoctorOfAPrescriptionReturnsMessageWithStatusCodeOk() {

    }

    @Test @Order(15)
    public void testPutDoctorOfAPrescriptionReturnsMessageWithStatusCodeBadRequest() {

    }

    @Test @Order(16)
    public void testPutDoctorOfAPrescriptionReturnsMessageWithStatusCodeNotFound() {

    }

    @Test @Order(17)
    public void testDeleteAPrescriptionReturnsMessageWithStatusCodeOk(){

    }

    @Test @Order(18)
    public void testDeleteAPrescriptionReturnsMessageWithStatusCodeNotFound() {

    }
}
