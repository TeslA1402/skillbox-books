package org.example.skillboxbooks.advice;

public record ErrorDescription(String message) {
    public ErrorDescription(RuntimeException e) {
        this(e.getMessage());
    }
}