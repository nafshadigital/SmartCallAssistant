package com.nafshadigital.smartcallassistant.vo;

public class VerifyOTPResponse {
    private String status;
    private String message;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}