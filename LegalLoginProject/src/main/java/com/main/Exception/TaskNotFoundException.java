package com.scriza.CaseManagement.Exceptions;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(String noTaskFoundWithTask) {
        super(noTaskFoundWithTask);
    }
}
