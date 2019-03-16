package com.nafshadigital.smartcallassistant.vo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nafshadigital.smartcallassistant.activity.DBHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class SyncContactVO implements Serializable {

    public String id = "";
    public String user_id = "";
    public String phone = "";
    public String name = "";
    public String fcmtoken = "";
    private DBHelper dbHelper;

    public SyncContactVO(Context context){
        id = "";
        user_id = "";
        phone = "";
        name = "";
        fcmtoken = "";
        this.dbHelper = new DBHelper(context);
    }

    public JSONObject toJSONObject(){
        JSONObject temp = new JSONObject();
        try {
            temp.put("id", id);
            temp.put("phone", phone);
            temp.put("name", name);
            temp.put("fcmtoken", fcmtoken);
        }catch (JSONException e){
            e.printStackTrace();
            System.out.println("Error in JSON" + e.toString());
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

        SyncContactVO temp = new SyncContactVO(null);

        if(!jsObj.isNull("id"))
            temp.id = jsObj.getString("id");
        if(!jsObj.isNull("phone"))
            temp.phone = jsObj.getString("phone");
        if(!jsObj.isNull("name"))
            temp.name = jsObj.getString("name");
        if(!jsObj.isNull("fcmtoken"))
            temp.fcmtoken = jsObj.getString("fcmtoken");

        return temp;
    }

    public ArrayList<SyncContactVO> getSyncContactVO()
    {
        ArrayList<SyncContactVO> arraytp = new ArrayList<SyncContactVO>();
        String selectQuery = "select * from "+dbHelper.SYNC_CONTACTS_TABLE_NAME;
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);

        int count = 0;
        if(cursor.moveToFirst())
        {
            do{
                SyncContactVO tp = new SyncContactVO(null);
                tp.id = cursor.getString(0);
                tp.name = cursor.getString(1);
                tp.phone = cursor.getString(2);
                arraytp.add(tp);
                count++;
                if(count > 50)
                {
                    break;
                }
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return arraytp;
    }


    public ArrayList<SyncContactVO> getNotifyArraylist(JSONArray jsa) throws JSONException{
        ArrayList<SyncContactVO> pro = new ArrayList<SyncContactVO>();
        SyncContactVO temp;
        JSONObject jso;

        for(int i=0; i<jsa.length(); i++){
            temp = new SyncContactVO(null);
            jso = (JSONObject) jsa.get(i);

            if(!jso.isNull("id"))
                temp.id = jso.getString("id");
            if(!jso.isNull("phone"))
                temp.phone = jso.getString("phone");
            if(!jso.isNull("name"))
                temp.name = jso.getString("name");
            else
                temp.name = "User";

            pro.add(temp);
        }
        return pro;
    }
}
