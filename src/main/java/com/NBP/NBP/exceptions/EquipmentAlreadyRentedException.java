package com.NBP.NBP.exceptions;

public class EquipmentAlreadyRentedException extends RuntimeException {
    public EquipmentAlreadyRentedException(String message) {
        super(message);
    }
}
