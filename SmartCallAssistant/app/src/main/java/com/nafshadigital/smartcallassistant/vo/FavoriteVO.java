package com.nafshadigital.smartcallassistant.vo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nafshadigital.smartcallassistant.activity.DBHelper;

import java.util.ArrayList;

public class FavoriteVO {
    public String id = "";
    public String name = "";
    public String phnnumber = "";
    private DBHelper dbHelper;

    public  FavoriteVO(Context context){
        id = "";
        name = "";
        phnnumber = "";
        this.dbHelper = new DBHelper(context);
    }


    public long addfavorites()
    {
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("name", name);
        cv.put("phnnumber", phnnumber);
        System.out.println("Phone Number to Add --->" + phnnumber + " Contact Name --->" + name);
        long res = db.insert(dbHelper.FAVORITE_TABLE_NAME, null, cv);
        System.out.println("Phone Number to Add --->" + "Result = " + res);
        db.close();
        return res;
    }


    public ArrayList<FavoriteVO> getFavorites()
    {
        ArrayList<FavoriteVO> arraytp = new ArrayList<FavoriteVO>();
        String selectQuery = "select * from "+dbHelper.FAVORITE_TABLE_NAME;
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if(cursor.moveToFirst())
        {
            do{
                FavoriteVO tp = new FavoriteVO(null);
                tp.id = cursor.getString(0);
                tp.name = cursor.getString(1);
                tp.phnnumber = cursor.getString(2);
                arraytp.add(tp);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return arraytp;
    }

    public int checkFavorites(String phnnum){
        try {
            if (phnnum.length() > 8) {
                phnnum = phnnum.substring(phnnum.length() - 8);
            }
        }
        catch(Exception e)
        {
            return 0;
        }
        String selectQuery = "select * from " +dbHelper.FAVORITE_TABLE_NAME + " where phnnumber like '%" +  phnnum +"'";


        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        int res = cursor.getCount();

        System.out.println(selectQuery + " = " + res);
        cursor.close();
        db.close();
        return res;
        }

        public int checkFavcount(){
            String seletedQuery = "select * from " +dbHelper.FAVORITE_TABLE_NAME;
            SQLiteDatabase db = this.dbHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery(seletedQuery,null);
            int res = cursor.getCount();
            cursor.close();
            db.close();
            return res;
        }

    public void deleteFavourite(int id) {

        SQLiteDatabase database = this.dbHelper.getWritableDatabase();
        database.execSQL("DELETE FROM "+ dbHelper.FAVORITE_TABLE_NAME + " WHERE id = "+id+" ");
        database.close();
    }
}




