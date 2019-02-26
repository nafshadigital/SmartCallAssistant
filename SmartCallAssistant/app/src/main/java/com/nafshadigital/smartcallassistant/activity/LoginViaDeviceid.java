package com.nafshadigital.smartcallassistant.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.nafshadigital.smartcallassistant.R;
import com.nafshadigital.smartcallassistant.helpers.MyToast;
import com.nafshadigital.smartcallassistant.vo.UsersVO;
import com.nafshadigital.smartcallassistant.webservice.MyRestAPI;
import org.json.JSONObject;

import static android.Manifest.permission.READ_PHONE_STATE;

public class LoginViaDeviceid extends AppCompatActivity {
String device_id,android_id;
public static final int RequestPermissionCode = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_via_deviceid);

        android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        manifestPermission();

    }

    public void manifestPermission(){
        if (checkPermission()) {
            getDeviceId();
            // Toast.makeText(Dashboard.this, "All Permissions Granted Successfully", Toast.LENGTH_LONG).show();
        } else {
            requestPermission();
        }
    }
    private void requestPermission() {
        ActivityCompat.requestPermissions(LoginViaDeviceid.this, new String[]
                {
                        Manifest.permission.READ_PHONE_STATE
                }, RequestPermissionCode);

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RequestPermissionCode:
                if (grantResults.length > 0) {
                    boolean PhonestatePermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (PhonestatePermission) {

                    getDeviceId();
                        //     Toast.makeText(Dashboard.this, "Permission has been Successfully Granted", Toast.LENGTH_LONG).show();
                    } else {
                        //     Toast.makeText(Dashboard.this, "Permission has been Denied", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    public boolean checkPermission() {
        int PermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), READ_PHONE_STATE);

        return PermissionResult == PackageManager.PERMISSION_GRANTED ;
    }

    public void getDeviceId(){
        try{
            TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
            device_id = telephonyManager.getDeviceId();
        }catch (SecurityException e){

        }
        MyToast.show(this,"Permission has been Successfully Granted");

        signup();

     //   MyToast.show(this,"Device_id="+device_id + "Android_id="+android_id);
        System.out.println("Device_id="+device_id + "Android_id="+android_id);
    }

    public void signup(){
        UsersVO usersVO = new UsersVO();
        usersVO.android_id = android_id;
        usersVO.device_id = device_id;


        String res = MyRestAPI.PostCall("signUp", usersVO.toJSONObject());

        try {
            JSONObject jsonObject = new JSONObject(res);
            String status = jsonObject.getString("status");
            String id = jsonObject.getString("user_id");
            String message = jsonObject.getString("message");

            if(status.equals("1")){
                Intent in = new Intent(this,MyProfile.class);
                in.putExtra("userid",id);
                startActivity(in);
                MyToast.show(this,message);

                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("userID", id);
                editor.commit();

            }else{

            }
        }catch (Exception e){

        }
    }

}
