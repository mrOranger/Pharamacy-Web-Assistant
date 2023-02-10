package it.pharmacywebassistant.controller;

import it.pharmacywebassistant.controller.exception.BadRequestException;
import it.pharmacywebassistant.controller.exception.ConflictException;
import it.pharmacywebassistant.controller.exception.NotFoundException;
import it.pharmacywebassistant.controller.message.Message;
import it.pharmacywebassistant.model.Cosmetic;
import it.pharmacywebassistant.model.Drug;
import it.pharmacywebassistant.model.Product;
import it.pharmacywebassistant.service.ProductService;
import it.pharmacywebassistant.service.implementation.ProductServiceImpl;
import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
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
    public final ResponseEntity<List<Product>> getProducts() {
        final List<Product> products = service.findAll();
        if(products.isEmpty()) {
            throw new NotFoundException();
        }
        return ResponseEntity.ok(products);
    }

    @PostMapping(path = "/drugs/", consumes = MediaType.APPLICATION_JSON_VALUE) @SneakyThrows
    public final ResponseEntity<Message> postDrug(@Valid @RequestBody Drug product, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            System.out.println(errorMessage.getMessage(bindingResult.getFieldError(), LocaleContextHolder.getLocale()));
            throw new BadRequestException(errorMessage.getMessage(bindingResult.getFieldError(), LocaleContextHolder.getLocale()));
        }
        final Optional<Product> productInDatabase = service.findById(product.getId());
        if(productInDatabase.isPresent()) {
            throw new ConflictException();
        }
        service.save(product);
        return ResponseEntity.ok(new Message(LocalDate.now(), HttpStatus.OK.value(), "Prodotto inserito con successo!"));
    }

    @PostMapping(path = "/cosmetics/", consumes = MediaType.APPLICATION_JSON_VALUE) @SneakyThrows
    public final ResponseEntity<Message> postCosmetic(@Valid @RequestBody Cosmetic product, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new BadRequestException(errorMessage.getMessage(bindingResult.getFieldError(), LocaleContextHolder.getLocale()));
        }
        final Optional<Product> productInDatabase = service.findById(product.getId());
        if(productInDatabase.isPresent()) {
            throw new ConflictException();
        }
        service.save(product);
        return ResponseEntity.ok(new Message(LocalDate.now(), HttpStatus.OK.value(), "Prodotto inserito con successo!"));
    }
}
