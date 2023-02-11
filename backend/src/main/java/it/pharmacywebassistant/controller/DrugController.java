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
import it.pharmacywebassistant.model.Drug;
import it.pharmacywebassistant.model.dto.DrugDTO;
import it.pharmacywebassistant.service.DrugService;
import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
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
@RequestMapping(path = "v1/api/products/drugs", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "DrugController", description = "Controller per le operazioni riguardo un Medicinale")
public class DrugController {

    @Autowired
    private DrugService service;

    @Autowired
    private ResourceBundleMessageSource errorMessage;

    @Operation(summary = "GET all drugs", description = "Restituisce tutte le Medicine all'interno del Database", tags = "Get")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trovate delle Medicine nel Database", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = DrugDTO.class)))
            }),
            @ApiResponse(responseCode = "404", description = "Non sono state trovate delle Medicine nel Database", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            })
    })
    @GetMapping(path = "/") @SneakyThrows
    public ResponseEntity<List<DrugDTO>> getAllDrugs() {
        final List<DrugDTO> products = service.findAll();
        if(products.isEmpty()) {
            throw new NotFoundException();
        }
        return ResponseEntity.ok(products);
    }

    @Operation(summary = "GET Drug by Id", description = "Restituisce la Medicina corrispondente all'id passato come parametro", tags = "Get")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trovata una medicina corrispondente all'id passato come parametro", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = DrugDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Non è stata trovata nessuna medicia corrispondente all'id passato come parametro", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            })
    })
    @GetMapping(path = "/{id}") @SneakyThrows
    public ResponseEntity<DrugDTO> getDrugById(@Parameter(description = "Id della Medicina da cercare") @PathVariable Long id) {
        final Optional<DrugDTO> product = service.findById(id);
        if(product.isEmpty()) {
            throw new NotFoundException();
        }
        return ResponseEntity.ok(product.get());
    }

    @Operation(summary = "POST new Drug", description = "Inserisce una Medicina all'interno del Database", tags = "Post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La nuova Medicina è stata inserita correttamente", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            }),
            @ApiResponse(responseCode = "400", description = "Il formato della richiesta non è valido", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            }),
            @ApiResponse(responseCode = "409", description = "La Medicina è già registrata, usare il metodo PUT per modificarla", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            })
    })
    @PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE) @SneakyThrows
    public ResponseEntity<Message> postDrug(@Parameter(description = "Nuova medicina da inserire nel Database") @Valid @RequestBody Drug product, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            System.out.println(errorMessage.getMessage(bindingResult.getFieldError(), LocaleContextHolder.getLocale()));
            throw new BadRequestException(errorMessage.getMessage(bindingResult.getFieldError(), LocaleContextHolder.getLocale()));
        }
        final Optional<DrugDTO> productInDatabase = service.findById(product.getId());
        if(productInDatabase.isPresent()) {
            throw new ConflictException();
        }
        service.save(product);
        return ResponseEntity.ok(new Message(LocalDate.now(), HttpStatus.OK.value(), "Prodotto inserito con successo!"));
    }

    @Operation(summary = "PUT a Drug", description = "Modifica una Medicina registrata nel Database", tags = "Put")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La nuova Medicina è stata modificata correttamente", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            }),
            @ApiResponse(responseCode = "400", description = "Il formato della richiesta non è valido", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            }),
            @ApiResponse(responseCode = "404", description = "La Medicina non è registrata, usare il metodo POST per inserirla", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            })
    })
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE) @SneakyThrows
    public ResponseEntity<Message> putDrug( @Parameter(description = "Medicina da modificare nel Database") @PathVariable Long id, @Valid @RequestBody Drug product, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            System.out.println(errorMessage.getMessage(bindingResult.getFieldError(), LocaleContextHolder.getLocale()));
            throw new BadRequestException(errorMessage.getMessage(bindingResult.getFieldError(), LocaleContextHolder.getLocale()));
        }
        final Optional<DrugDTO> productInDatabase = service.findById(id);
        if(productInDatabase.isEmpty()) {
            throw new NotFoundException("Elemento " + id + " non presente nel database!");
        }
        product.setId(id);
        service.save(product);
        return ResponseEntity.ok(new Message(LocalDate.now(), HttpStatus.OK.value(), "Prodotto modificato con successo!"));
    }

    @Operation(summary = "DELETE all Drugs", description = "Elimina tutte le Medicine registrate", tags = "Delete")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Le Medicine sono state eliminate correttamente", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            }),
            @ApiResponse(responseCode = "409", description = "Non esiste alcuna Medicina registrata nel Database", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            })
    })
    @DeleteMapping(path = "/") @SneakyThrows
    public ResponseEntity<Message> deleteAllProducts() {
        final List<DrugDTO> products = service.findAll();
        if(products.isEmpty()) {
            throw new ConflictException("Impossibile eliminare una collezione vuota!");
        }
        service.deleteAll();
        return ResponseEntity.ok(new Message(LocalDate.now(), HttpStatus.OK.value(), "Prodotti eliminati con successo!"));
    }

    @Operation(summary = "DELETE a Drug by Id", description = "Elimina la Medicina corrispondente all'id passato come parametro", tags = "Delete")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La Medicina è stata eliminata correttamente", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            }),
            @ApiResponse(responseCode = "409", description = "Non esiste alcuna Medicina con quell'id registrata nel Database", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            })
    })
    @DeleteMapping(path = "/{id}") @SneakyThrows
    public ResponseEntity<Message> deleteProduct(@Parameter(description = "Id della Medicina da eliminare") @PathVariable Long id) {
        final List<DrugDTO> products = service.findAll();
        if(products.isEmpty()) {
            throw new ConflictException("Impossibile eliminare un elemento da una collezione vuota!");
        }
        final Stream<DrugDTO> foundProduct = products.stream().filter((product -> product.getId().equals(id)));
        if(foundProduct.findAny().isEmpty()) {
            throw new ConflictException("Impossibile eliminare un elemento non presente nella collezione!");
        }
        service.deleteById(id);
        return ResponseEntity.ok(new Message(LocalDate.now(), HttpStatus.OK.value(), "Prodotto eliminato con successo!"));
    }
}
