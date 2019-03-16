package com.nafshadigital.smartcallassistant.vo;

import android.content.Context;

import com.nafshadigital.smartcallassistant.activity.DBHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class SendHeartVO implements Serializable {

    public String sender_id = "";
    public String receiver_id = "";

    public SendHeartVO(){
        sender_id = "";
        receiver_id = "";
    }

    public JSONObject toJSONObject(){
        JSONObject temp = new JSONObject();
        try {
            temp.put("sender_id", sender_id);
            temp.put("receiver_id", receiver_id);
        }catch (JSONException e){
            e.printStackTrace();
            System.out.println("Error in JSON" + e.toString());
        }
        return temp;
    }
}
