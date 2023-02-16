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
import it.pharmacywebassistant.model.Patient;
import it.pharmacywebassistant.model.dto.PatientDTO;
import it.pharmacywebassistant.model.dto.PersonDTO;
import it.pharmacywebassistant.model.dto.ProductDTO;
import it.pharmacywebassistant.service.PersonService;
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

@RestController
@RequestMapping(path = "v1/api/patients/", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Controller relativo ad un Paziente", description = "Controller per le operazioni riguardo un Paziente")
public final class PatientController {

    @Autowired
    private PersonService service;

    @Autowired
    private ResourceBundleMessageSource errorMessage;

    @Operation(summary = "GET Patients", description = "Restituisce tutti i Pazienti registrati nel Database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sono presenti dei Pazienti all'interno del Database", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PersonDTO.class)))
            }),
            @ApiResponse(responseCode = "404", description = "Non sono stati trovati dei Pazienti all'interno del Database", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            })
    })
    @GetMapping(path = "/") @SneakyThrows
    public ResponseEntity<List<PersonDTO>> getAllPatients() {
        final List<PersonDTO> patientDTOList = service.findAllPatients();
        if(patientDTOList.isEmpty()) {
            throw new NotFoundException("Nessun Paziente registrato nel Database!");
        }
        return ResponseEntity.ok(patientDTOList);
    }

    @Operation(summary = "GET Patient by Tax Code", description = "Restituisce il Paziente corrispondente al Codice Fiscale  passato come parametro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "E'stato trovato un Paziente corrispondente all'id passato come parametro.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PersonDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Non è stato trovato alcun Paziente con il Codice Fiscale corrispondente al valore passato come parametro.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            })
    })
    @GetMapping(path = "/{taxCode}") @SneakyThrows
    public ResponseEntity<PersonDTO> getPatientByTaxCode(@Parameter(description = "Codice Fiscale del Paziente da cercare") @PathVariable String taxCode) {
        final Optional<PersonDTO> patient = service.findByTaxCode(taxCode);
        if(patient.isEmpty() || !(patient.get() instanceof PatientDTO)) {
            throw new NotFoundException("Nessun paziente registrato con Codice Fiscale " + taxCode + "!");
        }
        return ResponseEntity.ok(patient.get());
    }

    @Operation(summary = "POST new Patient", description = "Inserisce un nuovo Paziente all'interno del Database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Il nuovo Paziente è stato inserito correttamente.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            }),
            @ApiResponse(responseCode = "400", description = "Il formato della richiesta non è valido", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            }),
            @ApiResponse(responseCode = "409", description = "Il Paziente è già registrato, usare il metodo PUT per modificarlo", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            })
    })
    @PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE) @SneakyThrows
    public ResponseEntity<Message> postPatient(@Parameter(description = "Paziente da inserire") @Valid @RequestBody Patient patient, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            System.out.println(errorMessage.getMessage(bindingResult.getFieldError(), LocaleContextHolder.getLocale()));
            throw new BadRequestException(errorMessage.getMessage(bindingResult.getFieldError(), LocaleContextHolder.getLocale()));
        }
        final Optional<PersonDTO> patientDTO = service.findByTaxCode(patient.getTaxCode());
        if (patientDTO.isPresent() && (patientDTO.get() instanceof PatientDTO)) {
            throw new ConflictException("Il Paziente e' gia' registrato nel Database, usare metodo PUT per modificarlo!");
        }
        service.save(patient);
        return ResponseEntity.ok(new Message(LocalDate.now(), HttpStatus.OK.value(), "Paziente registrato con successo!"));
    }

    @Operation(summary = "PUT a Patient", description = "Modifica un Paziente registrato nel Database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Il Paziente è stato modificato correttamente", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            }),
            @ApiResponse(responseCode = "400", description = "Il formato della richiesta non è valido", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            }),
            @ApiResponse(responseCode = "404", description = "Il Paziente non è registrato, usare il metodo POST per inserirlo", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            })
    })
    @PutMapping(path = "/{taxCode}", consumes = MediaType.APPLICATION_JSON_VALUE) @SneakyThrows
    public ResponseEntity<Message> putPatient(@Parameter(description = "Codice Fiscale del Paziente da modificare") @PathVariable String taxCode, @Parameter(description = "Paziente da modificare") @Valid @RequestBody Patient patient, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            System.out.println(errorMessage.getMessage(bindingResult.getFieldError(), LocaleContextHolder.getLocale()));
            throw new BadRequestException(errorMessage.getMessage(bindingResult.getFieldError(), LocaleContextHolder.getLocale()));
        }
        patient.setTaxCode(taxCode);
        final Optional<PersonDTO> patientDTO = service.findByTaxCode(taxCode);
        if (patientDTO.isEmpty() || !(patientDTO.get() instanceof PatientDTO)) {
            throw new ConflictException("Paziente non registrato, usare metodo POST per registrarlo!");
        }
        service.save(patient);
        return ResponseEntity.ok(new Message(LocalDate.now(), HttpStatus.OK.value(), "Paziente modificato con successo!"));
    }

    @Operation(summary = "DELETE a Patient by Id", description = "Elimina un Paziente corrispondente al Codice Fiscale passato come parametro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Il Paziente è stato eliminato correttamente", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            }),
            @ApiResponse(responseCode = "409", description = "Non esiste alcun Paziente con quel Codice Fiscale registrato nel Database", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            })
    })
    @DeleteMapping(path = "/{taxCode}", consumes = MediaType.APPLICATION_JSON_VALUE) @SneakyThrows
    public ResponseEntity<Message> deletePatientByTaxCode(@Parameter(description = "Codice Fiscale del Paziente da rimuovere") @PathVariable String taxCode) {
        final Optional<PersonDTO> patientDTO = service.findByTaxCode(taxCode);
        if (patientDTO.isEmpty() || !(patientDTO.get() instanceof PatientDTO)) {
            throw new ConflictException("Paziente non registrato, usare metodo POST per registrarlo!");
        }
        service.deleteByTaxCode(taxCode);
        return ResponseEntity.ok(new Message(LocalDate.now(), HttpStatus.OK.value(), "Paziente eliminato con successo!"));
    }
}
