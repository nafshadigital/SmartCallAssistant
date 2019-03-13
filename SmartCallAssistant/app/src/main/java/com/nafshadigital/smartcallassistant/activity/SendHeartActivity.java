package com.nafshadigital.smartcallassistant.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nafshadigital.smartcallassistant.R;
import com.nafshadigital.smartcallassistant.adapter.ListAdapterViewFavorites;
import com.nafshadigital.smartcallassistant.adapter.ListAdapterViewSendHeart;
import com.nafshadigital.smartcallassistant.helpers.MyToast;
import com.nafshadigital.smartcallassistant.vo.FavoriteVO;
import com.nafshadigital.smartcallassistant.vo.SyncContactVO;

import java.util.ArrayList;

import static android.Manifest.permission.READ_CONTACTS;

public class SendHeartActivity extends AppCompatActivity {

    ImageView imgaddcon;
    ListView listfavcon;
    TextView txtempfav;
    ArrayList<SyncContactVO> FavAL;
    SyncContactVO favoriteVO;
    public static final int RequestPermissionCode = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendheart);
        setTitle("Send Hearts ♥");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        txtempfav = (TextView) findViewById(R.id.tvempfavo);
        listfavcon = (ListView) findViewById(R.id.listviewfavcont);

        display();

    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        return false;
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

    public void display(){
        SyncContactVO favoriteVO = new SyncContactVO(getApplicationContext());
        FavAL = favoriteVO.getSyncContactVO();
        ListAdapterViewSendHeart adapter = new ListAdapterViewSendHeart(this,FavAL);
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

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Uri contactData = data.getData();
            Cursor cursor = managedQuery(contactData, null, null, null, null);
            cursor.moveToFirst();
            String id = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
            String number = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));


            favoriteVO = new SyncContactVO(getApplicationContext());
            favoriteVO.name = name;
            number = number.replace(" ", "");
            number = number.replace("-", "");

            favoriteVO.phone = number;
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
        ActivityCompat.requestPermissions(SendHeartActivity.this, new String[]
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
