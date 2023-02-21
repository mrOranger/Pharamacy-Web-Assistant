package it.pharmacywebassistant.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import it.pharmacywebassistant.PharmacyWebAssistantApplication;

@ContextConfiguration(classes = PharmacyWebAssistantApplication.class)
@SpringBootTest @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public final class TestDrugsInPrescriptionController {
	
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;
    
    @BeforeEach
    public void beforeEach() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    
    @Test @Order(1)
    public void testGetAllDrugsInPrescription() {
    	
    }

    @Test @Order(2)
    public void testGetDrugsInPrescription() {
    	
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
