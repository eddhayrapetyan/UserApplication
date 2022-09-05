package com.jambit.testdocker.exception;

public class PersonAlreadyExistException extends Exception {
    public PersonAlreadyExistException(String message) {
        super(message);
    }
}