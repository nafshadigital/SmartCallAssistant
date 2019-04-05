package com.nafshadigital.smartcallassistant.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SaveAllContactVO {
    @SerializedName("user_id")
    @Expose
    private int user_id;
    @SerializedName("contact_size")
    @Expose
    private int contact_size;
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

    public int getContact_size() {
        return contact_size;
    }
    public void setContact_size(int contact_size) {
        this.contact_size = contact_size;
    }
}
