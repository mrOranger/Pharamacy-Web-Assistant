package it.pharmacywebassistant.controller;

import it.pharmacywebassistant.controller.exception.BadRequestException;
import it.pharmacywebassistant.controller.exception.ConflictException;
import it.pharmacywebassistant.controller.exception.NotFoundException;
import it.pharmacywebassistant.controller.message.Message;
import it.pharmacywebassistant.model.Cosmetic;
import it.pharmacywebassistant.model.dto.CosmeticDTO;
import it.pharmacywebassistant.service.CosmeticService;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@RestController
@RequestMapping(path = "v1/api/products/cosmetics", produces = MediaType.APPLICATION_JSON_VALUE)
public class CosmeticController {

    @Autowired
    private CosmeticService service;

    @Autowired
    private ResourceBundleMessageSource errorMessage;

    @GetMapping(path = "/") @SneakyThrows
    public ResponseEntity<List<CosmeticDTO>> getAllCosmetics() {
        final List<CosmeticDTO> products = service.findAll();
        if(products.isEmpty()) {
            throw new NotFoundException();
        }
        return ResponseEntity.ok(products);
    }

    @GetMapping(path = "/{id}") @SneakyThrows
    public ResponseEntity<CosmeticDTO> getCosmeticById(@PathVariable Long id) {
        final Optional<CosmeticDTO> product = service.findById(id);
        if(product.isEmpty()) {
            throw new NotFoundException();
        }
        return ResponseEntity.ok(product.get());
    }

    @PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE) @SneakyThrows
    public ResponseEntity<Message> postCosmetic(@Valid @RequestBody Cosmetic product, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            System.out.println(errorMessage.getMessage(bindingResult.getFieldError(), LocaleContextHolder.getLocale()));
            throw new BadRequestException(errorMessage.getMessage(bindingResult.getFieldError(), LocaleContextHolder.getLocale()));
        }
        final Optional<CosmeticDTO> productInDatabase = service.findById(product.getId());
        if(productInDatabase.isPresent()) {
            throw new ConflictException();
        }
        service.save(product);
        return ResponseEntity.ok(new Message(LocalDate.now(), HttpStatus.OK.value(), "Prodotto inserito con successo!"));
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE) @SneakyThrows
    public ResponseEntity<Message> putCosmetic(@PathVariable Long id, @Valid @RequestBody Cosmetic product, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            System.out.println(errorMessage.getMessage(bindingResult.getFieldError(), LocaleContextHolder.getLocale()));
            throw new BadRequestException(errorMessage.getMessage(bindingResult.getFieldError(), LocaleContextHolder.getLocale()));
        }
        final Optional<CosmeticDTO> productInDatabase = service.findById(id);
        if(productInDatabase.isEmpty()) {
            throw new NotFoundException("Elemento " + id + " non presente nel database!");
        }
        product.setId(id);
        service.save(product);
        return ResponseEntity.ok(new Message(LocalDate.now(), HttpStatus.OK.value(), "Prodotto modificato con successo!"));
    }

    @DeleteMapping(path = "/") @SneakyThrows
    public ResponseEntity<Message> deleteAllProducts() {
        final List<CosmeticDTO> products = service.findAll();
        if(products.isEmpty()) {
            throw new ConflictException("Impossibile eliminare una collezione vuota!");
        }
        service.deleteAll();
        return ResponseEntity.ok(new Message(LocalDate.now(), HttpStatus.OK.value(), "Prodotti eliminati con successo!"));
    }

    @DeleteMapping(path = "/{id}") @SneakyThrows
    public ResponseEntity<Message> deleteProduct(@PathVariable Long id) {
        final List<CosmeticDTO> products = service.findAll();
        if(products.isEmpty()) {
            throw new ConflictException("Impossibile eliminare un elemento da una collezione vuota!");
        }
        final Stream<CosmeticDTO> foundProduct = products.stream().filter((product -> product.getId().equals(id)));
        if(foundProduct.findAny().isEmpty()) {
            throw new ConflictException("Impossibile eliminare un elemento non presente nella collezione!");
        }
        service.deleteById(id);
        return ResponseEntity.ok(new Message(LocalDate.now(), HttpStatus.OK.value(), "Prodotto eliminato con successo!"));
    }
}
