package com.nafshadigital.smartcallassistant.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.nafshadigital.smartcallassistant.R;
import com.nafshadigital.smartcallassistant.adapter.ListAdapterViewSendHeart;
import com.nafshadigital.smartcallassistant.helpers.NetworkUtils;
import com.nafshadigital.smartcallassistant.helpers.PrefUtils;
import com.nafshadigital.smartcallassistant.network.ApiInterface;
import com.nafshadigital.smartcallassistant.network.SmartCallAssistantApiClient;
import com.nafshadigital.smartcallassistant.vo.AvailableContact;
import com.nafshadigital.smartcallassistant.vo.AvailableContactsResponse;
import com.nafshadigital.smartcallassistant.vo.SendHeartVO;
import com.nafshadigital.smartcallassistant.vo.SyncContactVO;
import com.nafshadigital.smartcallassistant.vo.UsersVO;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.READ_CONTACTS;

public class SendHeartActivity extends AppCompatActivity {

    ImageView imgaddcon;
    ListView listfavcon;
    TextView txtempfav;
    ArrayList<SyncContactVO> FavAL;
    SyncContactVO favoriteVO;
    LottieAnimationView lottieAnimationView;
    public static final int RequestPermissionCode = 1;
    private static final String TAG = "SendHeartActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendheart);
        setTitle("Send Hearts ♥");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        txtempfav = findViewById(R.id.tvempfavo);
        listfavcon = findViewById(R.id.listviewfavcont);
        lottieAnimationView = findViewById(R.id.animation_view);

        if(NetworkUtils.isInternetOn(this)){
            displayFromServer();
        }
        else
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
        setListAdapter();
    }
    private void setListAdapter(){
        ListAdapterViewSendHeart adapter = new ListAdapterViewSendHeart(this,FavAL);
        listfavcon.setAdapter(adapter);

        txtempfav.setVisibility(View.GONE );
        lottieAnimationView.setVisibility(View.GONE);
        if(FavAL.size() == 0){
            txtempfav.setVisibility(View.GONE);
            lottieAnimationView.setVisibility(View.VISIBLE);
        }
    }

    public void displayFromServer(){
      //  SyncContactVO favoriteVO = new SyncContactVO(getApplicationContext());
        //FavAL = favoriteVO.getSyncContactVO();
         UsersVO usersVO=new UsersVO();
         usersVO.user_id= PrefUtils.getUserId(getApplicationContext());
        Call<AvailableContactsResponse> call= SmartCallAssistantApiClient.getClient().create(ApiInterface.class)
                .getRegisteredContacts(usersVO);

        call.enqueue(new Callback<AvailableContactsResponse>() {
            @Override
            public void onResponse(Call<AvailableContactsResponse> call, Response<AvailableContactsResponse> response) {

                    AvailableContactsResponse availableContactsResponse=response.body();

                    if(availableContactsResponse!=null && availableContactsResponse.getList()!=null
                            ){

                        FavAL=new ArrayList<>();
                        try {
                            List<AvailableContact> contactList = availableContactsResponse.getList();
                            if (contactList != null) {
                                for (int i = 0; i < contactList.size(); i++) {
                                    SyncContactVO syncContactVO = new SyncContactVO(SendHeartActivity.this);
                                    syncContactVO.name = contactList.get(i).getName();
                                    syncContactVO.phone = contactList.get(i).getPhone();
                                    syncContactVO.user_id = contactList.get(i).getUser_id();
                                    FavAL.add(syncContactVO);
                                }
                                if (FavAL.size() > 0)
                                    setListAdapter();
                            }
                        }catch (Exception e){
                            Log.e(TAG, "onResponse: ",e );
                        }
                    }
            }

            @Override
            public void onFailure(Call<AvailableContactsResponse> call, Throwable t) {

            }
        });


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

    public void animate(View v) {

        if (android.os.Build.VERSION.SDK_INT <= 27)
        {
            if (lottieAnimationView.isAnimating()) {
                lottieAnimationView.cancelAnimation();
                //button.setText(getString(R.string.play));
            } else {
                lottieAnimationView.playAnimation();
                //button.setText(getString(R.string.pause));
            }
        }
    }

    public void sendRandomHearts(View view)
    {
        String userid = PrefUtils.getUserId(this);

        SendHeartVO users = new SendHeartVO();
        users.sender_id     = userid;               // From User ID
        users.receiver_phone = "";                  // Send to Random Phone Number

        Call<ResponseBody> call = SmartCallAssistantApiClient.getClient()
                .create(ApiInterface.class).sendRandomHeart(users);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ResponseBody responseBody=response.body();
                try {
                    System.out.println("Send Heart Result = " + responseBody.string());
                }
                catch (Exception e){
                    Log.e(TAG, "onResponse: ",e );
                }
                }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });




    }

}
