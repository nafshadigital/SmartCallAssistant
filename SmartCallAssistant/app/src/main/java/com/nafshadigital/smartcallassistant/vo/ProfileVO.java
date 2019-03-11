package com.nafshadigital.smartcallassistant.vo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nafshadigital.smartcallassistant.activity.DBHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Malaris on 3/31/2018.
 */

public class ProfileVO {
    public String id = "";
    public String name = "";
    public String email = "";
    public String mobile_no = "";
    public String code = "";
    private DBHelper dbHelper;

    public ProfileVO(Context context) {
        id = "";
        name = "";
        email = "";
        mobile_no = "";
        code = "";
        this.dbHelper = new DBHelper(context);
    }


    public int updateProfile() {
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("contact_name", name);
        cv.put("email", email);
        cv.put("mobile_no", mobile_no);
        cv.put("code", code);

        String selectQuery = " select * from " + dbHelper.PROFILE_TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        int res = 0;
        if (cursor.getCount() == 0) {
            res = (int) db.insert(dbHelper.PROFILE_TABLE_NAME, null, cv);
        } else {
            res = db.update(dbHelper.PROFILE_TABLE_NAME, cv, null, null);
        }
        cursor.close();
        db.close();
        return res;
    }
}




