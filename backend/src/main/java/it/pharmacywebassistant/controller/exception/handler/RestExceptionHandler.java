package it.pharmacywebassistant.controller.exception.handler;

import java.time.LocalDate;

import it.pharmacywebassistant.controller.exception.BadRequestException;
import it.pharmacywebassistant.controller.exception.ConflictException;
import it.pharmacywebassistant.controller.exception.NotFoundException;
import it.pharmacywebassistant.controller.message.Message;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public final class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<Message> notFoundExceptionHandler(Exception ex) {
        final Message response = new Message();
        response.setDate(LocalDate.now());
        response.setMessage(ex.getMessage());
        response.setCode(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<Message>(response, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<Message> badRequestExceptionHandler(Exception ex) {
        final Message response = new Message();
        response.setDate(LocalDate.now());
        response.setMessage(ex.getMessage());
        response.setCode(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<Message>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConflictException.class)
    public final ResponseEntity<Message> conflictExceptionHandler(Exception ex) {
        final Message response = new Message();
        response.setDate(LocalDate.now());
        response.setMessage(ex.getMessage());
        response.setCode(HttpStatus.CONFLICT.value());
        return new ResponseEntity<Message>(response, new HttpHeaders(), HttpStatus.CONFLICT);
    }

}