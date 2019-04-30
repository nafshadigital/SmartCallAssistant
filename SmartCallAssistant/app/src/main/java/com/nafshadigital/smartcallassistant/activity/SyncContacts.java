package com.nafshadigital.smartcallassistant.activity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.nafshadigital.smartcallassistant.R;
import com.nafshadigital.smartcallassistant.service.SyncContactsService;


public class SyncContacts extends AppCompatActivity {

    private Context context;
    Intent syncContactsIntent;
    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync_contacts);
        this.context = this;
        lottieAnimationView = findViewById(R.id.animation_view);

        startContactsSyncService();
    }

    private void startContactsSyncService() {
        SyncContactsService syncContactsService = new SyncContactsService(this.context);

        syncContactsIntent = new Intent(this.context, syncContactsService.getClass());
        startService(syncContactsIntent);

        if (!isMyServiceRunning(SyncContactsService.class)) {
            animate();
            startService(syncContactsIntent);
        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i ("isMyServiceRunning?", true+"");
                return true;
            }
        }
        Log.i ("isMyServiceRunning?", false+"");
        return false;
    }
    public void animate() {

        if (android.os.Build.VERSION.SDK_INT <= 27) {
            if (lottieAnimationView.isAnimating()) {
                lottieAnimationView.cancelAnimation();
                //button.setText(getString(R.string.play));
            } else {
                lottieAnimationView.playAnimation();
                //button.setText(getString(R.string.pause));
            }
        }
    }

}
