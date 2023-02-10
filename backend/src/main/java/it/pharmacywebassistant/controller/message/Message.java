package it.pharmacywebassistant.controller.message;

import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
public final class Message {

    private LocalDate date;
    private Integer code;
    private String message;

    public Message(LocalDate date, Integer code, String message) {
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

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
