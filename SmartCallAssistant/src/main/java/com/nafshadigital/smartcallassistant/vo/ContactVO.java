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

public class ContactVO {
    public String id = "";
    public String number = "";
    public String name = "";
    public String email = "";
    private DBHelper dbHelper;

    public  ContactVO(Context context){
        id = "";
        number = "";
        name = "";
        email = "";

        this.dbHelper = new DBHelper(context);
    }


    public long addContacts()
    {
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();

            cv.put("number", number);
            cv.put("name", name);
            cv.put("email", email);
            long res = db.insert(dbHelper.CONTACTS_TABLE_NAME, null, cv);
            db.close();
            return res;

    }
}


