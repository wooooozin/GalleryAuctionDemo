package com.woozi.auction.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(Long id, String message) {
        super(message + "id: " + id);
    }
}
