package com.nafshadigital.smartcallassistant.vo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nafshadigital.smartcallassistant.helpers.DBHelper;

import java.io.Serializable;
import java.util.ArrayList;

public class CallLogVO implements Serializable{
    public String id = "";
    public String number = "";
    public String dt_log = "";
    public String incoming = "";
    public String outgoing = "";
    private DBHelper dbHelper;
Context context;
    public  CallLogVO(Context context){
        id = "";
        number = "";
        dt_log = "";
        incoming = "";
        outgoing = "";

        this.dbHelper = new DBHelper(context);
    }


    public long addCalllogs()
    {
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("number", number);
        cv.put("dt_log", dt_log);
        cv.put("incoming", incoming);
        cv.put("outgoing", outgoing);
        long res = db.insert(dbHelper.CALLLOG_TABLE_NAME, null, cv);
        db.close();
        return  res;

    }

    public  ArrayList<CallLogVO> getincomingCalllog()
    {
        ArrayList<CallLogVO> callllogAL = new ArrayList<CallLogVO>();
       // String select = "SELECT * FROM " + TABLE_USRS + " WHERE " + KEY_NAME + " = ?
        String selectQuery = "select * from " + dbHelper.CALLLOG_TABLE_NAME + " where incoming =1 order by id desc";
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if(cursor.moveToFirst()){
            do{
                CallLogVO log = new CallLogVO(null);
                log.id = cursor.getString(0);
                log.number = cursor.getString(1);
                log.dt_log = cursor.getString(2);
                log.incoming = cursor.getString(3);
                callllogAL.add(log);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return callllogAL;
    }

    public ArrayList<CallLogVO> getoutgoingCalllog()
    {
        ArrayList<CallLogVO> calllogAL = new ArrayList<CallLogVO>();
        String selectQuery = "select * from " + dbHelper.CALLLOG_TABLE_NAME + " where outgoing =1 order by id desc";
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if(cursor.moveToFirst()){
            do{
                CallLogVO log = new CallLogVO(null);
                log.id = cursor.getString(0);
                log.number = cursor.getString(1);
                log.dt_log = cursor.getString(2);
                log.outgoing = cursor.getString(4);
                calllogAL.add(log);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return calllogAL;
    }

    public void deletecall(int id) {

        SQLiteDatabase database = this.dbHelper.getWritableDatabase();
        database.execSQL("DELETE FROM "+ dbHelper.CALLLOG_TABLE_NAME + " WHERE id = "+id+" ");
        database.close();
    }

    public CallLogVO getCalllogById(String id){
        CallLogVO callLogVO = new CallLogVO(context);
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        String selectedQuery = "select * from " + dbHelper.CALLLOG_TABLE_NAME + " where id ='" + id +"'";
        Cursor res = db.rawQuery(selectedQuery,null);
        if(res.moveToFirst()){
            do {
                    callLogVO.number = res.getString(res.getColumnIndex("number"));
                    callLogVO.dt_log = res.getString(res.getColumnIndex("dt_log"));
                }while (res.moveToNext());
                }
                res.close();
                db.close();
                return callLogVO;
            }

}


