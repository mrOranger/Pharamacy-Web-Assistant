package it.pharmacywebassistant.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Cosmetic Controller", description = "Controller per le operazioni riguardo un Cosmetico")
public class CosmeticController {

    @Autowired
    private CosmeticService service;

    @Autowired
    private ResourceBundleMessageSource errorMessage;

    @Operation(summary = "GET all Cosmetics", description = "Restituisce tutti i Cosmetici presenti nel Database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sono stati trovati dei Cosmetici nel Database", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CosmeticDTO.class)))
            }),
            @ApiResponse(responseCode = "404", description = "Non è stato trovato alcun Cosmetico nel Database", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            })
    })
    @GetMapping(path = "/") @SneakyThrows
    public ResponseEntity<List<CosmeticDTO>> getAllCosmetics() {
        final List<CosmeticDTO> products = service.findAll();
        if(products.isEmpty()) {
            throw new NotFoundException();
        }
        return ResponseEntity.ok(products);
    }

    @Operation(summary = "GET Cosmetic by Id", description = "Restituisce il cosmetico corrispondente all'Id passato in input")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "E'stato trovato un Cosmetico all'interno del Database", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CosmeticDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Non è stato trovato alcun Cosmetico all'interno del Database", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            })
    })
    @GetMapping(path = "/{id}") @SneakyThrows
    public ResponseEntity<CosmeticDTO> getCosmeticById(@Parameter(description = "Id del Cosmetico da cercare") @PathVariable Long id) {
        final Optional<CosmeticDTO> product = service.findById(id);
        if(product.isEmpty()) {
            throw new NotFoundException();
        }
        return ResponseEntity.ok(product.get());
    }

    @Operation(summary = "POST new Cosmetic", description = "Inserisce un nuovo Cosmetico nel Database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Il nuovo Cosmetico è stato inserito con successo", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            }),
            @ApiResponse(responseCode = "400", description = "Il formato della richiesta non è valido", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            }),
            @ApiResponse(responseCode = "409", description = "Il Cosmetico è già registrato, usare il metodo PUT per modificarlo", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            })
    })
    @PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE) @SneakyThrows
    public ResponseEntity<Message> postCosmetic(@Parameter(description = "Nuovo Cosmetico da inserire") @Valid @RequestBody Cosmetic product, BindingResult bindingResult) {
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

    @Operation(summary = "PUT a Cosmetic", description = "Modifica un Cosmetico registrato nel Database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Il Cosmetico è stato registrato con successo", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            }),
            @ApiResponse(responseCode = "400", description = "Il formato della richiesta non è valido", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            }),
            @ApiResponse(responseCode = "404", description = "Il Cosmetico non è registrato nel Database, usare il metodo POST per inserirlo", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            })
    })
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE) @SneakyThrows
    public ResponseEntity<Message> putCosmetic(@Parameter(description = "Id del Cosmetico da modificare") @PathVariable Long id, @Parameter(description = "Cosmetico da modificare") @Valid @RequestBody Cosmetic product, BindingResult bindingResult) {
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

    @Operation(summary = "DELETE all Cosmetics", description = "Elimina tutti i Cosmetici registrati nel Database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "I Cosmetici sono stati eliminati correttamente", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            }),
            @ApiResponse(responseCode = "409", description = "Non esiste alcun Cosmetico registrato nel Database", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            })
    })
    @DeleteMapping(path = "/") @SneakyThrows
    public ResponseEntity<Message> deleteAllProducts() {
        final List<CosmeticDTO> products = service.findAll();
        if(products.isEmpty()) {
            throw new ConflictException("Impossibile eliminare una collezione vuota!");
        }
        service.deleteAll();
        return ResponseEntity.ok(new Message(LocalDate.now(), HttpStatus.OK.value(), "Prodotti eliminati con successo!"));
    }

    @Operation(summary = "DELETE a Cosmetic by Id", description = "Elimina il Cosmetico identificato dall'Id passato come parametro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Il Cosmetico è stato registrato correttamente", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            }),
            @ApiResponse(responseCode = "409", description = "Non esiste alcun cosmetico registrato nel Database, oppure con l'Id passato come parametro", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            })
    })
    @DeleteMapping(path = "/{id}") @SneakyThrows
    public ResponseEntity<Message> deleteProduct(@Parameter(description = "Id del Cosmetico da eliminare") @PathVariable Long id) {
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
