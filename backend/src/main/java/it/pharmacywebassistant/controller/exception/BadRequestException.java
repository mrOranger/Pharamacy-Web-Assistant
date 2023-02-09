package it.pharmacywebassistant.controller.exception;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public final class BadRequestException extends Exception {

    private String message;

    public BadRequestException() {
        this.message = "Errore nel contenuto della richiesta!";
    }

    public BadRequestException(String message) {
        this.message = message;
    }
}
