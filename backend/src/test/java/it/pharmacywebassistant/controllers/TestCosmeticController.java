package it.pharmacywebassistant.controllers;

import it.pharmacywebassistant.PharmacyWebAssistantApplication;
import it.pharmacywebassistant.service.implementation.ProductServiceImpl;
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
    private ProductServiceImpl service;

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

    @Test
    @Order(1) @SneakyThrows
    public void testGetAllProductsReturnsMessageWithNotFoundCode() {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/products/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.message").value("Nessun elemento presente nel database!"))
                .andDo(print());
    }

    @Test @Order(2) @SneakyThrows
    public void testPostNewProductReturnsMessageWithOkCode() {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/products/cosmetics/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(cosmetic))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.message").value("Prodotto inserito con successo!"))
                .andDo(print());
    }

    @Test @Order(3) @SneakyThrows
    public void testPostNewProductReturnsMessageWithConflictCode() {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/products/cosmetics/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(cosmetic))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value(HttpStatus.CONFLICT.value()))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.message").value("Elemento già presente all'interno del database!"))
                .andDo(print());
    }

    @Test @Order(4) @SneakyThrows
    public void testPostNewProductReturnsMessageWithBadRequestCode() {
        cosmetic.remove("name");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/products/cosmetics/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(cosmetic))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.message").value("Il nome del prodotto non può essere nullo"))
                .andDo(print());
    }

    @Test @Order(5) @SneakyThrows
    public void testGetAllProductsReturnsMessageWithOkayCodeAndCollectionOfSizeOne() {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/products/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", isA(List.class)))
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andDo(print());
    }
}