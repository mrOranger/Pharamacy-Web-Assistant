package it.pharmacywebassistant.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.pharmacywebassistant.controller.message.Message;
import it.pharmacywebassistant.model.Doctor;
import it.pharmacywebassistant.model.Patient;
import it.pharmacywebassistant.model.Prescription;
import it.pharmacywebassistant.model.dto.PrescriptionDTO;
import it.pharmacywebassistant.service.PrescriptionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "v1/api/prescriptions/", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Controller relativo ad una Prescrizione", description = "Controller per le operazioni riguardo una Prescrizione medica, fatta da un Dottore per un Paziente")
public final class PrescriptionController {

    @Autowired
    private PrescriptionService service;

    @Autowired
    private ResourceBundleMessageSource resourceBundleMessageSource;

    @Operation(summary = "GET Prescrizioni", description = "Restituisce tutte le Prescrizioni Mediche registrate all'interno del Database, " +
            "se queste sono presenti, altrimenti restituisce un messaggio di errore")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Il server ha trovato delle Prescrizioni Mediche correttamente registrate..", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PrescriptionDTO.class)))
            }),
            @ApiResponse(responseCode = "404", description = "Il Server non ha trovato alcuna Prescrizione Medica correttamente registrata nel Database" +
                    " è necessario aggiungerne una nuova.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            })
    })
    @GetMapping(path = "/")
    public ResponseEntity<List<PrescriptionDTO>> getAllPrescriptions() {
        return null;
    }

    @Operation(summary = "GET Prescrizioni di un Paziente", description = "Restituisce tutte le Prescrizioni Mediche registrate all'interno del Database, " +
            "relative ad un Paziente indentificato dal Codice Fiscale passato come parametro, " +
            "se queste sono presenti, altrimenti restituisce un messaggio di errore")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Il server ha trovato delle Prescrizioni Mediche correttamente registrate, " +
                    "per il Paziente con Codice Fiscale passato come parametro", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PrescriptionDTO.class)))
            }),
            @ApiResponse(responseCode = "404", description = "Il Server non ha trovato alcuna Prescrizione Medica correttamente registrata nel Database" +
                    " per il Paziente con Codice Fiscale passato in input, è necessario aggiungere una nuova prescrizione.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            })
    })
    @GetMapping(path = "/patient/{taxCode}")
    public ResponseEntity<List<PrescriptionDTO>> getAllPrescriptionsByPatientTaxCode(@Parameter(description = "Codice Fiscale del Paziente di cui si vogliono ricercare le prescrizioni") @PathVariable String taxCode) {
        return null;
    }

    @Operation(summary = "GET Prescrizioni di un Dottore", description = "Restituisce tutte le Prescrizioni Mediche registrate all'interno del Database, " +
            " effettuate da un Dottore indentificato dal Codice Fiscale passato come parametro, " +
            "se queste sono presenti, altrimenti restituisce un messaggio di errore")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Il server ha trovato delle Prescrizioni Mediche correttamente registrate, " +
                    "effettuate dal Dottore identificato dal Codice Fiscale passato come parametro", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PrescriptionDTO.class)))
            }),
            @ApiResponse(responseCode = "404", description = "Il Server non ha trovato alcuna Prescrizione Medica correttamente registrata nel Database" +
                    " effettuate dal Dottore identificato dal Codice Fiscale passato in input, è necessario aggiungere una nuova prescrizione.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            })
    })
    @GetMapping(path = "/doctor/{taxCode}")
    public ResponseEntity<List<PrescriptionDTO>> getAllPrescriptionsByDoctorTaxCode(@Parameter(description = "Codice Fiscale del Dottore di cui si vogliono ricercare le prescrizioni") @PathVariable String taxCode) {
        return null;
    }

    @Operation(summary = "GET Prescrizione", description = "Restituisce una Prescrizione Medica, identificata dall'id passato come parametro, " +
            "se questa è presente, altrimenti restituisce un messaggio di errore")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Il server ha trovato una Prescrizione Mediche correttamente registrata" ,
                    content = {
                        @Content(mediaType = "application/json", schema = @Schema(implementation = PrescriptionDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Il Server non ha trovato alcuna Prescrizione Medica correttamente registrata nel Database" +
                    " è necessario aggiungere una nuova prescrizione.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            })
    })
    @GetMapping(path = "/{id}")
    public ResponseEntity<PrescriptionDTO> getPrescriptionById(@Parameter(description = "ID della Prescrizione Medica che si vuole ricercare") @PathVariable Long id) {
        return null;
    }

    @Operation(summary = "POST nuova Prescrizione Medica", description = "Inserisce una nuova Prescizione Medica, se questa è valida, all'interno del Database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La nuova Prescrizione Medica è stata registrata correttamente.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            }),
            @ApiResponse(responseCode = "400", description = "Il formato della richiesta non è valido", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            }),
            @ApiResponse(responseCode = "409", description = "La Prescizione Medica è già registrata nel Database, per modificarla usare metodo PUT.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            })
    })
    @PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Message> postPrescription(@Parameter(description = "Nuova Prescrizione Medica da inserire nel Database, in formato valido.") @Valid @RequestBody Prescription prescription, BindingResult bindingResult) {
        return null;
    }

    @Operation(summary = "PUT Prescrizione Medica", description = "Modifica una Prescrizione Medica già registrata nel Database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La Prescrizione Medica è stata modificata correttamente", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            }),
            @ApiResponse(responseCode = "400", description = "Il formato della richiesta non è valido.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            }),
            @ApiResponse(responseCode = "404", description = "La Prescrizione Medica non è registrata, usare il metodo POST per inserirne una nuova.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            })
    })
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Message> putPrescription(@Parameter(description = "ID della Prescrizione Medica da modificare") @PathVariable Long id, @Parameter(description = "Prescrizione Medica in formato valido, da modificare") @Valid @RequestBody Prescription prescription, BindingResult bindingResult) {
        return null;
    }

    @Operation(summary = "PUT Dottore di una Prescrizione Medica", description = "Modifica il Dottore che ha registrato una Prescrizione Medica già registrata nel Database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Il Dottore è stato modificato correttamente", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            }),
            @ApiResponse(responseCode = "400", description = "Il formato della richiesta non è valido.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            }),
            @ApiResponse(responseCode = "404", description = "La Prescrizione Medica non è registrata, usare il metodo POST per inserirne una nuova.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            })
    })
    @PutMapping(path = "/{id}/doctor/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Message> putPrescriptionDoctor( @Parameter(description = "ID della Prescrizione Medica da modificare") @PathVariable Long id, @Parameter(description = "Dottore della Prescrizione Medica da modificare") @Valid @RequestBody Doctor doctor, BindingResult bindingResult) {
        return null;
    }

    @Operation(summary = "PUT Paziente di una Prescrizione Medica", description = "Modifica il Paziente di una Prescrizione Medica già registrata nel Database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Il Paziente è stato modificato correttamente", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            }),
            @ApiResponse(responseCode = "400", description = "Il formato della richiesta non è valido.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            }),
            @ApiResponse(responseCode = "404", description = "La Prescrizione Medica non è registrata, usare il metodo POST per inserirne una nuova.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            })
    })
    @PutMapping(path = "/{id}/patient/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Message> putPrescriptionPatient( @Parameter(description = "ID della Prescrizione Medica da modificare") @PathVariable Long id, @Parameter(description = "Paziente della Prescrizione Medica da modificare") @Valid @RequestBody Patient patient, BindingResult bindingResult) {
        return null;
    }

    @Operation(summary = "DELETE Prescrizione Medica", description = "Elimina una Prescrizione Medica già registrata correttamente nel Database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La Prescrizione Medica è stata eliminata correttamente.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            }),
            @ApiResponse(responseCode = "409", description = "Non esiste alcuna prescrizione Medica correttamente registrata con qull'Id nel database.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            })
    })
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Message> deletePrescriptionById( @Parameter(description = "ID della Prescrizione Medica da eliminare") @PathVariable Long id) {
        return null;
    }
}
