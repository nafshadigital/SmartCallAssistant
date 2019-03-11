package com.nafshadigital.smartcallassistant.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.util.Log;

import com.nafshadigital.smartcallassistant.activity.DBHelper;
import com.nafshadigital.smartcallassistant.vo.NotificationVO;
import com.nafshadigital.smartcallassistant.vo.SyncContactVO;
import com.nafshadigital.smartcallassistant.webservice.MyRestAPI;

import java.util.Timer;
import java.util.TimerTask;

public class SyncContactsService extends Service {
    public int counter = 0;
    public int recordsProcessed = 0;
    private DBHelper dbHelper;
    private Boolean stopThread = false;
    private int syncSeconds = 25; // Every n 25 seconds the contacts will be synched

    public SyncContactsService(Context applicationContext) {
        super();
    }

    public SyncContactsService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        startTimer();
        printContactList();
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stoptimertask();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void printContactList(int startFromIndex) {
        printContactList();
    }

    private void printContactList() {
        this.dbHelper = new DBHelper(this);
        int recordCounter = 0;
        int processRecords = recordsProcessed + 10;
        int contactsCount = 0;

        Cursor cursor = getContacts();
        contactsCount = cursor.getCount();

        System.out.println("Total Contacts in the Phone =" + contactsCount);
        if (contactsCount <= processRecords) {
            stopThread = true;
            Log.i("Quit", "Thread finished its job" + contactsCount);
            this.stoptimertask();
        }
        SharedPreferences sharedPreference = getApplicationContext().getSharedPreferences("LoginDetails",Context.MODE_PRIVATE);
        String userid = sharedPreference.getString("userID","");

        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {

            String contactId =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));

            Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);


            while (phones.moveToNext()) {
                String number = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));

                {
                    recordCounter++;
                    if (recordCounter >= recordsProcessed && recordCounter <= processRecords) {
                        System.out.println("Inside Loop Process records reached ----> " + recordsProcessed);
                        System.out.println("Inside Loop Process records reached ----> " + processRecords);

                        Log.d(recordCounter + ":" + "Display_Name", cursor.getString(cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME)) + " Number" + number);
                        Log.d(recordCounter + ":" + "Phone", number);

                        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
                        this.dbHelper.addSyncContacts(db, recordCounter, name, number);

                        number = number.replaceAll("\\D", "").trim();
                        if (number.substring(0, 1).equals("00")) {
                            number = number.substring(2, number.length());
                        }
                        if (number.length() > 9) {

                            SyncContactVO contacts = new SyncContactVO();
                            contacts.user_id = userid;
                            contacts.contact_name = name;
                            contacts.contact_number = number;

                            String savedId = MyRestAPI.PostCall("savecontact",contacts.toJSONObject());
                            System.out.println("Save ID Response" + savedId);

                        }
                        db.close();
                    }

                }
                if (recordCounter >= processRecords || stopThread == true) {
                    System.out.println("Process records reached ----> " + processRecords);
                    break;
                }
            }
            phones.close();
            cursor.moveToNext();

            if (recordCounter >= processRecords || stopThread == true) {
                recordsProcessed = processRecords;
                System.out.println("Process records reached ----> " + recordsProcessed);
                break;
            }

        }
        cursor.close();

        if (stopThread) {
            this.stoptimertask();
        }
        System.out.println("Total Records in the Phone " + this.getActivityCount(3));
    }

    private Cursor getContacts() {
        // Run query
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        String[] projection = new String[]{
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME
        };
        String[] selectionArgs = null;
        String sortOrder = ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";

        return getContentResolver().query(uri, projection, null, selectionArgs, sortOrder);
    }

    public int getActivityCount(int is_updated) {
        String countQuery = "";
        if (is_updated > 1) // 0 means notupdate l means updated more than means both records
        {
            countQuery = "SELECT  * FROM " + dbHelper.SYNC_CONTACTS_TABLE_NAME;
        } else {
            countQuery = "SELECT  * FROM " + dbHelper.SYNC_CONTACTS_TABLE_NAME + " where is_updated=" + is_updated;
        }
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }

    private Timer timer;
    private TimerTask timerTask;

    public void startTimer() {
        //set a new Timer
        timer = new Timer();
        //initialize the TimerTask's job
        initializeTimerTask();
        //schedule the timer, to wake up every 25 seconds
        timer.schedule(timerTask, 1000*syncSeconds, 1000);

    }
    /**
     * it sets the timer to print the counter every x seconds
     */
    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                printContactList(recordsProcessed);
            }
        };
    }


    public void stoptimertask() {
        Log.v("Stop", "Timer Stopped");
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }


}