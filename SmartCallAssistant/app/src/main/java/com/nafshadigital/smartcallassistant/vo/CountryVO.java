package com.nafshadigital.smartcallassistant.vo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class CountryVO implements Serializable {
    public String id;
    public String country_code;
    public String country;
    public String is_active;

    public void CountryVO() {
        id = "";
        country_code = "";
        country = "";
        is_active = "";
    }

    public JSONObject toJSONObject() {
        JSONObject temp = new JSONObject();
        try {
            temp.put("id", id);
            temp.put("country_code", country_code);
            temp.put("country", country);
            temp.put("is_active", is_active);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return temp;
    }

    public CountryVO getCountryVO(JSONObject jsObj) throws JSONException {
        CountryVO temp = new CountryVO();

        if (!jsObj.isNull("id"))
            temp.id = jsObj.getString("id");
        if (!jsObj.isNull("country_code"))
            temp.country_code = jsObj.getString("country_code");
        if (!jsObj.isNull("country"))
            temp.country = jsObj.getString("country");
        if (!jsObj.isNull("is_active"))
            temp.is_active = jsObj.getString("is_active");

        return temp;
    }

    public ArrayList<CountryVO> getCountryArrayList(JSONArray jsa) throws JSONException {
        ArrayList<CountryVO> pro = new ArrayList<CountryVO>();
        CountryVO temp;
        JSONObject jso;

        for (int i = 0; i < jsa.length(); i++) {
            temp = new CountryVO();

            jso = (JSONObject) jsa.get(i);

            if (jso.isNull("id"))
                temp.id = i+"";
            if (!jso.isNull("dial_code"))
                temp.country_code = jso.getString("dial_code");
            if (!jso.isNull("name"))
                temp.country = jso.getString("name");

            if (!jso.isNull("id"))
                temp.id = jso.getString("id");
            if (!jso.isNull("country_code"))
                temp.country_code = jso.getString("country_code");
            if (!jso.isNull("country"))
                temp.country = jso.getString("country");
            if (!jso.isNull("is_active"))
                temp.is_active = jso.getString("is_active");

            pro.add(temp);
        }
        return pro;
    }

    public String toString() {
        return country;
    }
}
