package it.pharmacywebassistant.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.pharmacywebassistant.controller.exception.BadRequestException;
import it.pharmacywebassistant.controller.exception.ConflictException;
import it.pharmacywebassistant.controller.exception.NotFoundException;
import it.pharmacywebassistant.controller.message.Message;
import it.pharmacywebassistant.model.Patient;
import it.pharmacywebassistant.model.dto.PatientDTO;
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
@RequestMapping(path = "v1/api/patients/", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Controller relativo ad un Paziente", description = "Controller per le operazioni riguardo un Paziente")
public final class PatientController {

    @Autowired
    private PersonService service;

    @Autowired
    private ResourceBundleMessageSource errorMessage;

    @GetMapping(path = "/") @SneakyThrows
    public ResponseEntity<List<PersonDTO>> getAllPatients() {
        final List<PersonDTO> patientDTOList = service.findAllPatients();
        if(patientDTOList.isEmpty()) {
            throw new NotFoundException("Nessun Paziente registrato nel Database!");
        }
        return ResponseEntity.ok(patientDTOList);
    }

    @GetMapping(path = "/{taxCode}") @SneakyThrows
    public ResponseEntity<PersonDTO> getPatientByTaxCode(@PathVariable String taxCode) {
        final Optional<PersonDTO> patient = service.findByTaxCode(taxCode);
        if(patient.isEmpty() || !(patient.get() instanceof PatientDTO)) {
            throw new NotFoundException("Nessun paziente registrato con Codice Fiscale " + taxCode + "!");
        }
        return ResponseEntity.ok(patient.get());
    }

    @PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE) @SneakyThrows
    public ResponseEntity<Message> postPatient(@Valid @RequestBody Patient patient, BindingResult bindingResult) {
        System.out.println(patient.getTaxCode());
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

    @PutMapping(path = "/{taxCode}", consumes = MediaType.APPLICATION_JSON_VALUE) @SneakyThrows
    public ResponseEntity<Message> putPatient(@PathVariable String taxCode, @Valid @RequestBody Patient patient, BindingResult bindingResult) {
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

    @DeleteMapping(path = "/{taxCode}", consumes = MediaType.APPLICATION_JSON_VALUE) @SneakyThrows
    public ResponseEntity<Message> deletePatientByTaxCode(@PathVariable String taxCode) {
        final Optional<PersonDTO> patientDTO = service.findByTaxCode(taxCode);
        if (patientDTO.isEmpty() || !(patientDTO.get() instanceof PatientDTO)) {
            throw new ConflictException("Paziente non registrato, usare metodo POST per registrarlo!");
        }
        service.deleteByTaxCode(taxCode);
        return ResponseEntity.ok(new Message(LocalDate.now(), HttpStatus.OK.value(), "Paziente eliminato con successo!"));
    }
}
