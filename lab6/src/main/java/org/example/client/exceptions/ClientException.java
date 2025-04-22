package org.example.client.exceptions;

public class ClientException extends RuntimeException {
    public ClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
