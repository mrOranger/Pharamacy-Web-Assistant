package it.pharmacywebassistant.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.pharmacywebassistant.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "v1/api/patients/", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Controller relativo ad un Paziente", description = "Controller per le operazioni riguardo un Paziente")
public final class PatientController {

    @Autowired
    private PersonService service;



}
