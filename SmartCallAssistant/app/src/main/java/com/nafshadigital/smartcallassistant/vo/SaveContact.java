package com.nafshadigital.smartcallassistant.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SaveContact {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phone")
    @Expose
    private String phone;


    public SaveContact() {

    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
