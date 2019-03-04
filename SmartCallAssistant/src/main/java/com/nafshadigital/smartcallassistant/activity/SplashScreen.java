package com.nafshadigital.smartcallassistant.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nafshadigital.smartcallassistant.R;
import com.nafshadigital.smartcallassistant.helpers.MyToast;

public class SplashScreen extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
                String userid =  sharedPreferences.getString("userID", "");
               // MyToast.show(SplashScreen.this,userid);
                System.out.println("userIDSplash="+userid);
                if(userid == "")
                {

                    Intent in = new Intent(SplashScreen.this,EnterMobilenumber.class);
                    startActivity(in);

                   /* Intent in = new Intent(SplashScreen.this,LoginViaDeviceid.class);
                    startActivity(in); */
                }else {
                    Intent dash = new Intent(SplashScreen.this,Dashboard.class);
                    startActivity(dash);
                }
                finish();
            }
        },SPLASH_TIME_OUT);


    }
}
