package com.nafshadigital.smartcallassistant.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.nafshadigital.smartcallassistant.R;
import com.nafshadigital.smartcallassistant.adapter.ListAdapterViewContacts;
import com.nafshadigital.smartcallassistant.vo.ContactVO;

import java.util.ArrayList;

public class Contacts extends AppCompatActivity {

    ListView listView ;
    ArrayList<String> StoreContacts ;
    Cursor cursor ;
    String name, phonenumber ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        listView = findViewById(R.id.listviewcontacts);


        StoreContacts = new ArrayList<>();


                GetContactsIntoArrayList();

                ListAdapterViewContacts adapter = new ListAdapterViewContacts(Contacts.this,StoreContacts);
                listView.setAdapter(adapter);
    }

    public boolean onOptionsItemSelected(android.view.MenuItem item){
        finish();
        return true;

    }

    public void GetContactsIntoArrayList(){

        cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {

                name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

                phonenumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                ContactVO contactVO = new ContactVO(getApplicationContext());
                contactVO.number = phonenumber;
                contactVO.name = name;
                long result = contactVO.addContacts();

                StoreContacts.add(name + " "  + ":" + " " + phonenumber);
            }
        }

        if (cursor != null) {
            cursor.close();
        }

    }



}
