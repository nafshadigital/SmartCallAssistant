package com.nafshadigital.smartcallassistant.vo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nafshadigital.smartcallassistant.activity.DBHelper;

import java.util.ArrayList;

/**
 * Created by Malaris on 3/31/2018.
 */

public class RemainderVO {
    public String id = "";
    public String st_date = "";
    public String end_date = "";
    public String message = "";
    public String type = "";
    public String value = "";
    private DBHelper dbHelper;

    public  RemainderVO(Context context){
        id = "";
        st_date = "";
        end_date = "";
        message = "";
        type = "";
        value = "";
        this.dbHelper = new DBHelper(context);
    }


    public long insertRemainder() {
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("st_date", st_date);
        cv.put("end_date", end_date);
        cv.put("message", message);
        cv.put("type", type);
        cv.put("value", value);

      return db.insert(dbHelper.REMAINDER_TABLE_NAME, null, cv);

    }

    public ArrayList<RemainderVO> getRemainder()
    {
        ArrayList<RemainderVO> arraytp = new ArrayList<RemainderVO>();
        String selectQuery = "select * from "+dbHelper.REMAINDER_TABLE_NAME;
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if(cursor.moveToFirst())
        {
            do{
                RemainderVO tp = new RemainderVO(null);
                tp.id = cursor.getString(0);
                tp.st_date = cursor.getString(1);
                tp.message = cursor.getString(2);
                arraytp.add(tp);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return arraytp;
    }

    public void deleteRemainder() {

        SQLiteDatabase database = this.dbHelper.getWritableDatabase();
        database.execSQL("DELETE FROM " + dbHelper.REMAINDER_TABLE_NAME );
        database.close();
    }
}




