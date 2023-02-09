package it.pharmacywebassistant.services.cosmetics;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest @TestMethodOrder(MethodOrderer.class)
public final class TestCosmetics {

    @Test @Order(1)
    public void testFindAllCosmeticsReturnsACollectionOfSizeZero() {

    }

    @Test @Order(2)
    public void testAddNewCosmeticReturnsANewCosmetic() {

    }

    @Test @Order(3)
    public void testUpdateAnExistingCosmeticReturnsTheUpdatedCosmetic() {

    }

    @Test @Order(4)
    public void testAddAnotherNewCosmeticReturnsANewCosmetic() {

    }

    @Test @Order(5)
    public void testDeleteAnExistingCosmeticReturnsACollectionOfSizeOne() {

    }

    @Test @Order(6)
    public void testDeleteAllCosmeticsReturnsACollectionOfSizeZero() {

    }
}
