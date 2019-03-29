package com.nafshadigital.smartcallassistant.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.nafshadigital.smartcallassistant.vo.SignUpResponse;

import retrofit2.Callback;

public class PrefUtils {
public static String getUserId(Context context){
    if(context!=null) {
        SharedPreferences sharedPreference = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        String userid = sharedPreference.getString("userID", "");
            if(userid!=null)
                return userid;
    }
    return "";
    }

    public static void setUserId(String id, Context context) {
    if(context!=null) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userID", id);
        editor.apply();
    }
    }


    public static void setLastSyncTime(long millis, Context context) {
        if(context!=null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("contactSync", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putLong("syncTime", millis);
            editor.apply();
        }
    }


    public static long getLastSyncTime(Context context){
        if(context!=null) {
            SharedPreferences sharedPreference = context.getSharedPreferences("contactSync", Context.MODE_PRIVATE);

            return sharedPreference.getLong("syncTime", 0L);
        }
        return 0L;
    }
}
