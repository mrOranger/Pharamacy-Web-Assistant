package it.pharmacywebassistant.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isA;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

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
        
        prescription = new JSONObject();
        prescription.put("id", 1L);
        prescription.put("patient", patient);
        prescription.put("doctor", doctor);
        prescription.put("drugs", drugs);
    }
    
    @Test @Order(1) @SneakyThrows
    public void testGetAllDrugsInPrescriptionReturnsPrescriptionNotFoundMessage() {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/prescriptions/1/drugs/")
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()))
        .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
        .andExpect(jsonPath("$.message").value("Prescrizione 1 non presente nel Database!"))
        .andDo(print());	
    }

    @Test @Order(2) @SneakyThrows
    public void testGetDrugInPrescriptionReturnsPrescriptionNotFoundMessage() {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/prescriptions/1/drugs/1")
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()))
        .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
        .andExpect(jsonPath("$.message").value("Prescrizione 1 non presente nel Database!"))
        .andDo(print());    	
    }
    
    @Test @Order(3) @SneakyThrows
    public void testPostNewPrescription() {
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/api/prescriptions/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(prescription))
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").value(HttpStatus.OK.value()))
        .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
        .andExpect(jsonPath("$.message").value("Prescrizione registrata correttamente nel Database!"))
        .andDo(print());	
    }
    
    @Test @Order(4) @SneakyThrows
    public void testGetAllDrugsInPrescriptionReturnsDrugsNotFoundMessage() {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/prescriptions/1/drugs/")
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()))
        .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
        .andExpect(jsonPath("$.message").value("La Prescrizione 1 non contiene Farmaci prescritti!"))
        .andDo(print());	
    }

    @Test @Order(5) @SneakyThrows
    public void testGetDrugInPrescriptionReturnsDrugNotFoundMessage() {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/prescriptions/1/drugs/1")
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()))
        .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
        .andExpect(jsonPath("$.message").value("La Prescrizione 1 non contiene il Farmaco 1!"))
        .andDo(print());    	
    }
    
    @Test @Order(6) @SneakyThrows
    public void testPostNewDrugInPrescritionReturnsOkMessage() {
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/api/prescriptions/1/drugs/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(drug))
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").value(HttpStatus.OK.value()))
        .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
        .andExpect(jsonPath("$.message").value("Farmaco registrato correttamente all'interno della Prescrizione!"))
        .andDo(print());    
        
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/prescriptions/1/drugs/")
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.*", isA(List.class)))
        .andExpect(jsonPath("$.*", hasSize(1)))
        .andDo(print());
        
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/prescriptions/1/drugs/1")
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json(drug.toString()))
        .andDo(print());   
    }
    
    @Test @Order(7) @SneakyThrows
    public void testPostNewDrugInPrescritionReturnsBadRequestMessage() {
    	
    	drug.remove("name");
    	
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/api/prescriptions/1/drugs/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(drug))
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
        .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
        .andExpect(jsonPath("$.message").value("Il nome del prodotto non può essere nullo"))
        .andDo(print());    
        
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/prescriptions/1/drugs/")
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.*", isA(List.class)))
        .andExpect(jsonPath("$.*", hasSize(1)))
        .andDo(print());
        
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/prescriptions/1/drugs/1")
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json(drug.toString()))
        .andDo(print());   
        
        drug.put("name", "Product");
    }
    
    @Test @Order(8) @SneakyThrows
    public void testPostNewDrugInPrescritionReturnsConflictMessage() {
    	
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/api/prescriptions/1/drugs/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(drug))
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.code").value(HttpStatus.CONFLICT.value()))
        .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
        .andExpect(jsonPath("$.message").value("Il Farmaco e' gia' registrato all'interno della Prescrizione Medica, usare metodo PUT per modificarlo."))
        .andDo(print());    
        
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/prescriptions/1/drugs/")
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.*", isA(List.class)))
        .andExpect(jsonPath("$.*", hasSize(1)))
        .andDo(print());
        
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/prescriptions/1/drugs/1")
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json(drug.toString()))
        .andDo(print());   
    }
    
    @Test @Order(9) @SneakyThrows
    public void testPutDrugInPrescriptionReturnsOkMessage () {
    	
    	drug.remove("id");
    	
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/api/prescriptions/1/drugs/1/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(drug))
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").value(HttpStatus.OK.value()))
        .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
        .andExpect(jsonPath("$.message").value("Farmaco modificato correttamente all'interno della Prescrizione!"))
        .andDo(print());    
        
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/prescriptions/1/drugs/")
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.*", isA(List.class)))
        .andExpect(jsonPath("$.*", hasSize(1)))
        .andDo(print());
        
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/prescriptions/1/drugs/1")
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json(drug.toString()))
        .andDo(print());  
    }
    
    @Test @Order(10) @SneakyThrows
    public void testPutDrugInPrescriptionReturnsNotFoundMessage() {
    	
    	drug.remove("id");
    	
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/api/prescriptions/1/drugs/2/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(drug))
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()))
        .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
        .andExpect(jsonPath("$.message").value("Il Farmaco non e' registrato all'interno della Prescrizione Medica, usare metodo POST per registrarlo."))
        .andDo(print());    
        
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/prescriptions/1/drugs/")
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.*", isA(List.class)))
        .andExpect(jsonPath("$.*", hasSize(1)))
        .andDo(print());
        
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/prescriptions/1/drugs/1")
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json(drug.toString()))
        .andDo(print());  
        
        drug.put("id", 1);
    }
    
    @Test @Order(11) @SneakyThrows
    public void testDeleteDrugInPrescriptionById() {
    	
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/api/prescriptions/1/drugs/1/")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").value(HttpStatus.OK.value()))
        .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
        .andExpect(jsonPath("$.message").value("Farmaco eliminato correttamente dalla Prescrizione!"))
        .andDo(print()); 
        
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/api/prescriptions/1/drugs/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(drug))
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").value(HttpStatus.OK.value()))
        .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
        .andExpect(jsonPath("$.message").value("Farmaco registrato correttamente all'interno della Prescrizione!"))
        .andDo(print());    
    }
    
    @Test @Order(12) @SneakyThrows
    public void testDeleteAllDrugsInPrescriptionReturnsOkMessage() {
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/api/prescriptions/1/drugs/")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").value(HttpStatus.OK.value()))
        .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
        .andExpect(jsonPath("$.message").value("Farmaci eliminati correttamente dalla Prescrizione!"))
        .andDo(print());    
        
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/prescriptions/1/drugs/1")
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()))
        .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
        .andExpect(jsonPath("$.message").value("La Prescrizione 1 non contiene il Farmaco 1!"))
        .andDo(print());    	
        
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/prescriptions/1/drugs/")
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()))
        .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
        .andExpect(jsonPath("$.message").value("La Prescrizione 1 non contiene Farmaci prescritti!"))
        .andDo(print());	
    }
}
