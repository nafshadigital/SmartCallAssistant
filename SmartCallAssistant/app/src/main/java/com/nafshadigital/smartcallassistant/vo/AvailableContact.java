package com.nafshadigital.smartcallassistant.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AvailableContact {

    @SerializedName("user_id")
    @Expose
    private String user_id=null;
    @SerializedName("name")
    @Expose
    private String name=null;
    @SerializedName("phone")
    @Expose
    private String phone=null;


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
