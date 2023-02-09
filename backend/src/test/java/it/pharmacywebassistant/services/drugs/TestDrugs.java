package it.pharmacywebassistant.services.drugs;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest @TestMethodOrder(MethodOrderer.class)
public class TestDrugs {

    @Test
    @Order(1)
    public void testFindAllDrugsReturnsACollectionOfSizeZero() {

    }

    @Test @Order(2)
    public void testAddNewDrugReturnsANewDrug() {

    }

    @Test @Order(3)
    public void testUpdateAnExistingDrugReturnsTheUpdatedDrug() {

    }

    @Test @Order(4)
    public void testAddAnotherNewDrugReturnsANewDrug() {

    }

    @Test @Order(5)
    public void testDeleteAnExistingDrugReturnsACollectionOfSizeOne() {

    }

    @Test @Order(6)
    public void testDeleteAllDrugsReturnsACollectionOfSizeZero() {

    }
}
