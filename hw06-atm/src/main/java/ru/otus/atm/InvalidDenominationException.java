package ru.otus.atm;

public class InvalidDenominationException extends RuntimeException {
    public InvalidDenominationException(String message) {
        super(message);
    }
}
