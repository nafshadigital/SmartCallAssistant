package com.nafshadigital.smartcallassistant.activity;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.nafshadigital.smartcallassistant.webservice.IWebServiceDeclaration;

import java.sql.Time;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Malaris on 10/05/2018.
 */

public class DBHelper extends SQLiteOpenHelper implements IWebServiceDeclaration {
    public String ACTIVITY_TABLE_NAME;
    public String PROFILE_TABLE_NAME;
    public String CONTACTS_TABLE_NAME;
    public String CALLLOG_TABLE_NAME;
    public String REMAINDER_TABLE_NAME;
    public String SETTINGS_TABLE_NAME;
    public String FAVORITE_TABLE_NAME;
    public String SYNC_CONTACTS_TABLE_NAME;
    public String ACTIVITY_NAME = "activity_name";
    public String IS_DEFAULT = "is_default";
    Context context;
    public DBHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        this.context = context;
        ACTIVITY_TABLE_NAME = "tbl_activity";
        PROFILE_TABLE_NAME = "tbl_profile";
        CONTACTS_TABLE_NAME = "tbl_contacts";
        CALLLOG_TABLE_NAME = "tbl_calllogs";
        REMAINDER_TABLE_NAME = "tbl_remainder";
        SETTINGS_TABLE_NAME = "tbl_settings";
        FAVORITE_TABLE_NAME = "tbl_favorites";
        SYNC_CONTACTS_TABLE_NAME = "tbl_sync_contacts";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tbl_activity = "create table " + ACTIVITY_TABLE_NAME + "(id integer primary key AUTOINCREMENT,activity_name text,activity_message text,is_default integer,is_active integer)";
        db.execSQL(tbl_activity);

        this.addActivity(db, "Driving","1");
        this.addActivity(db, "In a meeting","1");
        this.addActivity(db, "Taking rest","1");
        this.addActivity(db, "Prayer time","1");
       // this.addActivity(db, "Type activity","1");

        String tbl_profile = "create table " + PROFILE_TABLE_NAME + "(id integer primary key AUTOINCREMENT,name text,email text,mobile_no integer,code integer)";
        db.execSQL(tbl_profile);

        String tbl_contacts = "create table " + CONTACTS_TABLE_NAME + "(id integer primary key AUTOINCREMENT,number integer,name text,email text)";
        db.execSQL(tbl_contacts);

        String tbl_calllogs = "create table " + CALLLOG_TABLE_NAME + "(id integer primary key AUTOINCREMENT,number text,dt_log text,incoming integer,outgoing integer)";
        db.execSQL(tbl_calllogs);

        this.addCalllog(db, 984677567,"2018-08-18",0,1);
        this.addCalllog(db, 954564568,"2018-03-12",1,0);
        this.addCalllog(db, 985454542,"2018-04-30",0,1);
        this.addCalllog(db, 984845455,"2018-05-20",1,0);

        String tbl_remainder = "create table " + REMAINDER_TABLE_NAME + "(id integer primary key AUTOINCREMENT,st_date text,end_date text,message text,type text,value text)";
        db.execSQL(tbl_remainder);

        String tbl_settings = "create table " + SETTINGS_TABLE_NAME + "(id integer primary key AUTOINCREMENT,activity_id integer,activity_name text,fromtime integer,totime integer,smsmute text,favmute text,vibmute text,ismobilemute integer)";
        db.execSQL(tbl_settings);

        //dummy entry in the settings
        //SettingsVO settings = new SettingsVO(this.context);
        this.addSettings(db);

        String tbl_favorites = "create table " + FAVORITE_TABLE_NAME + "(id integer primary key AUTOINCREMENT,name text,phnnumber text)";
        db.execSQL(tbl_favorites);

        String tbl_sync_contacts = "create table " + SYNC_CONTACTS_TABLE_NAME + "(id integer ,phone text primary key,name text,is_installed integer)";
        db.execSQL(tbl_sync_contacts);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop table if exists "+ ACTIVITY_TABLE_NAME);
        db.execSQL("drop table if exists "+ PROFILE_TABLE_NAME);
        db.execSQL("drop table if exists "+ CONTACTS_TABLE_NAME);
        db.execSQL("drop table if exists "+ CALLLOG_TABLE_NAME);
        db.execSQL("drop table if exists "+ REMAINDER_TABLE_NAME);
        db.execSQL("drop table if exists "+ SETTINGS_TABLE_NAME);
        db.execSQL("drop table if exists "+ FAVORITE_TABLE_NAME);
        db.execSQL("drop table if exists "+ SYNC_CONTACTS_TABLE_NAME);

        onCreate(db);
    }

    public static String getDateTime(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return dateFormat.format(date);
    }

    public static Date strinToDate(String dateStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
        try {
            Date d = dateFormat.parse(dateStr);
            return d;
        }catch (Exception e) {
            return null;
        }
    }

    public static String strinToTime(String dateStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
        try {
            Date d = dateFormat.parse(dateStr);
            return  getTime(d.getHours(), d.getMinutes() );
        }catch (Exception e) {
            return null;
        }
    }

    public static String getTime(int hr,int min) {
        Time tme = new Time(hr,min,0);//seconds by default set to zero
        Format formatter;
        formatter = new SimpleDateFormat("h:mm a");
        return formatter.format(tme);
    }

    public void addActivity(SQLiteDatabase db, String activity_name,String is_default) {
        ContentValues values = new ContentValues();
        values.put(ACTIVITY_NAME, activity_name);
        values.put(IS_DEFAULT,"1");
        db.insert(ACTIVITY_TABLE_NAME, null, values);
    }

    public void addSettings(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put("activity_id", "");
        cv.put("activity_name", "");
        cv.put("fromtime", "");
        cv.put("totime", "");
        cv.put("smsmute", "yes");
        cv.put("favmute", "yes");
        cv.put("vibmute", "yes");
        cv.put("ismobilemute","0");
        db.insert(SETTINGS_TABLE_NAME, null, cv);
    }

    public void addCalllog(SQLiteDatabase db, Integer number,String dt_log,Integer incoming,Integer outgoing) {
        ContentValues values = new ContentValues();
        values.put("number", number);
        values.put("dt_log",dt_log);
        values.put("incoming",incoming);
        values.put("outgoing",outgoing);
        db.insert(CALLLOG_TABLE_NAME, null, values);
    }

    public void deleteSyncContacts(SQLiteDatabase db)
    {
        db.execSQL("delete  from " + SYNC_CONTACTS_TABLE_NAME);
    }
    public void addSyncContacts(SQLiteDatabase db,int id, String name,String phone) {
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("phone", phone);
        values.put("name",name);
        values.put("is_installed","0");
        if(id ==1) {
            db.execSQL("delete  from " + SYNC_CONTACTS_TABLE_NAME);
        }
        try {
            db.insertWithOnConflict(SYNC_CONTACTS_TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_ABORT);
        }
        catch(Exception e)
        {
            Log.v(" Contact Name exist -->",name);
        }
    }

    public void setMemberByPhone(SQLiteDatabase db, String phone) {
        ContentValues values = new ContentValues();
        values.put("phone", phone);
        values.put("is_installed","1");
        db.update(SYNC_CONTACTS_TABLE_NAME, values, "phone="+phone, null);
    }
}

