package com.nafshadigital.smartcallassistant.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SaveAllContactVO {
    @SerializedName("user_id")
    @Expose
    private int user_id;
    @SerializedName("contacts")
    @Expose
    private List<SaveContact> contacts;

    public List<SaveContact> getContacts() {
        return contacts;
    }

    public void setContacts(List<SaveContact> contacts) {
        this.contacts = contacts;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
