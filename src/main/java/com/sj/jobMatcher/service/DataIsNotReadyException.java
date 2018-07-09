package com.sj.jobMatcher.service;

public class DataIsNotReadyException extends Exception {

    public DataIsNotReadyException(String message) {
        super(message);
    }

    public DataIsNotReadyException(String message, Throwable cause) {
        super(message, cause);
    }
}