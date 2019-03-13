package com.nafshadigital.smartcallassistant.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.airbnb.lottie.LottieAnimationView;
import com.nafshadigital.smartcallassistant.R;
import com.nafshadigital.smartcallassistant.helpers.MyToast;
import com.nafshadigital.smartcallassistant.vo.ProfileVO;
import com.nafshadigital.smartcallassistant.vo.UsersVO;
import com.nafshadigital.smartcallassistant.webservice.MyRestAPI;

import org.json.JSONException;
import org.json.JSONObject;

public class ShareHeartsActivity extends AppCompatActivity {
    EditText txtname,txtemail,txtmobno,txtcode;
    String android_id,device_id,name,email;
    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharehearts);
        setTitle("Share your ♥♥♥");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        try{
            TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
            device_id = telephonyManager.getDeviceId();
        }catch (SecurityException e){

        }
        lottieAnimationView = (LottieAnimationView) findViewById(R.id.animation_view);

    }

    public void animate(View v) {
        if (lottieAnimationView.isAnimating()) {
            lottieAnimationView.cancelAnimation();
            //button.setText(getString(R.string.play));
        } else {
            lottieAnimationView.playAnimation();
            //button.setText(getString(R.string.pause));
        }
    }

    public boolean onOptionsItemSelected(android.view.MenuItem item){
        finish();
        return true;
    }


}
