package com.nafshadigital.smartcallassistant.vo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class CallerNotificationVO implements Serializable {

    public String sender_id = "";
    public String receiver_phone = "";
    public String message = "";
    public String name = "";
    public String date = "";


    public void NotificationVO(){
        sender_id = "";
        receiver_phone = "";
        message = "";
        name = "";
        date = "";
    }

    public JSONObject toJSONObject(){
        JSONObject temp = new JSONObject();
        try {
            temp.put("sender_id", sender_id);
            temp.put("receiver_phone", receiver_phone);
            temp.put("message", message);
            temp.put("contact_name", name);
            temp.put("created_date", date);
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

    public CallerNotificationVO getNotificationVO(JSONObject jsObj) throws JSONException {
        CallerNotificationVO temp = new CallerNotificationVO();

        if(!jsObj.isNull("sender_id"))
            temp.sender_id = jsObj.getString("sender_id");
        if(!jsObj.isNull("receiver_phone"))
            temp.receiver_phone = jsObj.getString("receiver_phone");
        if(!jsObj.isNull("message"))
            temp.message = jsObj.getString("message");
        if(!jsObj.isNull("contact_name"))
            temp.name = jsObj.getString("contact_name");
        if(!jsObj.isNull("created_date"))
            temp.date = jsObj.getString("created_date");
        return temp;
    }

    public ArrayList<CallerNotificationVO> getNotifyArraylist(JSONArray jsa) throws JSONException{
        ArrayList<CallerNotificationVO> pro = new ArrayList<CallerNotificationVO>();
        CallerNotificationVO temp;
        JSONObject jso;

        for(int i=0; i<jsa.length(); i++){
            temp = new CallerNotificationVO();
            jso = (JSONObject) jsa.get(i);

            if(!jso.isNull("sender_id"))
                temp.sender_id = jso.getString("sender_id");
            if(!jso.isNull("receiver_phone"))
                temp.receiver_phone = jso.getString("receiver_phone");
            if(!jso.isNull("message"))
                temp.message = jso.getString("message");
            if(!jso.isNull("contact_name"))
                temp.name = jso.getString("contact_name");
            else
                temp.name = "User";

            if(!jso.isNull("created_date"))
                temp.date = jso.getString("created_date");
            pro.add(temp);
        }
        return pro;
    }
}
