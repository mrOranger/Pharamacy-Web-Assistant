package it.pharmacywebassistant.controllers;

import it.pharmacywebassistant.PharmacyWebAssistantApplication;
import lombok.SneakyThrows;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isA;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = PharmacyWebAssistantApplication.class)
@SpringBootTest @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public final class TestDoctorController {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;
    private JSONObject patient;

    @BeforeEach @SneakyThrows
    public void beforeAll() {

        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        patient = new JSONObject();

        patient.put("taxCode", "AB123")
                .put("firstName", "Mario")
                .put("lastName", "Rossi")
                .put("dateOfBirth", Date.valueOf(LocalDate.now()).toString());
    }

    @Test @Order(1) @SneakyThrows
    public void testGetAllPatientsReturnsMessageWithNotFoundCode() {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/doctors/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.message").value("Nessun Dottore registrato nel Database!"))
                .andDo(print());
    }

    @Test @Order(2) @SneakyThrows
    public void testGetPatientByIdReturnsMessageWithNotFoundCode() {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/doctors/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.message").value("Nessun Dottore registrato con Codice Fiscale 1!"))
                .andDo(print());
    }

    @Test @Order(3) @SneakyThrows
    public void testPostNewPatientReturnsMessageWithOkCode() {
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/api/doctors/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(patient))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.message").value("Dottore registrato con successo!"))
                .andDo(print());

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/doctors/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", isA(List.class)))
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andDo(print());

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/doctors/AB123")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.taxCode").value(patient.get("taxCode")))
                .andExpect(jsonPath("$.firstName").value(patient.get("firstName")))
                .andExpect(jsonPath("$.lastName").value(patient.get("lastName")))
                .andExpect(jsonPath("$.dateOfBirth").value(patient.get("dateOfBirth")))
                .andDo(print());
    }

    @Test @Order(4) @SneakyThrows
    public void testPostNewPatientReturnsMessageWithConflictCode() {
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/api/doctors/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(patient))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value(HttpStatus.CONFLICT.value()))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.message").value("Il Dottore e' gia' registrato nel Database, usare metodo PUT per modificarlo!"))
                .andDo(print());
    }

    @Test @Order(5) @SneakyThrows
    public void testPostNewPatientWithoutTaxCodeReturnsMessageWithBadRequestCode() {

        patient.remove("taxCode");
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/api/doctors/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(patient))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.message").value("Il Codice Fiscale non può essere nullo!"))
                .andDo(print());

    }

    @Test @Order(6) @SneakyThrows
    public void testPostNewPatientWithoutFirstNameReturnsMessageWithBadRequestCode() {

        patient.remove("firstName");
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/api/doctors/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(patient))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.message").value("Il Nome non può essere nullo!"))
                .andDo(print());

    }

    @Test @Order(7) @SneakyThrows
    public void testPostNewPatientWithoutLastNameReturnsMessageWithBadRequestCode() {

        patient.remove("lastName");
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/api/doctors/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(patient))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.message").value("Il Cognome non può essere nullo!"))
                .andDo(print());

    }

    @Test @Order(7) @SneakyThrows
    public void testPostNewPatientWithoutDateOfBirthReturnsMessageWithBadRequestCode() {

        patient.remove("dateOfBirth");
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/api/doctors/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(patient))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.message").value("La Data di Nascita non puo' essere nulla!"))
                .andDo(print());

    }

    @Test @Order(8) @SneakyThrows
    public void testPutPatientReturnsMessageWithOkCode() {
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/api/doctors/AB123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(patient))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.message").value("Dottore modificato con successo!"))
                .andDo(print());
    }

    @Test @Order(9) @SneakyThrows
    public void testPutPatientWithoutFirstNameReturnsMessageWithBadRequestCode() {
        patient.remove("firstName");
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/api/doctors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(patient))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.message").value("Il Nome non può essere nullo!"))
                .andDo(print());
    }

    @Test @Order(10) @SneakyThrows
    public void testPutPatientWithoutLastNameReturnsMessageWithBadRequestCode() {
        patient.remove("lastName");
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/api/doctors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(patient))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.message").value("Il Cognome non può essere nullo!"))
                .andDo(print());
    }

    @Test @Order(11) @SneakyThrows
    public void testPutPatientWithoutBirthDateReturnsMessageWithBadRequestCode() {
        patient.remove("dateOfBirth");
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/api/doctors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(patient))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.message").value("La Data di Nascita non puo' essere nulla!"))
                .andDo(print());
    }

    @Test @Order(12) @SneakyThrows
    public void testPutPatientReturnsMessageWithConflictCode() {
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/api/doctors/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(patient))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value(HttpStatus.CONFLICT.value()))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.message").value("Dottore non registrato, usare metodo POST per registrarlo!"))
                .andDo(print());
    }

    @Test @Order(13) @SneakyThrows
    public void testDeletePatientByIdReturnsMessageWithOkCode() {
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/api/doctors/AB123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.message").value("Dottore eliminato con successo!"))
                .andDo(print());

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/doctors/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.message").value("Nessun Dottore registrato nel Database!"))
                .andDo(print());

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/doctors/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.message").value("Nessun Dottore registrato con Codice Fiscale 1!"))
                .andDo(print());
    }
}
