package it.pharmacywebassistant.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
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
import it.pharmacywebassistant.service.DrugsInPrescriptionService;
import jakarta.validation.Valid;
import lombok.SneakyThrows;

@RestController
@RequestMapping(path = "v1/api/prescriptions/", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Controller relativo ai Farmaci registrati nelle Prescrizioni", description = "Controller per le operazioni riguardo un Farmaco, registrato in una Prescrizione Medica, fatta da un Dottore per un Paziente")
public class PrescriptionDrugsController {
	
    @Autowired
    private DrugsInPrescriptionService service;

    @Autowired
    private ResourceBundleMessageSource resourceBundleMessageSource;

    @Operation(summary = "GET Farmaci in Prescrizione", description = "Restituisce tutti i Farmaci registrati in una Prescrizione Medica"
    		+ " prescritta da un Dottore per un Paziente. Se la Prescrizione Medica non è registrata, oppure non ci sono Farmaci registrati per questa, allora viene restituito un messaggio di errore.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La risposta è correttamente evasa, sono stati trovati dei Farmaci all'interno della Prescrizione Medica indicata in input.", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = DrugDTO.class)))
            }),
            @ApiResponse(responseCode = "404", description = "Non è stato possibile trovare la relativa Prescrizione Medica correttamente registrata nel Database,"
            		+ "oppure la Prescrizione Medica indicata, non contiene Farmaci.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            })
    })
    @GetMapping(path = "{prescriptionId}/drugs/") @SneakyThrows
    public ResponseEntity<List<DrugDTO>> getAllDrugsInPrescription(@PathVariable Long prescriptionId) {
        final List<DrugDTO> drugsInPrescription = service.findAllDrugs(prescriptionId);
        if(drugsInPrescription == null) {
        	throw new NotFoundException("Prescrizione " + prescriptionId + " non presente nel Database!");
        }
        if(drugsInPrescription.isEmpty()) {
        	throw new NotFoundException("La Prescrizione " + prescriptionId + " non contiene Farmaci prescritti!");
        }
        return ResponseEntity.ok(drugsInPrescription);
    }

    @Operation(summary = "GET Farmaco in Prescrizione", description = "Restituisce il Farmaco indicato dall'Id registrato in una Prescrizione Medica"
    		+ " prescritta da un Dottore per un Paziente. Se la Prescrizione Medica non è registrata, oppure il Farmaco non è stato correttamente registrato, allora viene restituito un messaggio di errore.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La risposta è correttamente evasa, è stato trovato un Farmaco registrato correttamente all'interno della Prescrizione Medica indicata in input.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = DrugDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Non è stato possibile trovare la relativa Prescrizione Medica correttamente registrata nel Database,"
            		+ "oppure il Farmaco indicato, non è registrato per la Prescrizione Medica.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            })
    })
    @GetMapping(path = "{prescriptionId}/drugs/{drugId}") @SneakyThrows
    public ResponseEntity<DrugDTO> getDrugInPrescription(@PathVariable Long prescriptionId, @PathVariable Long drugId) {
        final Optional<DrugDTO> drugInPrescription = service.findByDrugId(prescriptionId, drugId);
        if(drugInPrescription == null) {
        	throw new NotFoundException("Prescrizione " + prescriptionId + " non presente nel Database!");
        }
        if(drugInPrescription.isEmpty()) {
        	throw new NotFoundException("La Prescrizione " + prescriptionId + " non contiene il Farmaco " + drugId + "!");
        }
        return ResponseEntity.ok(drugInPrescription.get());
   }
    
    @Operation(summary = "POST Medicina in Prescrizione Medica", description = "Inserisce una nuova Medicina all'interno di una Prescrizione Medica identificata dall'Id passato come parametro"
    		+ ". Se la Prescrizione Medica non e' presente, restituisce un messaggio di errore con codice NotFound. Se il formato della richiesta non è valido, restituisce un messaggio di errore con codice Bad Request."
    		+ "Se la Medicina e' gia' registrata all'interno della Prescrizione Medica, restituisce un messaggio di errore con codice Conflict.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La nuova Medicina e' stata registrata correttamente.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            }),
            @ApiResponse(responseCode = "400", description = "Il formato della richiesta non è valido", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            }),
            @ApiResponse(responseCode = "409", description = "Il Farmaco e' gia' registrato all'interno della Prescrizione Medica, usare metodo PUT per modificarlo.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            })
    })
    @PostMapping(path = "{prescriptionId}/drugs/", consumes = MediaType.APPLICATION_JSON_VALUE) @SneakyThrows
    public ResponseEntity<Message> postDrugInPrescription(@PathVariable Long prescriptionId, @Valid @RequestBody Drug drug, BindingResult bindingResult) {
    	
    	if(bindingResult.hasErrors()) {
    		throw new BadRequestException(resourceBundleMessageSource.getMessage(bindingResult.getFieldError(), LocaleContextHolder.getLocale()));
    	}
    	
        final Optional<DrugDTO> drugInPrescription = service.findByDrugId(prescriptionId, drug.getId());
        
        if(drugInPrescription == null) {
        	throw new NotFoundException("Prescrizione " + prescriptionId + " non presente nel Database!");
        }
        
        if(drugInPrescription.isPresent()) {
        	throw new ConflictException("Il Farmaco e' gia' registrato all'interno della Prescrizione Medica, usare metodo PUT per modificarlo.");
        }
        
        service.save(prescriptionId, drug);
        return ResponseEntity.ok(new Message(LocalDate.now(), HttpStatus.OK.value(), "Farmaco registrato correttamente all'interno della Prescrizione!"));
    }
    
    @Operation(summary = "PUT Medicina in Prescrizione Medica", description = "Modifica una Medicina all'interno di una Prescrizione Medica identificata dall'Id passato come parametro"
    		+ ". Se la Prescrizione Medica non e' presente, restituisce un messaggio di errore con codice NotFound. Se il formato della richiesta non è valido, restituisce un messaggio di errore con codice Bad Request."
    		+ "Se la Medicina non e' registrata all'interno della Prescrizione Medica, restituisce un messaggio di errore con codice Conflict.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La nuova Medicina e' stata registrata correttamente.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            }),
            @ApiResponse(responseCode = "400", description = "Il formato della richiesta non è valido", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            }),
            @ApiResponse(responseCode = "404", description = "Il Farmaco non e' registrato all'interno della Prescrizione Medica, usare metodo POST per registrarlo.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            })
    })
    @PutMapping(path = "{prescriptionId}/drugs/{drugId}/", consumes = MediaType.APPLICATION_JSON_VALUE) @SneakyThrows
    public ResponseEntity<Message> putDrugInPrescription(@PathVariable Long prescriptionId, @PathVariable Long drugId, @Valid @RequestBody Drug drug, BindingResult bindingResult) {
    	
    	drug.setId(drugId);
    	
    	if(bindingResult.hasErrors()) {
    		throw new BadRequestException(resourceBundleMessageSource.getMessage(bindingResult.getFieldError(), LocaleContextHolder.getLocale()));
    	}
    	
        final Optional<DrugDTO> drugInPrescription = service.findByDrugId(prescriptionId, drug.getId());
        
        if(drugInPrescription == null) {
        	throw new NotFoundException("Prescrizione " + prescriptionId + " non presente nel Database!");
        }
        
        if(drugInPrescription.isEmpty()) {
        	throw new NotFoundException("Il Farmaco non e' registrato all'interno della Prescrizione Medica, usare metodo POST per registrarlo.");
        }
        
        service.update(prescriptionId, drugId, drug);
        return ResponseEntity.ok(new Message(LocalDate.now(), HttpStatus.OK.value(), "Farmaco modificato correttamente all'interno della Prescrizione!"));
    } 
    
    @Operation(summary = "DELETE Medicina in Prescrizione Medica", description = "Elimina una Medicina all'interno di una Prescrizione Medica identificata dall'Id passato come parametro"
    		+ ". Se la Prescrizione Medica non e' presente, restituisce un messaggio di errore con codice NotFound. "
    		+ "Se la Medicina non e' registrata all'interno della Prescrizione Medica, restituisce un messaggio di errore con codice Conflict.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La nuova Medicina e' stata registrata correttamente.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            }),
            @ApiResponse(responseCode = "404", description = "Il Farmaco non e' registrato all'interno della Prescrizione Medica, usare metodo POST per registrarlo.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            })
    })
    @DeleteMapping(path = "{prescriptionId}/drugs/") @SneakyThrows
    public ResponseEntity<Message> deleteDrugsInPrescription(@PathVariable Long prescriptionId) {
        final List<DrugDTO> drugsInPrescription = service.findAllDrugs(prescriptionId);
        if(drugsInPrescription == null) {
        	throw new NotFoundException("Prescrizione " + prescriptionId + " non presente nel Database!");
        }
        if(drugsInPrescription.isEmpty()) {
        	throw new NotFoundException("La Prescrizione " + prescriptionId + " non contiene nessun Farmaco!");
        }
        service.deleteAllDrugs(prescriptionId);
        return ResponseEntity.ok(new Message(LocalDate.now(), HttpStatus.OK.value(), "Farmaci eliminati correttamente dalla Prescrizione!"));
    }
    
    @Operation(summary = "DELETE Medicina in Prescrizione Medica", description = "Elimina una Medicina all'interno di una Prescrizione Medica identificata dall'Id passato come parametro"
    		+ ". Se la Prescrizione Medica non e' presente, restituisce un messaggio di errore con codice NotFound. "
    		+ "Se la Medicina non e' registrata all'interno della Prescrizione Medica, restituisce un messaggio di errore con codice Conflict.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La nuova Medicina e' stata registrata correttamente.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            }),
            @ApiResponse(responseCode = "404", description = "Il Farmaco non e' registrato all'interno della Prescrizione Medica, usare metodo POST per registrarlo.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            })
    })
    @DeleteMapping(path = "{prescriptionId}/drugs/{drugId}/") @SneakyThrows
    public ResponseEntity<Message> deleteDrugInPrescription(@PathVariable Long prescriptionId, @PathVariable Long drugId) {
        final List<DrugDTO> drugsInPrescription = service.findAllDrugs(prescriptionId);
        if(drugsInPrescription == null) {
        	throw new NotFoundException("Prescrizione " + prescriptionId + " non presente nel Database!");
        }
        if(drugsInPrescription.isEmpty()) {
        	throw new NotFoundException("La Prescrizione " + prescriptionId + " non contiene nessun Farmaco!");
        }
        service.deleteByDrugId(prescriptionId, drugId);
        return ResponseEntity.ok(new Message(LocalDate.now(), HttpStatus.OK.value(), "Farmaco eliminato correttamente dalla Prescrizione!"));
    }
}
