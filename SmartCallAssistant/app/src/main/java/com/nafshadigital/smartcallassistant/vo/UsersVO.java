package com.nafshadigital.smartcallassistant.vo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class UsersVO implements Serializable {
    public String id = "";
    public String name = "";
    public String email = "";
    public String country_code = "";
    public String mobile = "";
    public String is_active = "";
    public String verification_code = "";
    public String user_id = "";
    public String android_id = "";
    public String device_id = "";



    public void UsersVO() {
        id = "";
        name = "";
        email = "";
        country_code = "";
        mobile = "";
        is_active = "";
        verification_code = "";
        user_id = "";
        android_id = "";
        device_id = "";

    }

    public JSONObject toJSONObject() {
        JSONObject temp = new JSONObject();
        try {
            temp.put("id", id);
            temp.put("name", name);
            temp.put("email", email);
            temp.put("country_code", country_code);
            temp.put("mobile", mobile);
            temp.put("is_active", is_active);
            temp.put("verification_code", verification_code);
            temp.put("user_id", user_id);
            temp.put("android_id", android_id);
            temp.put("device_id", device_id);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return temp;
    }

    public UsersVO getUserVO(JSONObject jsObj) throws JSONException {
        UsersVO temp = new UsersVO();

        if (!jsObj.isNull("id"))
            temp.id = jsObj.getString("id");
        if (!jsObj.isNull("name"))
            temp.name = jsObj.getString("name");
        if (!jsObj.isNull("email"))
            temp.email = jsObj.getString("email");
        if (!jsObj.isNull("country_code"))
            temp.country_code = jsObj.getString("country_code");
        if (!jsObj.isNull("mobile"))
            temp.mobile = jsObj.getString("mobile");
        if (!jsObj.isNull("is_active"))
            temp.is_active = jsObj.getString("is_active");
        if (!jsObj.isNull("verification_code"))
            temp.verification_code = jsObj.getString("verification_code");
        if (!jsObj.isNull("user_id"))
            temp.user_id = jsObj.getString("user_id");
        if (!jsObj.isNull("android_id"))
            temp.android_id = jsObj.getString("android_id");
        if (!jsObj.isNull("device_id"))
            temp.device_id = jsObj.getString("device_id");
        return temp;
    }

    public ArrayList<UsersVO> getUserArrayList(JSONArray jsa) throws JSONException {
        ArrayList<UsersVO> pro = new ArrayList<UsersVO>();
        UsersVO temp;
        JSONObject jso;


        for (int i = 0; i < jsa.length(); i++) {
            temp = new UsersVO();

            jso = (JSONObject) jsa.get(i);

            if (!jso.isNull("id"))
                temp.id = jso.getString("id");
            if (!jso.isNull("name"))
                temp.name = jso.getString("name");
            if (!jso.isNull("email"))
                temp.email = jso.getString("email");
            if (!jso.isNull("country_code"))
                temp.country_code = jso.getString("country_code");
            if (!jso.isNull("mobile"))
                temp.mobile = jso.getString("mobile");
            if (!jso.isNull("is_active"))
                temp.is_active = jso.getString("is_active");
            if (!jso.isNull("verification_code"))
                temp.verification_code = jso.getString("verification_code");
            if (!jso.isNull("user_id"))
                temp.user_id = jso.getString("user_id");
            if (!jso.isNull("android_id"))
                temp.android_id = jso.getString("android_id");
            if (!jso.isNull("device_id"))
                temp.device_id = jso.getString("device_id");

            pro.add(temp);
        }
        return pro;
    }
}
