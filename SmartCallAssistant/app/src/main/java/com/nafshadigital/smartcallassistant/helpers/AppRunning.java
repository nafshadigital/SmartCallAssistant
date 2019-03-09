package com.nafshadigital.smartcallassistant.helpers;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

import java.util.List;

public class AppRunning extends Application {

    Ringtone ringtone; // SDK 26 and Above
    MediaPlayer mediaPlayer; // SDK 23
    Uri notification = Uri.EMPTY;
    boolean isBGServiceRunning = false;

    public boolean isBGServiceRunning() {
        return isBGServiceRunning;
    }

    public void setBGServiceRunning(boolean BGServiceRunning) {
        isBGServiceRunning = BGServiceRunning;
    }

    public Ringtone getRingtone() {
        return ringtone;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    public void setRingtone(Ringtone ringtone) {
        this.ringtone = ringtone;
    }

    public static boolean isAppRunning(final Context context, final String packageName) {


        final ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            final List<ActivityManager.RunningAppProcessInfo> procInfos = activityManager.getRunningAppProcesses();
            if (procInfos != null)
            {
                for (final ActivityManager.RunningAppProcessInfo processInfo : procInfos) {
                    if (processInfo.processName.equals(packageName)) {
                        return true;
                    }
                }
            }
            return false;
        }


    public static boolean isMyServiceRunning(Context context, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}