package com.thoughtworks.parking_lot.core;

public class CustomError {
    private String message;
    private int statusCode;

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
