package it.pharmacywebassistant.controller;

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

    @GetMapping(path = "/") @SneakyThrows
    public ResponseEntity<List<PersonDTO>> getAllDoctors() {
        final List<PersonDTO> patientDTOList = service.findAllPatients();
        if(patientDTOList.isEmpty()) {
            throw new NotFoundException("Nessun Dottore registrato nel Database!");
        }
        return ResponseEntity.ok(patientDTOList);
    }

    @GetMapping(path = "/{taxCode}") @SneakyThrows
    public ResponseEntity<PersonDTO> getDoctorByTaxCode(@PathVariable String taxCode) {
        final Optional<PersonDTO> doctor = service.findByTaxCode(taxCode);
        if(doctor.isEmpty() || !(doctor.get() instanceof DoctorDTO)) {
            throw new NotFoundException("Nessun Dottore registrato con Codice Fiscale " + taxCode + "!");
        }
        return ResponseEntity.ok(doctor.get());
    }

    @PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE) @SneakyThrows
    public ResponseEntity<Message> postDoctor(@Valid @RequestBody Doctor doctor, BindingResult bindingResult) {
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

    @PutMapping(path = "/{taxCode}", consumes = MediaType.APPLICATION_JSON_VALUE) @SneakyThrows
    public ResponseEntity<Message> putDoctor(@PathVariable String taxCode, @Valid @RequestBody Doctor doctor, BindingResult bindingResult) {
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

    @DeleteMapping(path = "/{taxCode}", consumes = MediaType.APPLICATION_JSON_VALUE) @SneakyThrows
    public ResponseEntity<Message> deleteDoctorByTaxCode(@PathVariable  String taxCode) {
        final Optional<PersonDTO> doctorDTO = service.findByTaxCode(taxCode);
        if (doctorDTO.isEmpty() || !(doctorDTO.get() instanceof DoctorDTO)) {
            throw new ConflictException("Dottore non registrato, usare metodo POST per registrarlo!");
        }
        service.deleteByTaxCode(taxCode);
        return ResponseEntity.ok(new Message(LocalDate.now(), HttpStatus.OK.value(), "Dottore eliminato con successo!"));
    }
}
