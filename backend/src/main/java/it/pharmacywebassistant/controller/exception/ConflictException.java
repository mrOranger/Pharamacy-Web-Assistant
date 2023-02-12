package it.pharmacywebassistant.controller.exception;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ConflictException extends Exception{


    private String message;

    public ConflictException() {
        this.message = "Elemento già presente all'interno del database!";
    }

    public ConflictException(String message) {
        this.message = message;
    }

}
