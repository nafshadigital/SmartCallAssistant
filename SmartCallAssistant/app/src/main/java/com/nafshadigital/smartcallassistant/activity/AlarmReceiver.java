package com.nafshadigital.smartcallassistant.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.nafshadigital.smartcallassistant.helpers.AppRunning;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent background = new Intent(context, BgPCICallService.class);
        ((AppRunning) context.getApplicationContext()).setBGServiceRunning(true);
        context.startService(background);
    }

}