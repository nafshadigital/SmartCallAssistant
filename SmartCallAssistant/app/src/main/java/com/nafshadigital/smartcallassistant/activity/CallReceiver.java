package com.nafshadigital.smartcallassistant.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.nafshadigital.smartcallassistant.helpers.AppRunning;
import com.nafshadigital.smartcallassistant.network.ApiInterface;
import com.nafshadigital.smartcallassistant.network.SmartCallAssistantApiClient;
import com.nafshadigital.smartcallassistant.vo.ActivityVO;
import com.nafshadigital.smartcallassistant.vo.CallLogVO;
import com.nafshadigital.smartcallassistant.vo.FavoriteVO;
import com.nafshadigital.smartcallassistant.vo.NotificationVO;
import com.nafshadigital.smartcallassistant.vo.RemainderVO;
import com.nafshadigital.smartcallassistant.vo.SendNotificationResponse;
import com.nafshadigital.smartcallassistant.vo.SettingsVO;
import com.nafshadigital.smartcallassistant.webservice.ActivityService;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallReceiver extends BroadcastReceiver {


    Uri notification = Uri.EMPTY;
    Ringtone ringtone;
    MediaPlayer mediaPlayer;
    private static final String TAG = "CallReceiver";

    public static int laststate = TelephonyManager.CALL_STATE_IDLE;
    public static String savedNumber = "";
    public static Date callStartTime;
    public static boolean isIncoming;
    ArrayList<RemainderVO> RemAL;
    String message,activity_id;
    SettingsVO settingsVO;
    public static String userId;
    Intent intent;

    @Override
    public void onReceive(Context context , Intent intent){
        System.out.println("Receiver Start");
        this.intent = intent;

        notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);


        if (((AppRunning) context.getApplicationContext()).getRingtone() == null) {
            this.ringtone = RingtoneManager.getRingtone(context, notification);
            ((AppRunning) context.getApplicationContext()).setRingtone(this.ringtone);
        } else {
            this.ringtone = ((AppRunning) context.getApplicationContext()).getRingtone();
        }


        if(intent.getAction().equals("android.intent.action.NEW_OUTGOING_CALL")){
            savedNumber = intent.getExtras().getString("android.intent.extra.PHONE_NUMBER");
        }
        else{
            String stateStr = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            String number = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
            int state = 0;
            if(stateStr.equals(TelephonyManager.EXTRA_STATE_IDLE)){
                state = TelephonyManager.CALL_STATE_IDLE;
            }
            else if(stateStr.equals((TelephonyManager.EXTRA_STATE_OFFHOOK))){
                state = TelephonyManager.CALL_STATE_OFFHOOK;
            }
            else if(stateStr.equals(TelephonyManager.EXTRA_STATE_RINGING)){
                state = TelephonyManager.CALL_STATE_RINGING;
            }
            onCallStateChanged(context, state, number);
        }


        if(!((AppRunning) context.getApplicationContext()).isBGServiceRunning()) {
            Intent background = new Intent(context, BgPCICallService.class);
            context.startService(background);
            ((AppRunning) context.getApplicationContext()).setBGServiceRunning(true);
        }
    }

    public void onCallStateChanged(Context context, int state, String number){

        if(laststate == state){
                this.ringtone.stop();
            return;
        }



        switch (state){
            case  TelephonyManager.CALL_STATE_RINGING:
                System.out.println("case  TelephonyManager.CALL_STATE_RINGING");
                savedNumber = number;

                settingsVO = new SettingsVO(context);
                settingsVO.getSettings();

                FavoriteVO favoriteVO = new FavoriteVO(context);
                String phoneNo = savedNumber;
                phoneNo = phoneNo.replace(" ", "");
                phoneNo = phoneNo.replace("-", "");

                int res = favoriteVO.checkFavorites(phoneNo);

                if(settingsVO.favmute.equals("yes") && res > 0) {

                    AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                    am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);

                    this.ringtone.play();
                    // Toast.makeText(context, "Ringing RINGER_MODE_NORMAL" + savedNumber + " Call time " + callStartTime +" Date " + new Date() , Toast.LENGTH_LONG).show();
                }else{
                    if(settingsVO.ismobilemute.equals("1")){
                        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                        try {
                            // Get the getITelephony() method
                            Class<?> classTelephony = Class.forName(telephonyManager.getClass().getName());
                            Method method = classTelephony.getDeclaredMethod("getITelephony");
                            // Disable access check
                            method.setAccessible(true);
                            // Invoke getITelephony() to get the ITelephony interface
                            Object telephonyInterface = method.invoke(telephonyManager);
                            // Get the endCall method from ITelephony
                            Class<?> telephonyInterfaceClass =Class.forName(telephonyInterface.getClass().getName());
                            Method methodEndCall = telephonyInterfaceClass.getDeclaredMethod("endCall");
                            // Invoke endCall()
                            methodEndCall.invoke(telephonyInterface);
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println("Error = " + e.toString());
                        }

                        activity_id =settingsVO.activity_id ;
                        System.out.println("ActivityId="+activity_id);

                        ActivityService activityService = new ActivityService(context);
                        ActivityVO activityVO = new ActivityVO();
                        activityVO = activityService.getActivityById(activity_id);
                        String sms = activityVO.activity_message;
                        //  MyToast.show(context,activityVO.activity_message);
                        System.out.println("sms=" +sms );

                        String totime = settingsVO.totime;
                        final Date todate = DBHelper.strinToDate(totime);

                        Date today = new Date();
                        long mills = todate.getTime() - today.getTime();
                        int hours = (int) (mills/(1000 * 60 * 60));
                        String h= String.format("%02d",hours);
                        int mins = (int) (mills/(1000*60)) % 60;
                        String m= String.format("%02d",mins);
                        String replyTime = "";
                        if(hours > 0) {
                            replyTime = h + " hours";
                        }
                        if(mins > 0) {
                            if(replyTime .length() > 0) {
                                replyTime = replyTime + " and ";
                            }
                            replyTime = replyTime + m + " minutes";
                        }
                        sms = sms.replace("<time>",replyTime);

                        try {

                               /* SmsManager smsManager = SmsManager.getDefault();
                                smsManager.sendTextMessage(phoneNo, null, sms, null, null);
                                Toast.makeText(context, "SMS Sent to "+ phoneNo, Toast.LENGTH_LONG).show(); */

                                SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
                                userId = sharedPreferences.getString("userID","");
                                //  MyToast.show(context,userId);
                                NotificationVO noti = new NotificationVO();
                                noti.phnNum = phoneNo;
                                noti.user_id = userId;
                                noti.message = sms;
                            Call<SendNotificationResponse> call= SmartCallAssistantApiClient.getClient()
                                    .create(ApiInterface.class).sendNotify(noti);
                            call.enqueue(new Callback<SendNotificationResponse>() {
                                @Override
                                public void onResponse(Call<SendNotificationResponse> call, Response<SendNotificationResponse> response) {
                                    Log.d(TAG, "onResponse: success");
                                }

                                @Override
                                public void onFailure(Call<SendNotificationResponse> call, Throwable t) {
                                    Log.e(TAG, "onFailure: " );
                                }
                            });



                        } catch (Exception e) {
                            //  Toast.makeText(context, "SMS failed, please try again later!", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                }
                isIncoming = true;
                callStartTime = new Date();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
                String dateToStr = format.format(callStartTime);
              //  Toast.makeText(context, "Incoming Call Ringing -" + savedNumber,Toast.LENGTH_LONG).show();

                if(isIncoming = true) {
                    CallLogVO callLogVO = new CallLogVO(context);
                    callLogVO.number = savedNumber;
                    callLogVO.dt_log = dateToStr;
                    callLogVO.incoming = "1";
                    callLogVO.outgoing = "0";
                    callLogVO.addCalllogs();
                //    MyToast.show(context, "CallLog =" + res);
                }
                break;

            case TelephonyManager.CALL_STATE_OFFHOOK:
                System.out.println("TelephonyManager.CALL_STATE_OFFHOOK");
                this.ringtone.stop();
                if(laststate != TelephonyManager.CALL_STATE_RINGING)
                {

                    isIncoming = false;
                    callStartTime = new Date();
                    SimpleDateFormat formatoff = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
                    String dateToStroff = formatoff.format(callStartTime);
                    savedNumber = number;
              //      Toast.makeText(context, "Outgoing Call Started",Toast.LENGTH_LONG).show();

                    if(isIncoming = false) {
                        CallLogVO callLogVO = new CallLogVO(context);
                        callLogVO.number = savedNumber;
                        callLogVO.dt_log = dateToStroff;
                        callLogVO.incoming = "0";
                        callLogVO.outgoing = "1";
                        callLogVO.addCalllogs();
                    //    MyToast.show(context, "CallLog =" + res);
                    }
                }
                break;

            case TelephonyManager.CALL_STATE_IDLE :
                System.out.println("TelephonyManager.CALL_STATE_OFFHOOK");
                this.ringtone.stop();
                if(laststate == TelephonyManager.CALL_STATE_RINGING){
               //     Toast.makeText(context, "Ringing but no pickup" + savedNumber + " Call time " + callStartTime +" Date " + new Date() , Toast.LENGTH_LONG).show();

                    settingsVO = new SettingsVO(context);
                    settingsVO.getSettings();
                    Date today = new Date();
                    favoriteVO = new FavoriteVO(context);
                    phoneNo = savedNumber;
                    phoneNo = phoneNo.replace(" ", "");
                    phoneNo = phoneNo.replace("-", "");

                    res = favoriteVO.checkFavorites(phoneNo);


                    if(settingsVO.favmute.equals("yes") && res > 0) {

                        try {
                            if(settingsVO.fromtime == null || settingsVO.fromtime.equals("") || settingsVO.totime == null || settingsVO.totime.equals("") ) {
                                System.out.println("From and to time not set.");
                                return;
                            }

                            Date fdate = DBHelper.strinToDate(settingsVO.fromtime);
                            Date tdate = DBHelper.strinToDate(settingsVO.totime);

                            if (today.compareTo(fdate) == 1 && tdate.compareTo(today) == 1) {
                                AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                                am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                            }
                        }catch (Exception e) {

                        }
                        //Toast.makeText(context, "Ringing RINGER_MODE_NORMAL" + savedNumber + " Call time " + callStartTime +" Date " + new Date() , Toast.LENGTH_LONG).show();
                    }
                }
                else if(isIncoming){
               //     Toast.makeText(context, "Incoming" + savedNumber + " Call time " + callStartTime , Toast.LENGTH_LONG).show();
                }
                else{
               //     Toast.makeText(context, "Outgoing" + savedNumber + " Call time " + callStartTime +" Date " + new Date() , Toast.LENGTH_LONG).show();
                }
                break;
        }
        laststate = state;
    }

}
