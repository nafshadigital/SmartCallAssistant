package com.nafshadigital.smartcallassistant.vo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class FCMNotificationVO implements Serializable {

    public String token = "";
    public String title = "";
    public String message = "";


    public void NotificationVO(){
        token = "";
        title = "";
        message = "";
    }

    public JSONObject toJSONObject(){
        JSONObject temp = new JSONObject();
        try {
                temp.put("token", token);
            temp.put("contact_number", title);
            temp.put("message", message);
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

    public FCMNotificationVO getNotificationVO(JSONObject jsObj) throws JSONException {
        FCMNotificationVO temp = new FCMNotificationVO();

        if(!jsObj.isNull("token"))
            temp.token = jsObj.getString("token");
        if(!jsObj.isNull("contact_number"))
            temp.title = jsObj.getString("contact_number");
        if(!jsObj.isNull("message"))
            temp.message = jsObj.getString("message");
        return temp;
    }

    public ArrayList<FCMNotificationVO> getNotifyArraylist(JSONArray jsa) throws JSONException{
        ArrayList<FCMNotificationVO> pro = new ArrayList<FCMNotificationVO>();
        FCMNotificationVO temp;
        JSONObject jso;

        for(int i=0; i<jsa.length(); i++){
            temp = new FCMNotificationVO();
            jso = (JSONObject) jsa.get(i);

            if(!jso.isNull("token"))
                temp.token = jso.getString("token");
            if(!jso.isNull("contact_number"))
                temp.title = jso.getString("contact_number");
            if(!jso.isNull("message"))
                temp.message = jso.getString("message");

            pro.add(temp);
        }
        return pro;
    }
}
