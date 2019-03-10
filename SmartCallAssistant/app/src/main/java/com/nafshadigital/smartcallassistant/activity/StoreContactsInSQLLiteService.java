package com.nafshadigital.smartcallassistant.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nafshadigital.smartcallassistant.R;
import com.nafshadigital.smartcallassistant.adapter.ListAdapterViewFavorites;
import com.nafshadigital.smartcallassistant.helpers.MyToast;
import com.nafshadigital.smartcallassistant.vo.FavoriteVO;

import java.util.ArrayList;

import static android.Manifest.permission.READ_CONTACTS;

//Not used anywhere replaced with SyncContactsService.java file
public class StoreContactsInSQLLiteService extends AppCompatActivity {

    ImageView imgaddcon;
    ListView listfavcon;
    TextView txtempfav;
    ArrayList<FavoriteVO> FavAL;
    FavoriteVO favoriteVO;
    public static final int RequestPermissionCode = 1;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        setTitle("Favourites");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        txtempfav = (TextView) findViewById(R.id.tvempfavo);
        listfavcon = (ListView) findViewById(R.id.listviewfavcont);

        //display();
        this.dbHelper = new DBHelper(this);

        printContactList();
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.addfavourite,menu);
        MenuItem item = menu.findItem(R.id.imgaddfavourite);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==R.id.imgaddfavourite)
        {
            Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
            startActivityForResult(contactPickerIntent, 1001);

            manifestPermission();

        }else {
            finish();
        }
        return true;
    }

    private void printContactList() {
        Cursor cursor = getContacts();
        cursor.moveToFirst();
        int i = 1;
        while (cursor.isAfterLast() == false) {


            String contactId =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));

            Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
            while (phones.moveToNext())
            {
                String number = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                number = number.replaceAll("\\D", "").trim();
                if(number.substring(0,1).equals("00"))
                {
                    number = number.substring(2,number.length());
                }
                if(number != null && number.length() > 9) {

                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
                    Log.d(i + ":" + "Display_Name", cursor.getString(cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME)));
                    Log.d(i + ":" + "Account_Type", cursor.getString(cursor.getColumnIndex(ContactsContract.RawContacts.ACCOUNT_TYPE)));
                    Log.d(i + ":" + "Phone", number);

                    SQLiteDatabase db = this.dbHelper.getWritableDatabase();
                    this.dbHelper.addSyncContacts(db,i,name,number);

                    db.close();

                    i++;
                }
            }
            phones.close();
            cursor.moveToNext();
        }
        cursor.close();

        System.out.println("Total Records in the Phone " + this.getActivityCount(3));
    }

    public int getActivityCount(int is_updated) {
        String countQuery = "";
        if(is_updated > 1) // 0 means notupdate l means updated more than means both records
        {
            countQuery = "SELECT  * FROM " +dbHelper.SYNC_CONTACTS_TABLE_NAME;
        }
        else
        {
            countQuery = "SELECT  * FROM " +dbHelper.SYNC_CONTACTS_TABLE_NAME + " where is_updated="+is_updated;
        }
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }

    private Cursor getContacts()
    {
        // Run query
        Uri uri = ContactsContract.Data.CONTENT_URI;
        String[] projection = new String[] {
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.RawContacts.ACCOUNT_TYPE
        };
        String[] selectionArgs = null;
        String sortOrder = ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";

        return getContentResolver().query(uri, projection, null, selectionArgs, sortOrder);
    }

    public void display(){
        FavoriteVO favoriteVO = new FavoriteVO(getApplicationContext());
        FavAL = favoriteVO.getFavorites();
        ListAdapterViewFavorites adapter = new ListAdapterViewFavorites(this,FavAL);
        listfavcon.setAdapter(adapter);

        txtempfav.setVisibility(View.GONE );
        if(FavAL.size() == 0){
            txtempfav.setVisibility(View.VISIBLE);
        }
    }

  /*  public boolean onOptionsItemSelected(android.view.MenuItem item){
        finish();
        return true;

    } */


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Uri contactData = data.getData();
            Cursor cursor = managedQuery(contactData, null, null, null, null);
            cursor.moveToFirst();
            String id = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
            String number = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

            // Toast.makeText(this, "id=" + id + "Name="+name  + "Number="+number, Toast.LENGTH_LONG).show();


            favoriteVO = new FavoriteVO(getApplicationContext());
            favoriteVO.name = name;
            number = number.replace(" ", "");
            number = number.replace("-", "");

            favoriteVO.phnnumber = number;

            int result = favoriteVO.checkFavorites(number);
            int favcont = favoriteVO.checkFavcount();
            if (favcont < 10) {
                //MyToast.show(this,""+result);
                if (result == 0) {
                    long res = favoriteVO.addfavorites();
                    MyToast.show(this, "SUCCESS");
                    display();
                } else {
                    MyToast.show(this, "Already Exist");
                }

            } else {
                MyToast.show(this, "Favorites limits 10 only");
            }
        }
    }

    public void manifestPermission(){
        if (checkPermission()) {
            // Toast.makeText(Dashboard.this, "All Permissions Granted Successfully", Toast.LENGTH_LONG).show();
        } else {
            requestPermission();
        }
    }
    private void requestPermission() {
        ActivityCompat.requestPermissions(StoreContactsInSQLLiteService.this, new String[]
                {
                        Manifest.permission.READ_CONTACTS
                }, RequestPermissionCode);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RequestPermissionCode:
                if (grantResults.length > 0) {
                    boolean ContactPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (ContactPermission) {
                        //     Toast.makeText(Dashboard.this, "Permission has been Successfully Granted", Toast.LENGTH_LONG).show();
                    } else {
                        //     Toast.makeText(Dashboard.this, "Permission has been Denied", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    public boolean checkPermission() {
        int FirstPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), READ_CONTACTS);
        return FirstPermissionResult == PackageManager.PERMISSION_GRANTED ;
    }
}
