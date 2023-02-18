package it.pharmacywebassistant.services;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestDrugsInPrescriptionService {
	
	
	@Test @Order(1)
	public void testGetAllDrugsInPrescriptionNotExistsReturnsNull() {
		
	}
	
	@Test @Order(1)
	public void testGetDrugByIdFromNotExistPrescriptionReturnsNull() {
		
	}
	
	@Test @Order(2)
	public void testAddNewPrescriptionWithNoDrugs() {
		
	}
	
	@Test @Order(3)
	public void testAddNewDrugInPrescription() {
		
	}
	
	@Test @Order(4)
	public void testUpdateDrugInPrescription() {
		
	}
	
	@Test @Order(5)
	public void testDeleteDrugInPrescriptionById() {
		
	}
	
	@Test @Order(6)
	public void testDeleteAllDrugsInPrescription() {
		
	}
}
