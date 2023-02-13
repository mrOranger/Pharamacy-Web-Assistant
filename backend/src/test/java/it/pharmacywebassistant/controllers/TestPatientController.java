package it.pharmacywebassistant.controllers;

import it.pharmacywebassistant.PharmacyWebAssistantApplication;
import it.pharmacywebassistant.service.PersonService;
import lombok.SneakyThrows;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Date;
import java.time.LocalDate;

@ContextConfiguration(classes = PharmacyWebAssistantApplication.class)
@SpringBootTest @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public final class TestPatientController {
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private PersonService service;

    private MockMvc mockMvc;
    private JSONObject patient;

    @BeforeEach @SneakyThrows
    public void beforeAll() {

        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        patient = new JSONObject();

        patient.put("taxCode", "AB123")
                .put("firstName", "Mario")
                .put("lastName", "Rossi")
                .put("dateOfBirth", Date.valueOf(LocalDate.now()));
    }

    @Test @Order(1)
    public void testGetAllPatientsReturnsMessageWithNotFoundCode() {

    }

    @Test @Order(2)
    public void testGetPatientByIdReturnsMessageWithNotFoundCode() {

    }

    @Test @Order(3)
    public void testPostNewPatientReturnsMessageWithOkCode() {

    }

    @Test @Order(4)
    public void testPostNewPatientReturnsMessageWithConflictCode() {

    }

    @Test @Order(5)
    public void testPostNewPatientReturnsMessageWithBadRequestCode() {

    }

    @Test @Order(6)
    public void testPutPatientReturnsMessageWithOkCode() {

    }

    @Test @Order(7)
    public void testPutPatientReturnsMessageWithBadRequestCode() {

    }

    @Test @Order(8)
    public void testPutPatientReturnsMessageWithNotFoundCode() {

    }

    @Test @Order(9)
    public void testDeletePatientByIdReturnsMessageWithOkCode() {

    }

    @Test @Order(10)
    public void testDeletePatientByIdReturnsMessageWithNotFoundCode() {

    }

    @Test @Order(11)
    public void testDeleteAllPatientsReturnsMessageWithOkCode() {

    }

    @Test @Order(12)
    public void testDeleteAllPatientsReturnsMessageWithConflictCode() {

    }
}
