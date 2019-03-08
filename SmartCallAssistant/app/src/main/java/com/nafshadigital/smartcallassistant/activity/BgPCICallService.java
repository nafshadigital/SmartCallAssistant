package com.nafshadigital.smartcallassistant.activity;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.nafshadigital.smartcallassistant.R;
import com.nafshadigital.smartcallassistant.helpers.AppRunning;
import com.nafshadigital.smartcallassistant.helpers.MyToast;
import com.nafshadigital.smartcallassistant.vo.NotificationVO;
import com.nafshadigital.smartcallassistant.vo.RemainderVO;
import com.nafshadigital.smartcallassistant.vo.SettingsVO;
import com.nafshadigital.smartcallassistant.webservice.MyRestAPI;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class BgPCICallService extends Service {

    private  boolean isRunning;
    private Context context;
    private Thread backgroundThread;
    ArrayList<RemainderVO> RemAL;
    String fromtime,totime;
    AudioManager am;
    int showTransAct = 0;
    int maxid = 0;
SettingsVO settingsVO;
    private WindowManager wm;
    private static LinearLayout ly1;
    private WindowManager.LayoutParams params1;
    String isAppRun;
    String userid;

    public static final String MyPREFERENCES = "MyPrefs";
    public static final String notificationKey = "notificationKey";
    SharedPreferences sharedpreferences;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        this.context=this;
        this.isRunning=false;
        this.backgroundThread=new Thread(runnable);
    }
    private final Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        public void run() {
            System.out.println("THE BACKGROUND SERVICE");
            TASK();
            getNotification();
            handler.postDelayed(this, 10000);
        }

    };

    @Override
    public int onStartCommand(Intent intent,int flags, int startId) {
        if(!this.isRunning){
            this.isRunning=true;
            this.backgroundThread.start();
        }
        return START_STICKY;
    }
    @Override
    public void onDestroy() {
        this.isRunning=false;

    }
    public void TASK() {
        // isMobileMute = 1, off 0
        settingsVO = new SettingsVO(getApplicationContext());
        settingsVO.getSettings();

        if(showTransAct == 0) {


            showTransAct = 1;
        }
     //   SharedPreferences sharedPreferences = getBaseContext().getSharedPreferences("bgservice", Context.MODE_PRIVATE);

       // String isMobileMute = sharedPreferences.getString("isMobileMute", settingsVO.ismobilemute);

        String isMobileMute = settingsVO.ismobilemute;

        System.out.println("BGSERVICE: Inside service " + isMobileMute);

            System.out.println("BGSERVICE: Inside isMobileMute = 0.");
             //SettingsVO settingsVO = new SettingsVO(context);
             settingsVO.getSettings();
             fromtime = settingsVO.fromtime;
             totime = settingsVO.totime;
             System.out.println("fromtodatestring =" + fromtime + "" + totime);
             if(fromtime == null || fromtime.equals("") || totime == null || totime.equals("") ) {
                 System.out.println("From and to time not set.");
                 return;
             }

             Date fdate = DBHelper.strinToDate(fromtime);
             Date tdate = DBHelper.strinToDate(totime);

            if(fdate == null || tdate == null ) {
                System.out.println("From and to time not set: null.");
                return;
            }
             Date today = new Date();
             System.out.println("fromtodate =" + fdate + "" + tdate + "" + today);
                am = (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);

                System.out.println("BGSERVICE: Condition: " + today.getTime() + " > " + fdate.getTime() +"  && " + today.getTime() + " < "+  tdate.getTime());


             System.out.println("BGSERVICE: Condition: today.compareTo(fdate) " + today.compareTo(fdate) + " && today.compareTo(tdate) " + tdate.compareTo(today));
                //if (today.getTime() > fdate.getTime() && today.getTime() < tdate.getTime()) {
              if (today.compareTo(fdate) == 1 && tdate.compareTo(today) == 1) {
                  System.out.println("BGSERVICE: INSIDE CONDITION");

                  if (isMobileMute.equals("0")) {
                      am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);

                      Intent intents = new Intent(this, NotifyActivity.class);
                      intents.setAction(Intent.ACTION_MAIN);
                      intents.addCategory(Intent.CATEGORY_LAUNCHER);
                      intents.setAction(Long.toString(System.currentTimeMillis()));
                      intents.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                      PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intents, PendingIntent.FLAG_UPDATE_CURRENT);

                      Notification noti = new Notification.Builder(this)

                              .setSmallIcon(R.mipmap.ic_launcher_round)
                              // .setColor(NotificationCompat.COLOR_DEFAULT)
                              .setContentTitle("Smart Call Assisstant")
                              .setContentText("Your Mobile is Mute Now")
                              .setContentIntent(pendingIntent).build();

                      NotificationManager notificationManager = (NotificationManager) this.getSystemService(this.NOTIFICATION_SERVICE);
                      // hide the notification after its selected
                      noti.flags |= Notification.FLAG_AUTO_CANCEL;
                      noti.defaults |= Notification.DEFAULT_SOUND;
                      notificationManager.notify(0, noti);

                      //SettingsVO settingsVO = new SettingsVO(getApplicationContext());
                      settingsVO.ismobilemute = "1";
                      settingsVO.updateIsmobilemute();
                      System.out.println("BGSERVICE update 1: " + settingsVO.ismobilemute);

                        boolean isRun = isAppIsInBackground(getApplicationContext());

                          if(isRun == false){
                              isAppRun = "1";
                          }
                          else if(isRun == true){
                          isAppRun = "0";
                      }

                      SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("AppRunning", Context.MODE_PRIVATE);
                      SharedPreferences.Editor editor = sharedPref.edit();
                      editor.putString("isRun", isAppRun);
                      editor.commit();

                      SharedPreferences sharedPrefer = context.getSharedPreferences("AppRunning", Context.MODE_PRIVATE);
                      String isRunApp =  sharedPrefer.getString("isRun", "");

                     System.out.println("RunApp="+isRunApp);

                      if(isRunApp == "0"){
                          if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                              startService(new Intent(this, NotifyTransparent.class));
                          } else if (Settings.canDrawOverlays(this)) {
                              startService(new Intent(this, NotifyTransparent.class));
                              }
                      }
                  }
              } else {
                  System.out.println("BGSERVICE: OUTSIDE CONDITION");
                  if (isMobileMute.equals("1")) {
                      //SettingsVO setting = new SettingsVO(getApplicationContext());
                      settingsVO.ismobilemute = "0";
                      settingsVO.updateIsmobilemute();

                      am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);

                      NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                      notificationManager.cancelAll();
                      stopService(new Intent(this, NotifyTransparent.class));
                  }
              }
    }

    private boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }

    public void getNotification(){

        SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES,this.MODE_PRIVATE);
        String min = sharedPreferences.getString(notificationKey,"");
        Integer minid = 0;
        if((min != null) && (!min.isEmpty())){
            minid = Integer.parseInt(min);
        }

        try {
            SharedPreferences sharedPreference = getApplicationContext().getSharedPreferences("LoginDetails",Context.MODE_PRIVATE);
            userid = sharedPreference.getString("userID","");

            NotificationVO noti = new NotificationVO();
            noti.user_id = userid;
            String maxId = MyRestAPI.PostCall("getmaxid",noti.toJSONObject());


            JSONObject jsonObject = new JSONObject(maxId);
            String maxpostId = jsonObject.getString("maxId");
            System.out.println("maxpostid="+maxpostId);

            if(maxpostId.equals("null"))
            {
                maxpostId = null;
            }

            if((maxpostId != null) && (!maxpostId.isEmpty())) {
                maxid = Integer.parseInt(maxpostId);
            }

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(notificationKey,""+ maxid);
            editor.commit();
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        maxid=50; minid=49;

        if(maxid > minid)
        {
            try
            {
                NotificationVO noti = new NotificationVO();
                noti.user_id = userid;
                noti.id = minid.toString();

                String result = MyRestAPI.PostCall("getnotifyafter",noti.toJSONObject());
                System.out.println("notifyafter="+noti.toJSONObject().toString()+result);
                ArrayList<NotificationVO> NotifAL = new NotificationVO().getNotifyArraylist(new JSONArray(new NotificationVO().stringToJSONObject(result).getString("notifications")));
                for(int i=0; i<NotifAL.size(); i++){
                    NotificationVO notif = new NotificationVO();
                    notif = NotifAL.get(i);
                    String content = notif.message;
                    String title = notif.name + " have sent you a message";

                    Intent intents = new Intent(this, NotificationDetail.class);
                    intents.putExtra("notifVO",notif);
                    intents.setAction(Intent.ACTION_MAIN);
                    intents.addCategory(Intent.CATEGORY_LAUNCHER);
                    intents.setAction(Long.toString(System.currentTimeMillis()));
                    intents.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intents, PendingIntent.FLAG_UPDATE_CURRENT);

                    Notification notification = new Notification.Builder(this)

                            .setSmallIcon(R.mipmap.appicon)
                            .setColor(NotificationCompat.COLOR_DEFAULT)
                            .setContentTitle(title)
                            .setContentText(content)
                            .setContentIntent(pendingIntent).build();

                    NotificationManager notificationManager = (NotificationManager) this.getSystemService(this.NOTIFICATION_SERVICE);
                    // hide the notification after its selected
                    notification.flags |= Notification.FLAG_AUTO_CANCEL;
                    notification.defaults |= Notification.DEFAULT_SOUND;
                    notificationManager.notify(0, notification);
                }

            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}


