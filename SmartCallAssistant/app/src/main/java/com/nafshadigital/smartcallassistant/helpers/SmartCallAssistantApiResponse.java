package com.nafshadigital.smartcallassistant.helpers;

import com.google.gson.annotations.SerializedName;

public class SmartCallAssistantApiResponse<D> {

    @SerializedName("data")
    public D data;

    @SerializedName("success")
    public String   success;

    @SerializedName("httpCode")
    public int httpCode;

    @SerializedName("error")
    public String error;

    public D getData() {
        return data;
    }

    public String getError() {
        return error;
    }

    public String getSuccess() {
        return success;
    }

    public int getHttpCode(){
        return httpCode;
    }
}