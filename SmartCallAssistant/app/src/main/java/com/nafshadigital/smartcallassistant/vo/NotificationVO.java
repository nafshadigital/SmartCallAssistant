package com.nafshadigital.smartcallassistant.vo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class NotificationVO implements Serializable {

    public String id = "";
    public String user_id = "";
    public String phnNum = "";
    public String message = "";
    public String name = "";
    public String date = "";


    public void NotificationVO(){
        id = "";
        user_id = "";
        phnNum = "";
        message = "";
        name = "";
        date = "";
    }

    public JSONObject toJSONObject(){
        JSONObject temp = new JSONObject();
        try {
            temp.put("id", id);
            temp.put("user_id", user_id);
            temp.put("phnNum", phnNum);
            temp.put("message", message);
            temp.put("name", name);
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

    public NotificationVO getNotificationVO(JSONObject jsObj) throws JSONException {
        NotificationVO temp = new NotificationVO();

        if(!jsObj.isNull("id"))
            temp.id = jsObj.getString("id");
        if(!jsObj.isNull("user_id"))
            temp.user_id = jsObj.getString("user_id");
        if(!jsObj.isNull("phnNum"))
            temp.phnNum = jsObj.getString("phnNum");
        if(!jsObj.isNull("message"))
            temp.message = jsObj.getString("message");
        if(!jsObj.isNull("name"))
            temp.name = jsObj.getString("name");
        if(!jsObj.isNull("created_date"))
            temp.date = jsObj.getString("created_date");
        return temp;
    }

    public ArrayList<NotificationVO> getNotifyArraylist(JSONArray jsa) throws JSONException{
        ArrayList<NotificationVO> pro = new ArrayList<NotificationVO>();
        NotificationVO temp;
        JSONObject jso;

        for(int i=0; i<jsa.length(); i++){
            temp = new NotificationVO();
            jso = (JSONObject) jsa.get(i);

            if(!jso.isNull("id"))
                temp.id = jso.getString("id");
            if(!jso.isNull("user_id"))
                temp.user_id = jso.getString("user_id");
            if(!jso.isNull("phnNum"))
                temp.phnNum = jso.getString("phnNum");
            if(!jso.isNull("message"))
                temp.message = jso.getString("message");
            if(!jso.isNull("name"))
                temp.name = jso.getString("name");
            else
                temp.name = "User";

            if(!jso.isNull("created_date"))
                temp.date = jso.getString("created_date");
            pro.add(temp);
        }
        return pro;
    }
}
