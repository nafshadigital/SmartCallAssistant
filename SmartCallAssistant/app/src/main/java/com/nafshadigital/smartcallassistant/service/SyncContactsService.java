package com.nafshadigital.smartcallassistant.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.nafshadigital.smartcallassistant.activity.DBHelper;
import com.nafshadigital.smartcallassistant.helpers.NetworkUtils;
import com.nafshadigital.smartcallassistant.helpers.PrefUtils;
import com.nafshadigital.smartcallassistant.network.ApiInterface;
import com.nafshadigital.smartcallassistant.network.SmartCallAssistantApiClient;
import com.nafshadigital.smartcallassistant.vo.AvailableContact;
import com.nafshadigital.smartcallassistant.vo.AvailableContactsResponse;
import com.nafshadigital.smartcallassistant.vo.SaveAllContactVO;
import com.nafshadigital.smartcallassistant.vo.SaveContact;
import com.nafshadigital.smartcallassistant.vo.SaveContactResponse;
import com.nafshadigital.smartcallassistant.vo.SyncContactVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SyncContactsService extends Service {
    public int counter = 0;

    private DBHelper dbHelper;
    private Boolean stopThread = false;
    private int syncSeconds = 60; // Every n 25 seconds the contacts will be synched
    private int minIntervalMilllis = 1000 * 60 * 10; //Every 10 mins
    private static final String TAG = "SyncContactsService";
    public SyncContactsService(Context applicationContext) {
        super();
    }

    public SyncContactsService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        super.onStartCommand(intent, flags, startId);
        startTimer();

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


    private void printContactList() {

        this.dbHelper = new DBHelper(this);

        long currentMillis=System.currentTimeMillis();
        long prevSyncTime= PrefUtils.getLastSyncTime(this);
        long diffMillis=currentMillis-prevSyncTime;
        if(diffMillis<minIntervalMilllis)
            return;
        int recordCounter = 0;

        int contactsCount = 0;

        Cursor cursor = getContacts();
        contactsCount = cursor.getCount();

        System.out.println("Total Contacts in the Phone =" + contactsCount);

        String userid=PrefUtils.getUserId(getApplicationContext());
        SaveAllContactVO saveAllContactVO=new SaveAllContactVO();
        saveAllContactVO.setUser_id(Integer.parseInt(userid));
        List<SaveContact> contactList=new ArrayList<>();
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {

            final String contactId =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));

            Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);


            if (phones != null) {
                while (phones.moveToNext()) {
                    String number = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    final String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));

                    {
                        recordCounter++;



                            Log.d(recordCounter + ":" + "Display_Name", cursor.getString(cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME)) + " Number" + number);
                            Log.d(recordCounter + ":" + "Phone", number);

                            final SQLiteDatabase db = this.dbHelper.getWritableDatabase();
                            this.dbHelper.addSyncContacts(db, recordCounter, name, number);

                            number = number.replaceAll("\\D", "").trim();
                            if (number.substring(0, 1).equals("00")) {
                                number = number.substring(2);
                            }
                            if (number.length() > 9) {

                                SyncContactVO contacts = new SyncContactVO(null);
                                contacts.user_id = userid;
                                contacts.name = name;
                                contacts.phone = number;


                               SaveContact saveContact= new SaveContact();
                                saveContact.setName(name);
                                saveContact.setPhone(number);
                                contactList.add(saveContact);



                            }



                    }

                }
            }


            if (phones != null) {
                phones.close();
            }
            cursor.moveToNext();



        }
        if(contactList.size()>0 && NetworkUtils.isInternetOn(this)){
            PrefUtils.setLastSyncTime(System.currentTimeMillis(),this);
            Log.d(TAG, "printContactList: allcontact hit"+contactList.size());
            saveAllContactVO.setContacts(contactList);
            Call<AvailableContactsResponse> call= SmartCallAssistantApiClient.getClient()
                    .create(ApiInterface.class).saveAllContacts(saveAllContactVO);
            final SQLiteDatabase db = this.dbHelper.getWritableDatabase();
            call.enqueue(new Callback<AvailableContactsResponse>() {
                @Override
                public void onResponse(Call<AvailableContactsResponse> call, Response<AvailableContactsResponse> response) {
                    try {
                        Log.d(TAG, "allcontact onResponse: ");
                        AvailableContactsResponse contactResponse=response.body();
                        if(contactResponse!=null) {
                            List<AvailableContact> availableContactList=contactResponse.getList();
                            if(availableContactList!=null )
                                for(int i=0;i<availableContactList.size();i++){
                                    String phoneNumber=availableContactList.get(i).getPhone();
                                    if(phoneNumber!=null && phoneNumber.length()>0) {
                                        Log.d(TAG, "onResponse allcontact: "+phoneNumber);
                                        dbHelper.setMemberByPhone(db, phoneNumber);
                                    }
                                }
                        }
                        db.close();

                    }catch (Exception e){
                        Log.e(TAG, "onResponse: ",e );
                    }
                }

                @Override
                public void onFailure(Call<AvailableContactsResponse> call, Throwable t) {
                    Log.e(TAG, "allcontact onFailure: ",t );
                    db.close();
                }
            });
        }
        cursor.close();

        if (stopThread) {
            this.stoptimertask();
        }
        System.out.println("Total Records in the Phone " + this.getActivityCount(3));

        // Todo: Purpose to check what is there is no Single contact in the
        // The member. Send Hearts to random member all over the world.
        // Send hearts to unknown One-at-time
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        //this.dbHelper.deleteSyncContacts(db);
        db.close();
        // Todo : Remove this code in production
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

    public synchronized int getActivityCount(int is_updated) {
        String countQuery = "";
        int count=0;
        try {
            if (is_updated > 1) // 0 means notupdate l means updated more than means both records
            {
                countQuery = "SELECT  * FROM " + dbHelper.SYNC_CONTACTS_TABLE_NAME;
            } else {
                countQuery = "SELECT  * FROM " + dbHelper.SYNC_CONTACTS_TABLE_NAME + " where is_updated=" + is_updated;
            }
            SQLiteDatabase db = this.dbHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery(countQuery, null);

             count = cursor.getCount();
            cursor.close();
            db.close();
        }
        catch (Exception e){
            Log.e(TAG, "getActivityCount: ",e );
        }
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

                int PERMISSION_ALL = 1;
                String[] PERMISSIONS = {
                        android.Manifest.permission.READ_CONTACTS,
                        android.Manifest.permission.WRITE_CONTACTS
                };

                if(hasPermissions(SyncContactsService.this, PERMISSIONS)) {

                    printContactList();
                    System.out.println("System started the Sync Process....");
                }
                else
                {
                    System.out.println("System is coming up and not Permission granted yet !");
                }

                printContactList();
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

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }



}