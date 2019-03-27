package com.nafshadigital.smartcallassistant.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SaveContactsResponse {

    @SerializedName("success")
    @Expose
    public Boolean success;
    @SerializedName("httpCode")
    @Expose
    private Integer httpCode;
    @SerializedName("error")
    public String error;


    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(Integer httpCode) {
        this.httpCode = httpCode;
    }

}
