package com.scriza.CaseManagement.Exceptions;

public class ClientNotFoundException extends RuntimeException {
    public ClientNotFoundException(String s) {
        super(s);
    }
}
