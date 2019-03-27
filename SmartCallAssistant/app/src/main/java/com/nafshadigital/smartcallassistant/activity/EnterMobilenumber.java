package com.nafshadigital.smartcallassistant.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.nafshadigital.smartcallassistant.R;
import com.nafshadigital.smartcallassistant.adapter.ListAdapterViewCountry;
import com.nafshadigital.smartcallassistant.helpers.MyToast;
import com.nafshadigital.smartcallassistant.network.ApiInterface;
import com.nafshadigital.smartcallassistant.network.SmartCallAssistantApiClient;
import com.nafshadigital.smartcallassistant.vo.CountryVO;
import com.nafshadigital.smartcallassistant.vo.EmptyRequestVO;
import com.nafshadigital.smartcallassistant.vo.SignUpResponse;
import com.nafshadigital.smartcallassistant.vo.UsersVO;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnterMobilenumber extends AppCompatActivity {
    Spinner spincountry;
    EditText txtcountrycode,txtmobileno;
    Button btnok;
    ArrayList<CountryVO> CountryAL;
    String country_code;
    private static final String TAG = "EnterMobilenumber";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_mobile);

        spincountry = findViewById(R.id.spincountry);
        txtcountrycode = findViewById(R.id.txtcountrycode);
        txtmobileno = findViewById(R.id.txtphnnumberverify);
        btnok = findViewById(R.id.btnokverify);

        // The request code used in ActivityCompat.requestPermissions()
        // and returned in the Activity's onRequestPermissionsResult()
        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {
                android.Manifest.permission.READ_CONTACTS,
                android.Manifest.permission.WRITE_CONTACTS,
                android.Manifest.permission.SYSTEM_ALERT_WINDOW,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_NOTIFICATION_POLICY,
                Manifest.permission.VIBRATE,
                android.Manifest.permission.INTERNET
        };

        if(!hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }


        getcountry();
        spincountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View view, int position, long id) {

              //CountryVO countryVO =  CountryAL.get(position);
                CountryVO countryVO = (CountryVO) spincountry.getSelectedItem();
                country_code = countryVO.country_code;
             //   MyToast.show(getApplicationContext(),""+position + " - " +country_code);

                    txtcountrycode.setText(countryVO.country_code);

            }
            public void onNothingSelected(AdapterView<?> arg0) { }
        });


    }



    public void getcountry() {
        try {
            Log.d(TAG, "getcountry: ");
            Call<List<CountryVO>> responseBodyCall=SmartCallAssistantApiClient.getClient()
                    .create(ApiInterface.class).getCountry(new EmptyRequestVO());
            responseBodyCall.enqueue(new Callback<List<CountryVO>>() {
                @Override
                public void onResponse(Call<List<CountryVO>> call, Response<List<CountryVO>> response) {
                    Log.d(TAG, "onResponse: ");
                    if (response.body() != null && response.body().size()>0) {
                        try {
                            CountryAL = new ArrayList<>(response.body());
                            ListAdapterViewCountry adapter = new ListAdapterViewCountry(EnterMobilenumber.this,R.layout.spinner_item, CountryAL);
                            spincountry.setAdapter(adapter);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
             
                }

                @Override
                public void onFailure(Call<List<CountryVO>> call, Throwable t) {
                    Log.d(TAG, "onFailure: ");
                }
            });
            
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public boolean isValidation(){
        if (txtcountrycode.getText().toString().equals("")) {
            MyToast.show(this, "Enter  CountryCode");
            return false;
        }
        if (txtmobileno.getText().toString().equals("")) {
            MyToast.show(this, "Enter MobileNo");
            return false;
        }
        if(txtmobileno.getText().length() != 9 && txtmobileno.getText().length() != 10)
        {
            MyToast.show(this, "Please enter proper numbers");
            return false;
        }

        return true;
    }


    public void okverify(View view)
    {
        if(isValidation())
        {
            btnok.setEnabled(false);
            String android_id = android.provider.Settings.Secure.getString(getApplicationContext().getContentResolver(),
                    Settings.Secure.ANDROID_ID);

            UsersVO usersVO = new UsersVO();
            usersVO.country_code = txtcountrycode.getText().toString();
            usersVO.mobile = txtmobileno.getText().toString();
            usersVO.android_id = android_id;

            Call<SignUpResponse> call=SmartCallAssistantApiClient.getClient()
                    .create(ApiInterface.class).signUp(usersVO);
            call.enqueue(new Callback<SignUpResponse>() {
                @Override
                public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {

                    try {

                        SignUpResponse signUpResponse=response.body();
                        if(signUpResponse!=null){
                        String status = signUpResponse.getStatus();
                        String id = signUpResponse.getUser_id();
                        String otp = signUpResponse.getOtp();
                        System.out.println("OTP ---" + otp);
                        //   MyToast.show(this, "res=" + status);

                        if (status.equals("1")) {
              /*  String phnnum = txtmobileno.getText().toString();
                    try {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(phnnum, null, otp, null, null);
                        //   Toast.makeText(context, "SMS Sent!", Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        //  Toast.makeText(context, "SMS failed, please try again later!", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    } */
                            Intent in = new Intent(EnterMobilenumber.this, NumberVerify.class);
                            //   MyToast.show(this, usersVO.id);
                            in.putExtra("userVO", id);
                            startActivity(in);
                        }
                        }
                    } catch (Exception e) {
                        btnok.setEnabled(true);
                    }
                }

                @Override
                public void onFailure(Call<SignUpResponse> call, Throwable t) {

                }
            });


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
