package it.pharmacywebassistant.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;
import java.time.LocalDate;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import it.pharmacywebassistant.PharmacyWebAssistantApplication;
import lombok.SneakyThrows;

@ContextConfiguration(classes = PharmacyWebAssistantApplication.class)
@SpringBootTest @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public final class TestDrugsInPrescriptionController {
	
    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;
    private JSONObject prescription;
    private JSONObject patient;
    private JSONObject doctor;
    private JSONObject drug;
    private JSONArray drugs;
    
    @BeforeEach @SneakyThrows
    public void beforeEach() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        
        patient = new JSONObject();
        patient.put("taxCode", "AB123");
        patient.put("firstName", "Mario");
        patient.put("lastName", "Rossi");
        patient.put("dateOfBirth", Date.valueOf(LocalDate.now()));
        
        doctor = new JSONObject();
        doctor.put("taxCode", "AK123");
        doctor.put("firstName", "Valerio");
        doctor.put("lastName", "Rossi");
        doctor.put("dateOfBirth", Date.valueOf(LocalDate.now()));
        
        drug = new JSONObject();
        drug.put("id", 1L)
        	.put("name", "Product")
        	.put("description", "Product")
        	.put("cost", 1000.00)
        	.put("hasPrescription", true)
        	.put("company", new JSONObject()
                .put("id", 1)
                .put("name", "Prova")
                .put("address", new JSONObject()
                		.put("id", 1)
                        .put("streetName", "Prova")
                        .put("streetCode", 2)
                        .put("city", "Prova")
                        .put("nation", "Prova")));
        
        drugs = new JSONArray();
        drugs.put(drug);
        
        prescription = new JSONObject();
        prescription.put("id", 1L);
        prescription.put("patient", patient.toString());
        prescription.put("doctor", doctor.toString());
        prescription.put("drugs", drugs);
    }
    
    @Test @Order(1) @SneakyThrows
    public void testGetAllDrugsInPrescription() {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/prescriptions/1/drugs/")
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()))
        .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
        .andExpect(jsonPath("$.message").value("Prescrizione 1 non presente nel Database!"))
        .andDo(print());	
    }

    @Test @Order(2) @SneakyThrows
    public void testGetDrugInPrescription() {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/prescriptions/1/drugs/1")
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()))
        .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
        .andExpect(jsonPath("$.message").value("Prescrizione 1 non presente nel Database!"))
        .andDo(print());    	
    }
    
    @Test @Order(3)
    public void testPostNewDrugInPrescription() {
    	
    }
    
    @Test @Order(4)
    public void testPutDrugInPrescription() {
    	
    }
    
    @Test @Order(5)
    public void testDeleteDrugInPrescriptionById() {
    	
    }
    
    @Test @Order(6)
    public void testDeleteAllDrugsInPrescription() {
    	
    }
}
