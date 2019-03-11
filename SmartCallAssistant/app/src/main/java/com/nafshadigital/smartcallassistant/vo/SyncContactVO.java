package com.nafshadigital.smartcallassistant.vo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class SyncContactVO implements Serializable {

    public String id = "";
    public String user_id = "";
    public String contact_number = "";
    public String contact_name = "";


    public void SyncContactVO(){
        id = "";
        user_id = "";
        contact_number = "";
        contact_name = "";
    }

    public JSONObject toJSONObject(){
        JSONObject temp = new JSONObject();
        try {
            temp.put("id", id);
            temp.put("user_id", user_id);
            temp.put("contact_number", contact_number);
            temp.put("contact_name", contact_name);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return temp;
    }

    public JSONObject stringToJSONObject(String result){
    try{
        return new JSONObject(result);
    }catch (Exception e){
        e.printStackTrace();
    }
    return new JSONObject();
    }

    public SyncContactVO getSyncContactVO(JSONObject jsObj) throws JSONException {
        SyncContactVO temp = new SyncContactVO();

        if(!jsObj.isNull("id"))
            temp.id = jsObj.getString("id");
        if(!jsObj.isNull("user_id"))
            temp.user_id = jsObj.getString("user_id");
        if(!jsObj.isNull("contact_number"))
            temp.contact_number = jsObj.getString("contact_number");
        if(!jsObj.isNull("contact_name"))
            temp.contact_name = jsObj.getString("contact_name");
        return temp;
    }

    public ArrayList<SyncContactVO> getNotifyArraylist(JSONArray jsa) throws JSONException{
        ArrayList<SyncContactVO> pro = new ArrayList<SyncContactVO>();
        SyncContactVO temp;
        JSONObject jso;

        for(int i=0; i<jsa.length(); i++){
            temp = new SyncContactVO();
            jso = (JSONObject) jsa.get(i);

            if(!jso.isNull("id"))
                temp.id = jso.getString("id");
            if(!jso.isNull("user_id"))
                temp.user_id = jso.getString("user_id");
            if(!jso.isNull("contact_number"))
                temp.contact_number = jso.getString("contact_number");
            if(!jso.isNull("contact_name"))
                temp.contact_name = jso.getString("contact_name");
            else
                temp.contact_name = "User";

            pro.add(temp);
        }
        return pro;
    }
}
