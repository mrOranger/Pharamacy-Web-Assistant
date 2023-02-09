package it.pharmacywebassistant.controller.message;

import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
public final class Message {

    private LocalDate date;
    private Byte code;
    private String message;

    public Message(LocalDate date, Byte code, String message) {
        this.date = date;
        this.code = code;
        this.message = message;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Byte getCode() {
        return code;
    }

    public void setCode(Byte code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
