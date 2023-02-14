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
import it.pharmacywebassistant.model.Doctor;
import it.pharmacywebassistant.model.dto.DoctorDTO;
import it.pharmacywebassistant.model.dto.PersonDTO;
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
@RequestMapping(path = "v1/api/doctors/", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Controller relativo ad un Dottore", description = "Controller per le operazioni riguardo un Dottore")
public final class DoctorController {

    @Autowired
    private PersonService service;

    @Autowired
    private ResourceBundleMessageSource errorMessage;

    @Operation(summary = "GET Doctors", description = "Restituisce tutti i Dottori registrati nel Database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sono presenti dei Dottori all'interno del Database", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PersonDTO.class)))
            }),
            @ApiResponse(responseCode = "404", description = "Non sono stati trovati dei Dottori all'interno del Database", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            })
    })
    @GetMapping(path = "/") @SneakyThrows
    public ResponseEntity<List<PersonDTO>> getAllDoctors() {
        final List<PersonDTO> patientDTOList = service.findAllDoctors();
        if(patientDTOList.isEmpty()) {
            throw new NotFoundException("Nessun Dottore registrato nel Database!");
        }
        return ResponseEntity.ok(patientDTOList);
    }

    @Operation(summary = "GET Doctor by Tax Code", description = "Restituisce il Dottore corrispondente al Codice Fiscale passato come parametro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "E'stato trovato un Dottore corrispondente al Codice Fiscale come parametro.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PersonDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Non è stato trovato alcun Dottore con Codice Fiscale corrispondente al valore passato come parametro.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            })
    })
    @GetMapping(path = "/{taxCode}") @SneakyThrows
    public ResponseEntity<PersonDTO> getDoctorByTaxCode(@Parameter(description = "Codice Fiscale del Dottore da cercare") @PathVariable String taxCode) {
        final Optional<PersonDTO> doctor = service.findByTaxCode(taxCode);
        if(doctor.isEmpty() || !(doctor.get() instanceof DoctorDTO)) {
            throw new NotFoundException("Nessun Dottore registrato con Codice Fiscale " + taxCode + "!");
        }
        return ResponseEntity.ok(doctor.get());
    }

    @Operation(summary = "POST new Doctor", description = "Inserisce un nuovo Dottore all'interno del Database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Il nuovo Dottore è stato inserito correttamente.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            }),
            @ApiResponse(responseCode = "400", description = "Il formato della richiesta non è valido", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            }),
            @ApiResponse(responseCode = "409", description = "Il Dottore è già registrato, usare il metodo PUT per modificarlo", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            })
    })
    @PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE) @SneakyThrows
    public ResponseEntity<Message> postDoctor(@Parameter(description = "Paziente da registrare") @Valid @RequestBody Doctor doctor, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            System.out.println(errorMessage.getMessage(bindingResult.getFieldError(), LocaleContextHolder.getLocale()));
            throw new BadRequestException(errorMessage.getMessage(bindingResult.getFieldError(), LocaleContextHolder.getLocale()));
        }
        final Optional<PersonDTO> doctorDTO = service.findByTaxCode(doctor.getTaxCode());
        if (doctorDTO.isPresent() && (doctorDTO.get() instanceof DoctorDTO)) {
            throw new ConflictException("Il Dottore e' gia' registrato nel Database, usare metodo PUT per modificarlo!");
        }
        service.save(doctor);
        return ResponseEntity.ok(new Message(LocalDate.now(), HttpStatus.OK.value(), "Dottore registrato con successo!"));
    }

    @Operation(summary = "PUT a Doctor", description = "Modifica un Dottore registrato nel Database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Il Dottore è stato modificato correttamente", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            }),
            @ApiResponse(responseCode = "400", description = "Il formato della richiesta non è valido", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            }),
            @ApiResponse(responseCode = "404", description = "Il Dottore non è registrato, usare il metodo POST per inserirlo", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            })
    })
    @PutMapping(path = "/{taxCode}", consumes = MediaType.APPLICATION_JSON_VALUE) @SneakyThrows
    public ResponseEntity<Message> putDoctor(@Parameter(description = "Codice Fiscale del Dottore da modificare") @PathVariable String taxCode, @Parameter(description = "Dottore da modificare") @Valid @RequestBody Doctor doctor, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            System.out.println(errorMessage.getMessage(bindingResult.getFieldError(), LocaleContextHolder.getLocale()));
            throw new BadRequestException(errorMessage.getMessage(bindingResult.getFieldError(), LocaleContextHolder.getLocale()));
        }
        doctor.setTaxCode(taxCode);
        final Optional<PersonDTO> doctorDTO = service.findByTaxCode(taxCode);
        if (doctorDTO.isEmpty() || !(doctorDTO.get() instanceof DoctorDTO)) {
            throw new ConflictException("Dottore non registrato, usare metodo POST per registrarlo!");
        }
        service.save(doctor);
        return ResponseEntity.ok(new Message(LocalDate.now(), HttpStatus.OK.value(), "Dottore modificato con successo!"));
    }

    @Operation(summary = "DELETE a Patient by Tax Code", description = "Elimina un Dottore corrispondente al Codice Fiscale passato come parametro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Il Dottore è stato eliminato correttamente", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            }),
            @ApiResponse(responseCode = "409", description = "Non esiste alcun Dottore quel Codice Fiscale registrato nel Database", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            })
    })
    @DeleteMapping(path = "/{taxCode}", consumes = MediaType.APPLICATION_JSON_VALUE) @SneakyThrows
    public ResponseEntity<Message> deleteDoctorByTaxCode(@Parameter(description = "Codice Fiscale del Dottore da rimuovere") @PathVariable  String taxCode) {
        final Optional<PersonDTO> doctorDTO = service.findByTaxCode(taxCode);
        if (doctorDTO.isEmpty() || !(doctorDTO.get() instanceof DoctorDTO)) {
            throw new ConflictException("Dottore non registrato, usare metodo POST per registrarlo!");
        }
        service.deleteByTaxCode(taxCode);
        return ResponseEntity.ok(new Message(LocalDate.now(), HttpStatus.OK.value(), "Dottore eliminato con successo!"));
    }
}
