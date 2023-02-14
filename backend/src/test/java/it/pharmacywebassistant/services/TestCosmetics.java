package it.pharmacywebassistant.services;

import it.pharmacywebassistant.model.Address;
import it.pharmacywebassistant.model.Company;
import it.pharmacywebassistant.model.Cosmetic;
import it.pharmacywebassistant.service.ProductService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public final class TestCosmetics {

    @Autowired
    private ProductService service;
    private Cosmetic cosmetic;
    private Company company;
    private Address address;

    @BeforeEach
    public void beforeEach() {
        this.address = new Address("Prova", 2l, "Prova", "Prova");
        this.company = new Company("Prova", this.address);
        this.cosmetic = new Cosmetic("Prova", "Prova", 1000.00, "Prova");
        this.cosmetic.setCompany(this.company);
        this.cosmetic.setId(1l);
    }

    @Test @Order(1)
    public void testFindAllCosmeticsReturnsACollectionOfSizeZero() {
        assertThat(service.findAll().size()).isEqualTo(0);
    }

    @Test @Order(2)
    public void testAddNewCosmeticReturnsANewCosmetic() {
        assertThat(service.save(this.cosmetic).getName()).isEqualTo(this.cosmetic.getName());
    }

    @Test @Order(3)
    public void testUpdateAnExistingCosmeticReturnsTheUpdatedCosmetic() {
        this.cosmetic.setCost(5000.00);
        assertThat(service.save(this.cosmetic).getCost()).isEqualTo(5000.00);
    }

    @Test @Order(4)
    public void testAddAnotherNewCosmeticReturnsANewCosmetic() {
        this.cosmetic = new Cosmetic("Prova", "Prova", 1000.00, "Prova");
        this.cosmetic.setCompany(this.company);
        assertThat(service.save(this.cosmetic).getName()).isEqualTo("Prova");
    }

    @Test @Order(5)
    public void testDeleteAnExistingCosmeticReturnsACollectionOfSizeOne() {
        service.deleteById(this.cosmetic.getId());
        assertThat(service.findById(this.cosmetic.getId()).isEmpty());
    }

    @Test @Order(6)
    public void testDeleteAllCosmeticsReturnsACollectionOfSizeZero() {
        service.deleteAll();
        assertThat(service.findAll().size()).isEqualTo(0);
    }
}
