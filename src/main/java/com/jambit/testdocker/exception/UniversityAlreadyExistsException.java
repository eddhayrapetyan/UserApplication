package com.jambit.testdocker.exception;

public class UniversityAlreadyExistsException extends RuntimeException{
    public UniversityAlreadyExistsException(String message){
        super(message);
    }
}
