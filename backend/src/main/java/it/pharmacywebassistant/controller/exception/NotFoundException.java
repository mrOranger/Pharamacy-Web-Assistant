package it.pharmacywebassistant.controller.exception;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public final class NotFoundException extends Exception {

    private static final long serialVersionUID = -3441712789492284051L;
	private String message;

    public NotFoundException() {
        this.message = "Nessun elemento presente nel database!";
    }

    public NotFoundException(String message) {
        this.message = message;
    }
}
