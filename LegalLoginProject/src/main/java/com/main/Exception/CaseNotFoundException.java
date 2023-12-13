package com.scriza.CaseManagement.Exceptions;

public class CaseNotFoundException extends RuntimeException {
    public CaseNotFoundException(String s) {
        super(s);
    }
}
