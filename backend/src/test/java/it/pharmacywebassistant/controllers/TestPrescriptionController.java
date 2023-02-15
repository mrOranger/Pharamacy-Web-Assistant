package it.pharmacywebassistant.controllers;

import it.pharmacywebassistant.PharmacyWebAssistantApplication;
import it.pharmacywebassistant.service.PrescriptionService;
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
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public final class TestPrescriptionController {

    @Autowired
    private static WebApplicationContext context;

    @Autowired
    private PrescriptionService service;

    private MockMvc mockMvc;
    private JSONObject prescription;
    private JSONObject doctor;
    private JSONObject patient;

    @BeforeEach @SneakyThrows
    public void beforeEach() {

        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        patient = new JSONObject();
        patient.put("taxCode", "AB123")
                .put("firstName", "Mario")
                .put("lastName", "Rossi")
                .put("dateOfBirth", Date.valueOf(LocalDate.now()).toString());

        doctor = new JSONObject();
        patient.put("taxCode", "LO123")
                .put("firstName", "Federico")
                .put("lastName", "Verdi")
                .put("dateOfBirth", Date.valueOf(LocalDate.now()).toString());

        prescription = new JSONObject();
        prescription.put("id", "1")
                    .put("doctor", doctor)
                    .put("patient", patient);
    }

    @Test @Order(1) @SneakyThrows
    public void testGetAllPrescriptionsReturnsMessageWithStatusNotFound() {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/prescriptions/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.message").value("Nessuna Prescrizione Medica correttamente registrata nel Database!"))
                .andDo(print());
    }

    @Test @Order(2) @SneakyThrows
    public void testGetAllPrescriptionsOfAPatientReturnsMessageWithStatusCodeNotFound() {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/prescriptions/patient/AB123")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.message").value("Nessuna Prescrizione Medica, per il Paziente AB123 correttamente registrata nel Database!"))
                .andDo(print());
    }

    @Test @Order(3) @SneakyThrows
    public void testGetAllPrescriptionsOfADoctorReturnsMessageWithStatusCodeNotFound() {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/prescriptions/doctor/LO123")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.message").value("Nessuna Prescrizione Medica, per il Dottore LO123 correttamente registrata nel Database!"))
                .andDo(print());
    }

    @Test @Order(4) @SneakyThrows
    public void testGetPrescriptionByIdReturnsMessageWithStatusCodeNotFound() {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/prescriptions/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.message").value("Nessuna Prescrizione Medica con ID 1 registrata nel Database!"))
                .andDo(print());
    }

    @Test @Order(5) @SneakyThrows
    public void testPostNewPrescriptionReturnsMessageWithStatusCodeOk() {
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/api/prescriptions/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(prescription))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.message").value("Prescrizione registrata correttamente nel Database!"))
                .andDo(print());

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/prescriptions/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", isA(List.class)))
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andDo(print());

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/prescriptions/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(prescription.get("id")))
                .andExpect(jsonPath("$.patient.taxCode").value(patient.get("taxCode")))
                .andExpect(jsonPath("$.patient.firstName").value(patient.get("firstName")))
                .andExpect(jsonPath("$.patient.lastName").value(patient.get("lastName")))
                .andExpect(jsonPath("$.patient.dateOfBirth").value(patient.get("dateOfBirth")))
                .andExpect(jsonPath("$.doctor.taxCode").value(doctor.get("taxCode")))
                .andExpect(jsonPath("$.doctor.firstName").value(doctor.get("firstName")))
                .andExpect(jsonPath("$.doctor.lastName").value(doctor.get("lastName")))
                .andExpect(jsonPath("$.doctor.dateOfBirth").value(doctor.get("dateOfBirth")))
                .andDo(print());
    }

    @Test @Order(6) @SneakyThrows
    public void testPostNewPrescriptionReturnsMessageWithStatusCodeBadRequest() {

        prescription.remove("doctor");

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/api/prescriptions/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(prescription))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.message").value("Il formato della Prescrizione Medica non è valido!"))
                .andDo(print());

        prescription.put("doctor", doctor);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/prescriptions/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", isA(List.class)))
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andDo(print());

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/prescriptions/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(prescription.get("id")))
                .andExpect(jsonPath("$.patient.taxCode").value(patient.get("taxCode")))
                .andExpect(jsonPath("$.patient.firstName").value(patient.get("firstName")))
                .andExpect(jsonPath("$.patient.lastName").value(patient.get("lastName")))
                .andExpect(jsonPath("$.patient.dateOfBirth").value(patient.get("dateOfBirth")))
                .andExpect(jsonPath("$.doctor.taxCode").value(doctor.get("taxCode")))
                .andExpect(jsonPath("$.doctor.firstName").value(doctor.get("firstName")))
                .andExpect(jsonPath("$.doctor.lastName").value(doctor.get("lastName")))
                .andExpect(jsonPath("$.doctor.dateOfBirth").value(doctor.get("dateOfBirth")))
                .andDo(print());
    }

    @Test @Order(7) @SneakyThrows
    public void testPostNewPrescriptionReturnsMessageWithStatusCodeConflict() {
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/api/prescriptions/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(prescription))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value(HttpStatus.CONFLICT.value()))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.message").value("La Prescrizione Medica è già registrata, usare metodo PUT per modificarla"))
                .andDo(print());

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/prescriptions/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", isA(List.class)))
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andDo(print());

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/prescriptions/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(prescription.get("id")))
                .andExpect(jsonPath("$.patient.taxCode").value(patient.get("taxCode")))
                .andExpect(jsonPath("$.patient.firstName").value(patient.get("firstName")))
                .andExpect(jsonPath("$.patient.lastName").value(patient.get("lastName")))
                .andExpect(jsonPath("$.patient.dateOfBirth").value(patient.get("dateOfBirth")))
                .andExpect(jsonPath("$.doctor.taxCode").value(doctor.get("taxCode")))
                .andExpect(jsonPath("$.doctor.firstName").value(doctor.get("firstName")))
                .andExpect(jsonPath("$.doctor.lastName").value(doctor.get("lastName")))
                .andExpect(jsonPath("$.doctor.dateOfBirth").value(doctor.get("dateOfBirth")))
                .andDo(print());
    }

    @Test @Order(8) @SneakyThrows
    public void testPutPrescriptionReturnsMessageWithStatusCodeOk() {

        mockMvc.perform(MockMvcRequestBuilders.put("/v1/api/prescriptions/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(prescription))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.message").value("Prescrizione modificata correttamente nel Database!"))
                .andDo(print());

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/prescriptions/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", isA(List.class)))
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andDo(print());

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/prescriptions/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(prescription.get("id")))
                .andExpect(jsonPath("$.patient.taxCode").value(patient.get("taxCode")))
                .andExpect(jsonPath("$.patient.firstName").value(patient.get("firstName")))
                .andExpect(jsonPath("$.patient.lastName").value(patient.get("lastName")))
                .andExpect(jsonPath("$.patient.dateOfBirth").value(patient.get("dateOfBirth")))
                .andExpect(jsonPath("$.doctor.taxCode").value(doctor.get("taxCode")))
                .andExpect(jsonPath("$.doctor.firstName").value(doctor.get("firstName")))
                .andExpect(jsonPath("$.doctor.lastName").value(doctor.get("lastName")))
                .andExpect(jsonPath("$.doctor.dateOfBirth").value(doctor.get("dateOfBirth")))
                .andDo(print());
    }

    @Test @Order(9) @SneakyThrows
    public void testPutPrescriptionReturnsMessageWithStatusCodeBadRequest() {

        prescription.remove("doctor");

        mockMvc.perform(MockMvcRequestBuilders.put("/v1/api/prescriptions/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(prescription))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.message").value("Il formato della Prescrizione Medica non è valido!"))
                .andDo(print());

        prescription.put("doctor", doctor);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/prescriptions/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", isA(List.class)))
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andDo(print());

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/prescriptions/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(prescription.get("id")))
                .andExpect(jsonPath("$.patient.taxCode").value(patient.get("taxCode")))
                .andExpect(jsonPath("$.patient.firstName").value(patient.get("firstName")))
                .andExpect(jsonPath("$.patient.lastName").value(patient.get("lastName")))
                .andExpect(jsonPath("$.patient.dateOfBirth").value(patient.get("dateOfBirth")))
                .andExpect(jsonPath("$.doctor.taxCode").value(doctor.get("taxCode")))
                .andExpect(jsonPath("$.doctor.firstName").value(doctor.get("firstName")))
                .andExpect(jsonPath("$.doctor.lastName").value(doctor.get("lastName")))
                .andExpect(jsonPath("$.doctor.dateOfBirth").value(doctor.get("dateOfBirth")))
                .andDo(print());
    }

    @Test @Order(10) @SneakyThrows
    public void testPutPrescriptionReturnsMessageWithStatusCodeNotFound() {

        mockMvc.perform(MockMvcRequestBuilders.put("/v1/api/prescriptions/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(prescription))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.message").value("La Prescrizione Medica non è registrata, usare metodo POST per inserirne una nuova"))
                .andDo(print());

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/prescriptions/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", isA(List.class)))
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andDo(print());

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/prescriptions/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(prescription.get("id")))
                .andExpect(jsonPath("$.patient.taxCode").value(patient.get("taxCode")))
                .andExpect(jsonPath("$.patient.firstName").value(patient.get("firstName")))
                .andExpect(jsonPath("$.patient.lastName").value(patient.get("lastName")))
                .andExpect(jsonPath("$.patient.dateOfBirth").value(patient.get("dateOfBirth")))
                .andExpect(jsonPath("$.doctor.taxCode").value(doctor.get("taxCode")))
                .andExpect(jsonPath("$.doctor.firstName").value(doctor.get("firstName")))
                .andExpect(jsonPath("$.doctor.lastName").value(doctor.get("lastName")))
                .andExpect(jsonPath("$.doctor.dateOfBirth").value(doctor.get("dateOfBirth")))
                .andDo(print());

    }

    @Test @Order(11) @SneakyThrows
    public void testPutPatientOfAPrescriptionReturnsMessageWithStatusCodeOk() {
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/api/prescriptions/1/patient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(patient))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.message").value("Paziente della Prescrizione modificata correttamente nel Database!"))
                .andDo(print());

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/prescriptions/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", isA(List.class)))
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andDo(print());

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/prescriptions/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(prescription.get("id")))
                .andExpect(jsonPath("$.patient.taxCode").value(patient.get("taxCode")))
                .andExpect(jsonPath("$.patient.firstName").value(patient.get("firstName")))
                .andExpect(jsonPath("$.patient.lastName").value(patient.get("lastName")))
                .andExpect(jsonPath("$.patient.dateOfBirth").value(patient.get("dateOfBirth")))
                .andExpect(jsonPath("$.doctor.taxCode").value(doctor.get("taxCode")))
                .andExpect(jsonPath("$.doctor.firstName").value(doctor.get("firstName")))
                .andExpect(jsonPath("$.doctor.lastName").value(doctor.get("lastName")))
                .andExpect(jsonPath("$.doctor.dateOfBirth").value(doctor.get("dateOfBirth")))
                .andDo(print());
    }

    @Test @Order(12) @SneakyThrows
    public void testPutPatientOfAPrescriptionReturnsMessageWithStatusCodeBadRequest() {

        patient.remove("firstName");

        mockMvc.perform(MockMvcRequestBuilders.put("/v1/api/prescriptions/1/patient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(patient))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.message").value("Il formato del Paziente della Prescrizione Medica non è valido!"))
                .andDo(print());

        patient.put("firstName", "Mario");

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/prescriptions/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", isA(List.class)))
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andDo(print());

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/prescriptions/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(prescription.get("id")))
                .andExpect(jsonPath("$.patient.taxCode").value(patient.get("taxCode")))
                .andExpect(jsonPath("$.patient.firstName").value(patient.get("firstName")))
                .andExpect(jsonPath("$.patient.lastName").value(patient.get("lastName")))
                .andExpect(jsonPath("$.patient.dateOfBirth").value(patient.get("dateOfBirth")))
                .andExpect(jsonPath("$.doctor.taxCode").value(doctor.get("taxCode")))
                .andExpect(jsonPath("$.doctor.firstName").value(doctor.get("firstName")))
                .andExpect(jsonPath("$.doctor.lastName").value(doctor.get("lastName")))
                .andExpect(jsonPath("$.doctor.dateOfBirth").value(doctor.get("dateOfBirth")))
                .andDo(print());
    }

    @Test @Order(13) @SneakyThrows
    public void testPutPatientOfAPrescriptionReturnsMessageWithStatusCodeNotFound() {
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/api/prescriptions/1/patient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(prescription))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.message").value("La Prescrizione Medica non è registrata, usare metodo POST per inserirne una nuova"))
                .andDo(print());

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/prescriptions/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", isA(List.class)))
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andDo(print());
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/prescriptions/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(prescription.get("id")))
                .andExpect(jsonPath("$.patient.taxCode").value(patient.get("taxCode")))
                .andExpect(jsonPath("$.patient.firstName").value(patient.get("firstName")))
                .andExpect(jsonPath("$.patient.lastName").value(patient.get("lastName")))
                .andExpect(jsonPath("$.patient.dateOfBirth").value(patient.get("dateOfBirth")))
                .andExpect(jsonPath("$.doctor.taxCode").value(doctor.get("taxCode")))
                .andExpect(jsonPath("$.doctor.firstName").value(doctor.get("firstName")))
                .andExpect(jsonPath("$.doctor.lastName").value(doctor.get("lastName")))
                .andExpect(jsonPath("$.doctor.dateOfBirth").value(doctor.get("dateOfBirth")))
                .andDo(print());

    }

    @Test @Order(14) @SneakyThrows
    public void testPutDoctorOfAPrescriptionReturnsMessageWithStatusCodeOk() {

        mockMvc.perform(MockMvcRequestBuilders.put("/v1/api/prescriptions/1/doctor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(doctor))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.message").value("Dottore della Prescrizione modificata correttamente nel Database!"))
                .andDo(print());

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/prescriptions/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", isA(List.class)))
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andDo(print());

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/prescriptions/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(prescription.get("id")))
                .andExpect(jsonPath("$.patient.taxCode").value(patient.get("taxCode")))
                .andExpect(jsonPath("$.patient.firstName").value(patient.get("firstName")))
                .andExpect(jsonPath("$.patient.lastName").value(patient.get("lastName")))
                .andExpect(jsonPath("$.patient.dateOfBirth").value(patient.get("dateOfBirth")))
                .andExpect(jsonPath("$.doctor.taxCode").value(doctor.get("taxCode")))
                .andExpect(jsonPath("$.doctor.firstName").value(doctor.get("firstName")))
                .andExpect(jsonPath("$.doctor.lastName").value(doctor.get("lastName")))
                .andExpect(jsonPath("$.doctor.dateOfBirth").value(doctor.get("dateOfBirth")))
                .andDo(print());
    }

    @Test @Order(15) @SneakyThrows
    public void testPutDoctorOfAPrescriptionReturnsMessageWithStatusCodeBadRequest() {

        doctor.remove("firstName");

        mockMvc.perform(MockMvcRequestBuilders.put("/v1/api/prescriptions/1/doctor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(doctor))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.message").value("Il formato del Dottore della Prescrizione Medica non è valido!"))
                .andDo(print());

        doctor.put("firstName", "Federico");

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/prescriptions/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", isA(List.class)))
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andDo(print());

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/prescriptions/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(prescription.get("id")))
                .andExpect(jsonPath("$.patient.taxCode").value(patient.get("taxCode")))
                .andExpect(jsonPath("$.patient.firstName").value(patient.get("firstName")))
                .andExpect(jsonPath("$.patient.lastName").value(patient.get("lastName")))
                .andExpect(jsonPath("$.patient.dateOfBirth").value(patient.get("dateOfBirth")))
                .andExpect(jsonPath("$.doctor.taxCode").value(doctor.get("taxCode")))
                .andExpect(jsonPath("$.doctor.firstName").value(doctor.get("firstName")))
                .andExpect(jsonPath("$.doctor.lastName").value(doctor.get("lastName")))
                .andExpect(jsonPath("$.doctor.dateOfBirth").value(doctor.get("dateOfBirth")))
                .andDo(print());
    }

    @Test @Order(16) @SneakyThrows
    public void testPutDoctorOfAPrescriptionReturnsMessageWithStatusCodeNotFound() {
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/api/prescriptions/1/doctor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(doctor))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.message").value("La Prescrizione Medica non è registrata, usare metodo POST per inserirne una nuova"))
                .andDo(print());

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/prescriptions/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", isA(List.class)))
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andDo(print());

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/prescriptions/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(prescription.get("id")))
                .andExpect(jsonPath("$.patient.taxCode").value(patient.get("taxCode")))
                .andExpect(jsonPath("$.patient.firstName").value(patient.get("firstName")))
                .andExpect(jsonPath("$.patient.lastName").value(patient.get("lastName")))
                .andExpect(jsonPath("$.patient.dateOfBirth").value(patient.get("dateOfBirth")))
                .andExpect(jsonPath("$.doctor.taxCode").value(doctor.get("taxCode")))
                .andExpect(jsonPath("$.doctor.firstName").value(doctor.get("firstName")))
                .andExpect(jsonPath("$.doctor.lastName").value(doctor.get("lastName")))
                .andExpect(jsonPath("$.doctor.dateOfBirth").value(doctor.get("dateOfBirth")))
                .andDo(print());
    }

    @Test @Order(17) @SneakyThrows
    public void testDeleteAPrescriptionReturnsMessageWithStatusCodeOk(){
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/api/prescriptions/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.message").value("Prescrizione Medica eliminata correttamente dal Database!"))
                .andDo(print());

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/prescriptions/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.message").value("Nessuna Prescrizione Medica correttamente registrata nel Database!"))
                .andDo(print());

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/prescriptions/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.message").value("Nessuna Prescrizione Medica con ID 1 registrata nel Database!"))
                .andDo(print());
    }

    @Test @Order(18) @SneakyThrows
    public void testDeleteAPrescriptionReturnsMessageWithStatusCodeNotFound() {
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/api/prescriptions/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.message").value("Non è stata trovata alcuna Prescizione Medica con ID 1"))
                .andDo(print());

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/prescriptions/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.message").value("Nessuna Prescrizione Medica correttamente registrata nel Database!"))
                .andDo(print());

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/prescriptions/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.message").value("Nessuna Prescrizione Medica con ID 1 registrata nel Database!"))
                .andDo(print());
    }
}
