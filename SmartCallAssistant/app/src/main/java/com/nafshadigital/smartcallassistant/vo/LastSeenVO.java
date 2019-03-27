package com.nafshadigital.smartcallassistant.vo;

public class LastSeenVO {
    private String token;

    public LastSeenVO(String token){
        this.token=token;
    }
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
