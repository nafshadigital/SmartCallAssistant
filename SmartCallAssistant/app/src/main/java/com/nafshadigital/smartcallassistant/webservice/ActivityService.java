package com.nafshadigital.smartcallassistant.webservice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nafshadigital.smartcallassistant.helpers.DBHelper;
import com.nafshadigital.smartcallassistant.vo.ActivityVO;

import java.util.ArrayList;

public class ActivityService {
    private DBHelper dbHelper;

    public  ActivityService(Context context){

        this.dbHelper = new DBHelper(context);
    }

    public long addActivity(ActivityVO activityVO)
    {
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();

        String selectQuery = "select * from " + dbHelper.ACTIVITY_TABLE_NAME + " WHERE activity_name= '" + activityVO.activity_name+"' ";

        Cursor cursor = db.rawQuery(selectQuery,null);
        long res = 0;
        if(cursor.getCount() > 0) {
            res = -1;
        }else {
            cv.put("activity_name", activityVO.activity_name);
            cv.put("activity_message ",activityVO.activity_message);
            cv.put("is_default", "0");
            cv.put("is_active", activityVO.is_active);
            res = db.insert(dbHelper.ACTIVITY_TABLE_NAME, null, cv);
        }
        db.close();
        return res;
    }

    public int checkActivity(){
        String selectQuery = "select * from " +dbHelper.ACTIVITY_TABLE_NAME;
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        int res = cursor.getCount();
        cursor.close();
        db.close();
        return res;
    }

    public int updateActivitymsg(ActivityVO activityVO ){
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("activity_message", activityVO.activity_message);
        int res = db.update(dbHelper.ACTIVITY_TABLE_NAME,cv,"id="+activityVO.id,null);
        db.close();
        return res;
    }


    public ArrayList<ActivityVO> getAllActivity()
    {
        ArrayList<ActivityVO> arraytp = new ArrayList<ActivityVO>();
        String selectQuery = "select * from tbl_activity";
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if(cursor.moveToFirst())
        {
            do{
                ActivityVO tp = new ActivityVO();
                tp.id = cursor.getString(0);
                tp.activity_name = cursor.getString(1);
                tp.activity_message = cursor.getString(2);
                tp.is_default = cursor.getString(3);
                tp.is_active = cursor.getString(4);
                arraytp.add(tp);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return arraytp;
    }

    public int updateIsactive(String id){
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        db.execSQL("UPDATE "+dbHelper.ACTIVITY_TABLE_NAME + " SET is_active='0'");
        ContentValues cv = new ContentValues();
        cv.put("is_active","1");
        int res = db.update(dbHelper.ACTIVITY_TABLE_NAME,cv,"id="+id,null);
        db.close();
        return res;
    }

    public int getActivityCount() {
        String countQuery = "SELECT  * FROM " +dbHelper.ACTIVITY_TABLE_NAME;
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }

    public void deleteActivity(String id) {

        SQLiteDatabase database = this.dbHelper.getWritableDatabase();
        database.execSQL("DELETE FROM " + dbHelper.ACTIVITY_TABLE_NAME + " where id=" +id);
        database.close();
    }

    public ActivityVO getActivityById(String id) {
        ActivityVO tp = new ActivityVO();
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        String Selectedquery = "select * from " + dbHelper.ACTIVITY_TABLE_NAME + " where id ='" + id +"'";
        Cursor res =  db.rawQuery( Selectedquery, null);
        System.out.println("Selectedquery="+Selectedquery);
        if (res.moveToFirst()){
            do{
               // tp.activity_message = res.getString(2);
                tp.activity_message = res.getString(res.getColumnIndex("activity_message"));
                System.out.println("activity_message="+tp.activity_message);
            }while(res.moveToNext());
        }
        res.close();
        db.close();
        return tp;
    }
}
