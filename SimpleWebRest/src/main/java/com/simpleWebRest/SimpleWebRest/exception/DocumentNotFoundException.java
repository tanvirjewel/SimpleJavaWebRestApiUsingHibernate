package com.simpleWebRest.SimpleWebRest.exception;

public class DocumentNotFoundException extends RuntimeException {
    public DocumentNotFoundException(Long id) {
        super("Could not find document " + id);
    }
}
