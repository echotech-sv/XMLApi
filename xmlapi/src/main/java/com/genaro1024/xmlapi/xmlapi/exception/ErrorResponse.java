package com.genaro1024.xmlapi.xmlapi.exception;

import java.util.*;

import lombok.ToString;

@ToString
public class ErrorResponse {
    private final int status;
    private final String message;
    private final Date timestamp;


    public ErrorResponse(int status, String message, Date timestamp) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }
}
