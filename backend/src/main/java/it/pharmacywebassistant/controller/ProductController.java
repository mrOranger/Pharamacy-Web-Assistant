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
import it.pharmacywebassistant.model.Drug;
import it.pharmacywebassistant.model.dto.ProductDTO;
import it.pharmacywebassistant.service.ProductService;
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
@RequestMapping(path = "v1/api/products/", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Controller per un Prodotto", description = "Controller per le operazioni riguardo un Prodotto")
public final class ProductController {

    @Autowired
    private ProductService service;

    @Autowired
    private ResourceBundleMessageSource errorMessage;

    @Operation(summary = "GET Products", description = "Restituisce tutti i Prodotti all'interno del Database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sono presenti dei Prodotti all'interno del Database", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ProductDTO.class)))
            }),
            @ApiResponse(responseCode = "404", description = "Non sono stati trovati dei prodotti all'interno del Database", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            })
    })
    @GetMapping(path = "/") @SneakyThrows
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        final List<ProductDTO> products = service.findAll();
        if(products.isEmpty()) {
            throw new NotFoundException();
        }
        return ResponseEntity.ok(products);
    }

    @Operation(summary = "GET Product by Id", description = "Restituisce il Prodotto corrispondente all'id passato come parametro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "E'stato trovato un Prodotto corrispondente all'id passato come parametro.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Non è stato trovato alcun prodotto con l'id corrispondente al valore passato come parametro.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            })
    })
    @GetMapping(path = "/{id}") @SneakyThrows
    public ResponseEntity<ProductDTO> getProductById(@Parameter(description = "Id del Prodotto da cercare") @PathVariable Long id) {
        final Optional<ProductDTO> product = service.findById(id);
        if(product.isEmpty()) {
            throw new NotFoundException();
        }
        return ResponseEntity.ok(product.get());
    }

    @Operation(summary = "POST new Cosmetic", description = "Inserisce un nuovo Cosmetico all'interno del Database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Il nuovo Cosmetico è stato inserito correttamente.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            }),
            @ApiResponse(responseCode = "400", description = "Il formato della richiesta non è valido", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            }),
            @ApiResponse(responseCode = "409", description = "Il Cosmetico è già registrato, usare il metodo PUT per modificarlo", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            })
    })
    @PostMapping(path = "/cosmetics/", consumes = MediaType.APPLICATION_JSON_VALUE) @SneakyThrows
    public ResponseEntity<Message> postCosmetic(@Parameter(description = "Nuovo Cosmetico da inserire nel Database") @Valid @RequestBody Cosmetic product, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            System.out.println(errorMessage.getMessage(bindingResult.getFieldError(), LocaleContextHolder.getLocale()));
            throw new BadRequestException(errorMessage.getMessage(bindingResult.getFieldError(), LocaleContextHolder.getLocale()));
        }
        final Optional<ProductDTO> productInDatabase = service.findById(product.getId());
        if(productInDatabase.isPresent()) {
            throw new ConflictException();
        }
        service.save(product);
        return ResponseEntity.ok(new Message(LocalDate.now(), HttpStatus.OK.value(), "Cosmetico inserito con successo!"));
    }


    @Operation(summary = "POST new Drug", description = "Inserisce una Medicina all'interno del Database")
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
    @PostMapping(path = "/drugs/", consumes = MediaType.APPLICATION_JSON_VALUE) @SneakyThrows
    public ResponseEntity<Message> postDrug(@Parameter(description = "Nuova medicina da inserire nel Database") @Valid @RequestBody Drug product, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            System.out.println(errorMessage.getMessage(bindingResult.getFieldError(), LocaleContextHolder.getLocale()));
            throw new BadRequestException(errorMessage.getMessage(bindingResult.getFieldError(), LocaleContextHolder.getLocale()));
        }
        final Optional<ProductDTO> productInDatabase = service.findById(product.getId());
        if(productInDatabase.isPresent()) {
            throw new ConflictException();
        }
        service.save(product);
        return ResponseEntity.ok(new Message(LocalDate.now(), HttpStatus.OK.value(), "Medicina inserita con successo!"));
    }


    @Operation(summary = "PUT a Drug", description = "Modifica una Medicina registrata nel Database")
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
    @PutMapping(path = "/drugs/{id}", consumes = MediaType.APPLICATION_JSON_VALUE) @SneakyThrows
    public ResponseEntity<Message> putDrug( @Parameter(description = "Medicina da modificare nel Database") @PathVariable Long id, @Valid @RequestBody Drug product, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            System.out.println(errorMessage.getMessage(bindingResult.getFieldError(), LocaleContextHolder.getLocale()));
            throw new BadRequestException(errorMessage.getMessage(bindingResult.getFieldError(), LocaleContextHolder.getLocale()));
        }
        final Optional<ProductDTO> productInDatabase = service.findById(id);
        if(productInDatabase.isEmpty()) {
            throw new NotFoundException("Elemento " + id + " non presente nel database!");
        }
        product.setId(id);
        service.save(product);
        return ResponseEntity.ok(new Message(LocalDate.now(), HttpStatus.OK.value(), "Medicina modificata con successo!"));
    }

    @Operation(summary = "PUT a Drug", description = "Modifica un Cosmetico registrato nel Database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Il nuovo Cosmetico è stato registrato correttamente", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            }),
            @ApiResponse(responseCode = "400", description = "Il formato della richiesta non è valido", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            }),
            @ApiResponse(responseCode = "404", description = "Il Cosmetico è già registrato, usare il metodo POST per modificarlo", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            })
    })
    @PutMapping(path = "/cosmetics/{id}", consumes = MediaType.APPLICATION_JSON_VALUE) @SneakyThrows
    public ResponseEntity<Message> putDrug( @Parameter(description = "Cosmetico da modificare nel Database") @PathVariable Long id, @Valid @RequestBody Cosmetic product, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            System.out.println(errorMessage.getMessage(bindingResult.getFieldError(), LocaleContextHolder.getLocale()));
            throw new BadRequestException(errorMessage.getMessage(bindingResult.getFieldError(), LocaleContextHolder.getLocale()));
        }
        final Optional<ProductDTO> productInDatabase = service.findById(id);
        if(productInDatabase.isEmpty()) {
            throw new NotFoundException("Elemento " + id + " non presente nel database!");
        }
        product.setId(id);
        service.save(product);
        return ResponseEntity.ok(new Message(LocalDate.now(), HttpStatus.OK.value(), "Cosmetico modificato con successo!"));
    }

    @Operation(summary = "DELETE all Products", description = "Elimina tutti i Prodotti registrati nel Database")
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
        final List<ProductDTO> products = service.findAll();
        if(products.isEmpty()) {
            throw new ConflictException("Impossibile eliminare una collezione vuota!");
        }
        service.deleteAll();
        return ResponseEntity.ok(new Message(LocalDate.now(), HttpStatus.OK.value(), "Prodotti eliminati con successo!"));
    }

    @Operation(summary = "DELETE a Product by Id", description = "Elimina un Prodotto corrispondente all'id passato come parametro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Il Prodotto è stato eliminato correttamente", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            }),
            @ApiResponse(responseCode = "409", description = "Non esiste alcun Prodotto quell'id registrata nel Database", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            })
    })
    @DeleteMapping(path = "/{id}") @SneakyThrows
    public ResponseEntity<Message> deleteProductById(@Parameter(description = "Id del Prodotto da eliminare") @PathVariable Long id) {
        final List<ProductDTO> products = service.findAll();
        if(products.isEmpty()) {
            throw new ConflictException("Impossibile eliminare un elemento da una collezione vuota!");
        }
        final Stream<ProductDTO> foundProduct = products.stream().filter((product -> product.getId().equals(id)));
        if(foundProduct.findAny().isEmpty()) {
            throw new ConflictException("Impossibile eliminare un elemento non presente nella collezione!");
        }
        service.deleteById(id);
        return ResponseEntity.ok(new Message(LocalDate.now(), HttpStatus.OK.value(), "Prodotto eliminato con successo!"));
    }
}
