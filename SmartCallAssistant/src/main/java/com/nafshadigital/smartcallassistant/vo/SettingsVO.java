package com.nafshadigital.smartcallassistant.vo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.telecom.Call;

import com.nafshadigital.smartcallassistant.activity.DBHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SettingsVO {
    public String id = "";
    public String activity_id = "";
    public String activity_name = "";
    public String fromtime = "";
    public String totime = "";
    public String smsmute = "";
    public String favmute = "";
    public String vibmute = "";
    public String ismobilemute = "0";
    private DBHelper dbHelper;

    public SettingsVO(Context context) {
        id = "";
        activity_id = "";
        activity_name = "";
        fromtime = "";
        totime = "";
        smsmute = "No";
        favmute = "No";
        vibmute = "No";
        ismobilemute = "0";

        this.dbHelper = new DBHelper(context);
    }


    public long addsettings() {
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("activity_id", activity_id);
        cv.put("activity_name", activity_name);
        cv.put("fromtime", fromtime);
        cv.put("totime", totime);
        cv.put("smsmute", smsmute);
        cv.put("favmute", favmute);
        cv.put("vibmute", vibmute);
        cv.put("ismobilemute", "0");

        String selectedQuery = "select * from " + dbHelper.SETTINGS_TABLE_NAME;
        Cursor cursor = db.rawQuery(selectedQuery, null);
        System.out.println("Count=" + cursor.getCount());
        long res = 0;
        if (cursor.getCount() == 0) {
            res = db.insert(dbHelper.SETTINGS_TABLE_NAME, null, cv);
        } else {
            res = db.update(dbHelper.SETTINGS_TABLE_NAME, cv, null, null);
        }
        cursor.close();
        db.close();
        return res;
    }

    public int updateSettings() {
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("activity_id", activity_id);
        cv.put("activity_name", activity_name);
        cv.put("fromtime", fromtime);
        cv.put("totime", totime);
        cv.put("smsmute", "yes");
        cv.put("favmute", "yes");
        cv.put("vibmute", "yes");
        int res = db.update(dbHelper.SETTINGS_TABLE_NAME, cv, null, null);
        db.close();
        return res;
    }

    public void getSettings() {
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + dbHelper.SETTINGS_TABLE_NAME, null);

        if (res.moveToFirst()) {
            do {
                activity_id = res.getString(res.getColumnIndex("activity_id"));
                activity_name = res.getString(res.getColumnIndex("activity_name"));
                fromtime = res.getString(res.getColumnIndex("fromtime"));
                totime = res.getString(res.getColumnIndex("totime"));
                smsmute = res.getString(res.getColumnIndex("smsmute"));
                favmute = res.getString(res.getColumnIndex("favmute"));
                vibmute = res.getString(res.getColumnIndex("vibmute"));
                ismobilemute = res.getString(res.getColumnIndex("ismobilemute"));
                System.out.println("setting obj=" + toString());
            } while (res.moveToNext());
        }
        res.close();
        db.close();
    }

    public int updateIsmobilemute() {
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("ismobilemute", ismobilemute);
        cv.put("fromtime", fromtime);
        cv.put("totime", totime);
        int res = db.update(dbHelper.SETTINGS_TABLE_NAME, cv, null, null);
        db.close();
        return res;
    }

    public void deleteSettings() {

        SQLiteDatabase database = this.dbHelper.getWritableDatabase();
        database.execSQL("DELETE FROM " + dbHelper.SETTINGS_TABLE_NAME);
        database.close();
    }

    public String toString() {
        return "id-" + id + ", activity_id-" + activity_id + ", activity_name-" + activity_name + ", fromtime" + fromtime + ", totime-" + totime + ", smsmute=" + smsmute + ", favmute=" + favmute + ",vibmute=" + vibmute + ",ismobilemute=" +ismobilemute;
    }
}


