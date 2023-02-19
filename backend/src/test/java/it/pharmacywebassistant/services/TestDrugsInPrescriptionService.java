package it.pharmacywebassistant.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Date;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import it.pharmacywebassistant.model.Address;
import it.pharmacywebassistant.model.Company;
import it.pharmacywebassistant.model.Doctor;
import it.pharmacywebassistant.model.Drug;
import it.pharmacywebassistant.model.Patient;
import it.pharmacywebassistant.model.Prescription;
import it.pharmacywebassistant.model.dto.PrescriptionDTO;
import it.pharmacywebassistant.service.DrugsInPrescriptionService;
import it.pharmacywebassistant.service.PrescriptionService;

@SpringBootTest @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestDrugsInPrescriptionService {
	
	@Autowired
	private DrugsInPrescriptionService drugsInPrescriptionService;
	
	@Autowired
	private PrescriptionService prescriptionService;
	
	private static Prescription prescription;
	private static Drug drug;
	private static Company company;
	private static Address address;
	
	@BeforeAll
	public static void beforeAll() {
		prescription = new Prescription();
		prescription.setId(1L);
		prescription.setDoctor(new Doctor("AB123", "Mario", "Rossi", Date.valueOf(LocalDate.now())));
		prescription.setPatient(new Patient("AK123", "Federico", "Verdi", Date.valueOf(LocalDate.now())));
		
		
		address = new Address("Address", 1L, "Address", "Address");
		
		company = new Company();
		company.setName("Company");
		company.setAddress(address);
		
		drug = new Drug();
		drug.setId(1L);
		drug.setName("Drug");
		drug.setCost(20.0);
		drug.setDescription("Drug");
		drug.setHasPrescription(true);
		drug.setCompany(company);
	}
	
	
	@Test @Order(1)
	public void testGetAllDrugsInPrescriptionNotExistingReturnsNull() {
		assertThat(drugsInPrescriptionService.findAllDrugs(prescription.getId())).isEqualTo(null);
	}
	
	@Test @Order(1)
	public void testGetDrugByIdFromNotExistPrescriptionReturnsNull() {
		assertThat(drugsInPrescriptionService.findByDrugId(prescription.getId(), 2L)).isEqualTo(null);
	}
	
	@Test @Order(2)
	public void testAddNewPrescriptionWithNoDrugs() {
		PrescriptionDTO savedPrescription = prescriptionService.save(prescription);
		
		assertThat(savedPrescription.getId()).isEqualTo(prescription.getId());
		assertThat(savedPrescription.getDoctor().getTaxCode()).isEqualTo(prescription.getDoctor().getTaxCode());
		assertThat(savedPrescription.getDoctor().getFirstName()).isEqualTo(prescription.getDoctor().getFirstName());
		assertThat(savedPrescription.getDoctor().getLastName()).isEqualTo(prescription.getDoctor().getLastName());
		assertThat(savedPrescription.getDoctor().getDateOfBirth().toString()).isEqualTo(prescription.getDoctor().getDateOfBirth().toString());
		
	}
	
	@Test @Order(3)
	public void testGetAllDrugsInPrescription1ReturnsEmptyList() {
		assertThat(drugsInPrescriptionService.findAllDrugs(prescription.getId()).isEmpty()).isEqualTo(true);
	}
	
	@Test @Order(4)
	public void testGetDrugByIdFromPrescription1ReturnsEmptyOptional() {
		assertThat(drugsInPrescriptionService.findByDrugId(prescription.getId(), 2L).isEmpty()).isEqualTo(true);
	}
	
	@Test @Order(5)
	public void testAddNewDrugInPrescription() {
		PrescriptionDTO savedPrescription = drugsInPrescriptionService.save(prescription.getId(), drug);
	
		assertThat(savedPrescription.getId()).isEqualTo(prescription.getId());
		assertThat(savedPrescription.getDoctor().getTaxCode()).isEqualTo(prescription.getDoctor().getTaxCode());
		assertThat(savedPrescription.getDoctor().getFirstName()).isEqualTo(prescription.getDoctor().getFirstName());
		assertThat(savedPrescription.getDoctor().getLastName()).isEqualTo(prescription.getDoctor().getLastName());
		assertThat(savedPrescription.getDoctor().getDateOfBirth().toString()).isEqualTo(prescription.getDoctor().getDateOfBirth().toString());
		
		savedPrescription.getDrugs().forEach((currentDrug) -> {
			assertThat(currentDrug.getId().equals(drug.getId())).isEqualTo(true);
			assertThat(currentDrug.getName().equals(drug.getName())).isEqualTo(true);
			assertThat(currentDrug.getCost().equals(drug.getCost())).isEqualTo(true);
			assertThat(currentDrug.getDescription().equals(drug.getDescription())).isEqualTo(true);
			assertThat(currentDrug.getHasPrescription().equals(drug.getHasPrescription())).isEqualTo(true);
		});
		
		drugsInPrescriptionService.findAllDrugs(prescription.getId()).forEach((currentDrug) -> {
			assertThat(currentDrug.getId().equals(drug.getId())).isEqualTo(true);
			assertThat(currentDrug.getName().equals(drug.getName())).isEqualTo(true);
			assertThat(currentDrug.getCost().equals(drug.getCost())).isEqualTo(true);
			assertThat(currentDrug.getDescription().equals(drug.getDescription())).isEqualTo(true);
			assertThat(currentDrug.getHasPrescription().equals(drug.getHasPrescription())).isEqualTo(true);
		});
		
		drugsInPrescriptionService.findByDrugId(prescription.getId(), 2L).stream().forEach((currentDrug) -> {
			assertThat(currentDrug.getId().equals(drug.getId())).isEqualTo(true);
			assertThat(currentDrug.getName().equals(drug.getName())).isEqualTo(true);
			assertThat(currentDrug.getCost().equals(drug.getCost())).isEqualTo(true);
			assertThat(currentDrug.getDescription().equals(drug.getDescription())).isEqualTo(true);
			assertThat(currentDrug.getHasPrescription().equals(drug.getHasPrescription())).isEqualTo(true);
		});
	}
	
	@Test @Order(6)
	public void testUpdateDrugInPrescription() {
		
		drug.setId(2L);
		drug.setName("Nuovo Nome");
		drug.setDescription("Nuova descrizione");
		drug.setCost(2000.00);
		
		PrescriptionDTO savedPrescription = drugsInPrescriptionService.save(prescription.getId(), drug);
		
		assertThat(savedPrescription.getId()).isEqualTo(prescription.getId());
		assertThat(savedPrescription.getDoctor().getTaxCode()).isEqualTo(prescription.getDoctor().getTaxCode());
		assertThat(savedPrescription.getDoctor().getFirstName()).isEqualTo(prescription.getDoctor().getFirstName());
		assertThat(savedPrescription.getDoctor().getLastName()).isEqualTo(prescription.getDoctor().getLastName());
		assertThat(savedPrescription.getDoctor().getDateOfBirth().toString()).isEqualTo(prescription.getDoctor().getDateOfBirth().toString());
		
		savedPrescription.getDrugs().forEach((currentDrug) -> {
			assertThat(currentDrug.getId().equals(drug.getId())).isEqualTo(true);
			assertThat(currentDrug.getName().equals(drug.getName())).isEqualTo(true);
			assertThat(currentDrug.getCost().equals(drug.getCost())).isEqualTo(true);
			assertThat(currentDrug.getDescription().equals(drug.getDescription())).isEqualTo(true);
			assertThat(currentDrug.getHasPrescription().equals(drug.getHasPrescription())).isEqualTo(true);
		});
		
		assertThat(drugsInPrescriptionService.findAllDrugs(prescription.getId()).size()).isEqualTo(2);

	}
	
	@Test @Order(7)
	public void testDeleteDrugInPrescriptionById() {
		drugsInPrescriptionService.deleteByDrugId(prescription.getId(), 2L);
		assertThat(drugsInPrescriptionService.findAllDrugs(prescription.getId()).size()).isEqualTo(1);
	}
	
	@Test @Order(8)
	public void testDeleteAllDrugsInPrescription() {
		drugsInPrescriptionService.deleteAllDrugs(prescription.getId());
		assertThat(drugsInPrescriptionService.findAllDrugs(prescription.getId()).size()).isEqualTo(0);
	}
}
