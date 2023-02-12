package it.pharmacywebassistant.controllers;

import it.pharmacywebassistant.PharmacyWebAssistantApplication;
import it.pharmacywebassistant.service.CosmeticService;
import lombok.SneakyThrows;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isA;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = PharmacyWebAssistantApplication.class)
@SpringBootTest @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public final class TestCosmeticController {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private CosmeticService service;

    private MockMvc mockMvc;
    private JSONObject cosmetic;

    @BeforeEach
    @SneakyThrows
    public void beforeEach() {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        this.cosmetic = new JSONObject();

        this.cosmetic.put("id", 1)
                .put("name", "Product")
                .put("description", "Product")
                .put("cost", 1000.00)
                .put("type", "Prova")
                .put("company", new JSONObject()
                        .put("id", 1)
                        .put("name", "Prova")
                        .put("address", new JSONObject()
                                .put("id", 1)
                                .put("streetName", "Prova")
                                .put("streetCode", 2)
                                .put("city", "Prova")
                                .put("nation", "Prova"))
                );

    }

    @Test @Order(1) @SneakyThrows
    public void testGetAllProductsReturnsMessageWithNotFoundCode() {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/products/cosmetics/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.message").value("Nessun elemento presente nel database!"))
                .andDo(print());
    }

    @Test @Order(2) @SneakyThrows
    public void testGetProductByIdReturnsMessageWithNotFoundCode() {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/products/cosmetics/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.message").value("Nessun elemento presente nel database!"))
                .andDo(print());
    }

    @Test @Order(3) @SneakyThrows
    public void testPostNewProductReturnsMessageWithOkCode() {
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/api/products/cosmetics/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(cosmetic))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.message").value("Prodotto inserito con successo!"))
                .andDo(print());
    }

    @Test @Order(4) @SneakyThrows
    public void testPostNewProductReturnsMessageWithConflictCode() {
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/api/products/cosmetics/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(cosmetic))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value(HttpStatus.CONFLICT.value()))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.message").value("Elemento già presente all'interno del database!"))
                .andDo(print());
    }

    @Test @Order(5) @SneakyThrows
    public void testPostNewProductReturnsMessageWithBadRequestCode() {
        this.cosmetic.remove("name");
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/api/products/cosmetics/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(cosmetic))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.message").value("Il nome del prodotto non può essere nullo"))
                .andDo(print());
    }

    @Test @Order(6) @SneakyThrows
    public void testPutProductReturnsMessageWithOkCode() {
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/api/products/cosmetics/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(cosmetic))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.message").value("Prodotto modificato con successo!"))
                .andDo(print());
    }

    @Test @Order(7) @SneakyThrows
    public void testPutProductReturnsMessageWithNotFoundCode() {
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/api/products/cosmetics/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(cosmetic))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.message").value("Elemento 2 non presente nel database!"))
                .andDo(print());
    }
    @Test @Order(8) @SneakyThrows
    public void testPutProductReturnsMessageWithBadRequestCode() {
        this.cosmetic.remove("name");
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/api/products/cosmetics/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(cosmetic))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.message").value("Il nome del prodotto non può essere nullo"))
                .andDo(print());
    }

    @Test @Order(9) @SneakyThrows
    public void testGetAllProductsReturnsMessageWithOkayCodeAndCollectionOfSizeOne() {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/products/cosmetics/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", isA(List.class)))
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andDo(print());
    }


    @Test @Order(10) @SneakyThrows
    public void testRemoveProductWithId1ReturnsMessageWithOkCode() {
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/api/products/cosmetics/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.message").value("Prodotto eliminato con successo!"))
                .andDo(print());
    }

    @Test @Order(11) @SneakyThrows
    public void testGetAllProductsReturnsAgainMessageWithNotFoundCode() {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/products/cosmetics/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.message").value("Nessun elemento presente nel database!"))
                .andDo(print());
    }

    @Test @Order(12) @SneakyThrows
    public void testRemoveProductWithId1ReturnsMessageWithConflictCode() {
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/api/products/cosmetics/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value(HttpStatus.CONFLICT.value()))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.message").value("Impossibile eliminare un elemento da una collezione vuota!"))
                .andDo(print());
    }

    @Test @Order(13) @SneakyThrows
    public void testRemoveAllProductsReturnsMessageWithConflictCode() {
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/api/products/cosmetics/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value(HttpStatus.CONFLICT.value()))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.message").value("Impossibile eliminare una collezione vuota!"))
                .andDo(print());
    }
}
