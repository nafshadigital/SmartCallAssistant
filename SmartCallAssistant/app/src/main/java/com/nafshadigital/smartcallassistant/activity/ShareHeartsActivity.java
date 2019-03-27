package com.nafshadigital.smartcallassistant.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.nafshadigital.smartcallassistant.R;
import com.nafshadigital.smartcallassistant.network.ApiInterface;
import com.nafshadigital.smartcallassistant.network.SmartCallAssistantApiClient;
import com.nafshadigital.smartcallassistant.vo.MyProfileResponse;
import com.nafshadigital.smartcallassistant.vo.UsersVO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShareHeartsActivity extends AppCompatActivity {
    String android_id, device_id;
    LottieAnimationView lottieAnimationView;
    TextView HeartCount;
    private static final String TAG = "ShareHeartsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharehearts);
        setTitle("Share your ♥♥♥");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        try {
            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            device_id = telephonyManager.getDeviceId();
        } catch (SecurityException e) {

        }
        lottieAnimationView = findViewById(R.id.animation_view);
        HeartCount = findViewById(R.id.heart_count);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        String selectedUserID = sharedPreferences.getString("userID", "");

        UsersVO usersVO = new UsersVO();
        usersVO.id = selectedUserID;
        usersVO.android_id = android_id;
        usersVO.device_id = device_id;

        Call<MyProfileResponse> call = SmartCallAssistantApiClient.getClient()
                .create(ApiInterface.class).getProfile(usersVO);

        call.enqueue(new Callback<MyProfileResponse>() {
            @Override
            public void onResponse(Call<MyProfileResponse> call, Response<MyProfileResponse> response) {
                try {

                    MyProfileResponse myProfileResponse = response.body();
                    if (myProfileResponse != null && myProfileResponse.getUserRecord() != null) {
                        if (myProfileResponse.getUserRecord().size() > 0) {

                            String heartCount = myProfileResponse.getUserRecord().get(0).getHeart();
                            HeartCount.setText(heartCount);
                            if (heartCount.length() > 2) {
                                HeartCount.setGravity(Gravity.TOP);
                            }
                        }
                    }
                } catch (Exception e) {
                    Log.e(TAG, "onResponse: ", e);
                }

            }

            @Override
            public void onFailure(Call<MyProfileResponse> call, Throwable t) {

            }
        });

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

    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        finish();
        return true;
    }


}
