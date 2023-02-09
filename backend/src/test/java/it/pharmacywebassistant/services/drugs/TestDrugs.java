package it.pharmacywebassistant.services.drugs;

import it.pharmacywebassistant.model.Address;
import it.pharmacywebassistant.model.Company;
import it.pharmacywebassistant.model.Drug;
import it.pharmacywebassistant.service.ProductService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestDrugs {

    @Autowired
    private ProductService service;
    private Drug drug;
    private Company company;
    private Address address;

    @BeforeEach
    public void beforeEach() {
        this.address = new Address("Prova", 2l, "Prova", "Prova");
        this.company = new Company("Prova", this.address);
        this.drug = new Drug("Prova", "Prova", 1000.00, true);
        this.drug.setCompany(this.company);
        this.drug.setId(1l);
    }

    @Test
    @Order(1)
    public void testFindAllDrugsReturnsACollectionOfSizeZero() {
        assertThat(service.findAll().size()).isEqualTo(0);
    }

    @Test @Order(2)
    public void testAddNewDrugReturnsANewDrug() {
        assertThat(service.save(this.drug).getName()).isEqualTo(this.drug.getName());
    }

    @Test @Order(3)
    public void testUpdateAnExistingDrugReturnsTheUpdatedDrug() {
        this.drug.setCost(5000.00);
        assertThat(service.save(this.drug).getCost()).isEqualTo(5000.00);
    }

    @Test @Order(4)
    public void testAddAnotherNewDrugReturnsANewDrug() {
        this.drug = new Drug("Prova2", "Prova", 1000.00, true);
        this.drug.setCompany(this.company);
        assertThat(service.save(this.drug).getName()).isEqualTo("Prova2");
    }

    @Test @Order(5)
    public void testDeleteAnExistingDrugReturnsAnEmptyOptional() {
        service.deleteById(this.drug.getId());
        assertThat(service.findById(this.drug.getId()).isEmpty());
    }

    @Test @Order(6)
    public void testDeleteAllDrugsReturnsACollectionOfSizeZero() {
        service.deleteAll();
        assertThat(service.findAll().size()).isEqualTo(0);
    }
}
