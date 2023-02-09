package it.pharmacywebassistant.controller;

import it.pharmacywebassistant.controller.exception.BadRequestException;
import it.pharmacywebassistant.controller.exception.ConflictException;
import it.pharmacywebassistant.controller.exception.NotFoundException;
import it.pharmacywebassistant.controller.message.Message;
import it.pharmacywebassistant.model.Product;
import it.pharmacywebassistant.service.ProductService;
import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController @RequestMapping(path = "api/products", produces = MediaType.APPLICATION_JSON_VALUE)
public final class ProductController {

    @Autowired
    private ProductService service;

    @Autowired
    private ResourceBundleMessageSource errorMessage;

    @GetMapping(path = "/") @SneakyThrows
    private final ResponseEntity<List<Product>> getProducts() {
        final List<Product> products = service.findAll();
        if(products.isEmpty()) {
            throw new NotFoundException();
        }
        return ResponseEntity.ok(products);
    }

    @PostMapping(path = "/") @SneakyThrows
    private final ResponseEntity<Message> postProduct(@Valid Product product, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new BadRequestException();
        }
        final Optional<Product> productInDatabase = service.findById(product.getId());
        if(productInDatabase.isPresent()) {
            throw new ConflictException();
        }
        service.save(product);
        return ResponseEntity.ok(new Message(LocalDate.now(), (byte)HttpStatus.CREATED.value(), "Prodotto inserito con successo!"));
    }
}
