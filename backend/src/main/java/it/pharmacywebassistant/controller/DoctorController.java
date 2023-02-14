package it.pharmacywebassistant.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.pharmacywebassistant.controller.message.Message;
import it.pharmacywebassistant.model.Doctor;
import it.pharmacywebassistant.model.dto.DoctorDTO;
import it.pharmacywebassistant.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "v1/api/doctors/", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Controller relativo ad un Dottore", description = "Controller per le operazioni riguardo un Dottore")
public final class DoctorController {

    @Autowired
    private PersonService service;

    public ResponseEntity<List<DoctorDTO>> getAllDoctors() {
        return null;
    }

    public ResponseEntity<DoctorDTO> getDoctorByTaxCode(String taxCode) {
        return null;
    }

    public ResponseEntity<Message> postDoctor(Doctor doctor) {
        return null;
    }

    public ResponseEntity<Message> putDoctor(String taxCode, Doctor doctor) {
        return null;
    }

    public ResponseEntity<Message> deleteAllDoctors() {
        return null;
    }

    public ResponseEntity<Message> deleteDoctorByTaxCode(String taxCode) {
        return null;
    }
}
