package it.pharmacywebassistant.services.drugs;

import it.pharmacywebassistant.model.Address;
import it.pharmacywebassistant.model.Brand;
import it.pharmacywebassistant.model.BrandDrug;
import it.pharmacywebassistant.service.BrandDrugService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BrandDrugTest {

    @Autowired
    private BrandDrugService service;

    @Test @Order(1)
    public void testFindAllBrandDrugsReturnsEmptyCollection () {
        assertEquals(service.findAll().size(), 0);
    }

    @Test @Order(2)
    public void testSaveNewBrandDrugReturnsNewBrandDrug () {
        final Address address = new Address("1", "Prova", 1, "Prova", "Prova");
        final Brand brand = new Brand("Prova");
        brand.setAddress(address);
        BrandDrug drug = new BrandDrug("A123", "Description", 143.90f, true, Date.valueOf(LocalDate.parse("2012-12-12")));
        drug.setBrand(brand);
        BrandDrug savedDrug = service.save(drug);
        assertEquals(savedDrug.getName(), drug.getName());
        assertEquals(savedDrug.getDescription(), drug.getDescription());
        assertEquals(savedDrug.getCost(), drug.getCost());
        assertEquals(savedDrug.getNeedPrescription(), drug.getNeedPrescription());
        assertEquals(savedDrug.getExpiresIn(),  drug.getExpiresIn());
        assertEquals(savedDrug.getBrand().getCompanyName(), drug.getBrand().getCompanyName());
        assertEquals(savedDrug.getBrand().getAddress().getCode(), drug.getBrand().getAddress().getCode());
    }

    @Test @Order(3)
    public void testFindAllBrandDrugsReturnCollectionWithSizeOne () {
        assertEquals(service.findAll().size(), 1);
    }

    @Test @Order(4)
    public void testRemoveAllBrandDrugReturnsEmptyCollection () {
        service.deleteAll();
        assertEquals(service.findAll().size(), 0);
    }
}
