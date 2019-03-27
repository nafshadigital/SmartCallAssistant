package com.nafshadigital.smartcallassistant.activity;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.os.Build;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nafshadigital.smartcallassistant.R;
import com.nafshadigital.smartcallassistant.interfaces.TransparentWindowEvent;

public class NotifyTransparent extends Service implements View.OnClickListener {


    private WindowManager mWindowManager;
    private View mFloatingView;
    private View expandedView;
    TextView txtduration;
    Button btnoff,btncancel;
    ImageView imgcancel;
    AudioManager am;
    String fromtime,totime;
    private Thread backgroundThread;
    private  boolean isRunning;
    private NotifyActivity remActivitytrans;

    public NotifyTransparent() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        //this.isRunning=false;
        //this.backgroundThread=new Thread(runnable);
        super.onCreate();

        //getting the widget layout from xml using layout inflater
        mFloatingView = LayoutInflater.from(this).inflate(R.layout.activity_notify_transparent, null);

        Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/alarm-clock.otf");
        //txtduration = (TextView) mFloatingView.findViewById(R.id.tvdurationmutetrans);
       // txtduration.setTypeface(tf);

       // btnoff = (Button) mFloatingView.findViewById(R.id.btnoffmutetrans);
     //   btncancel = (Button) mFloatingView.findViewById(R.id.btncancelnotiftrans);
        imgcancel = mFloatingView.findViewById(R.id.buttonClose);
        am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        WindowManager.LayoutParams params1;
        //setting the layout parameters
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            params1  = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_PHONE,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT);
        }else {
             params1 = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT);
        }

        final WindowManager.LayoutParams params = params1;

        //getting windows services and adding the floating view to it
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mWindowManager.addView(mFloatingView, params);

        expandedView = mFloatingView.findViewById(R.id.layoutExpanded);

        //adding click listener to close button and expanded view
     //   mFloatingView.findViewById(R.id.btnoffmutetrans).setOnClickListener(this);
    //    mFloatingView.findViewById(R.id.btncancelnotiftrans).setOnClickListener(this);
        mFloatingView.findViewById(R.id.buttonClose).setOnClickListener(this);

        //adding an touchlistener to make drag movement of the floating widget
        mFloatingView.findViewById(R.id.layoutExpanded).setOnTouchListener(new View.OnTouchListener() {
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = params.x;
                        initialY = params.y;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return true;

                    case MotionEvent.ACTION_UP:
                        //when the drag is ended switching the state of the widget
                        expandedView.setVisibility(View.VISIBLE);
                        return true;

                    case MotionEvent.ACTION_MOVE:
                        //this code is helping the widget to move around the screen with fingers
                        params.x = initialX + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY + (int) (event.getRawY() - initialTouchY);
                        mWindowManager.updateViewLayout(mFloatingView, params);
                        return true;
                }
                return false;
            }
        });



    }

    /*

    private final Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        public void run() {
            TASK();
            handler.postDelayed(this, 1000);
        }

    };
*/
    @Override
    public int onStartCommand(Intent intent,int flags, int startId) {
        if(!this.isRunning){
            //this.isRunning=true;
           // this.backgroundThread.start();
            remActivitytrans = mFloatingView.findViewById(R.id.remactivitytrans);
            remActivitytrans.SetOnCancelActivityHandlerListener(new TransparentWindowEvent() {
                @Override
                public void onCancelActivityHandler() {
                    stopSelf();
                }
            });
            remActivitytrans.init();
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mFloatingView != null) mWindowManager.removeView(mFloatingView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
         /*   case R.id.layoutExpanded:

                expandedView.setVisibility(View.GONE);
                break; */

            case R.id.buttonClose:
                //closing the widget
                stopSelf();
                break;

           /* case R.id.btnoffmutetrans:

                am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                SettingsVO settingsVO = new SettingsVO(getApplicationContext());
                settingsVO.ismobilemute = "0";
                settingsVO.fromtime = "";
                settingsVO.totime = "";
                settingsVO.updateIsmobilemute();
                System.out.println("BGSERVICE update 0: "+settingsVO.ismobilemute);
                stopSelf();
                break;

            case R.id.btncancelnotiftrans:
                stopSelf();
                break; */
        }
    }
/*
public void TASK()
    {
        SettingsVO settingsVO = new SettingsVO(getApplicationContext());
        settingsVO.getSettings();

        fromtime = settingsVO.fromtime;
        totime = settingsVO.totime;

        final Date fromdate = DBHelper.strinToDate(fromtime);
        final Date todate = DBHelper.strinToDate(totime);

        Date today = new Date();

        System.out.println("fromdate=" + fromdate);
        try {
            if(fromdate != null && !fromdate .equals("")) {
                long mills = today.getTime() - fromdate.getTime();
                int hours = (int) (mills / (1000 * 60 * 60));
                String h = String.format("%02d", hours);
                int mins = (int) (mills / (1000 * 60)) % 60;
                String m = String.format("%02d", mins);
                long seconds = mills / 1000 % 60;
                String s = String.format("%02d", seconds);

                if(hours > 0){
                    String diff = h + ":" + m + ":" + s; // updated value every1 second
                    txtduration.setText(diff);
                    System.out.println("Notify Activity: " + diff + " " + mills);
                }else{
                    String diff = m + ":" + s; // updated value every1 second
                    txtduration.setText(diff);
                }

            }
        }catch (Exception e)
        {

        }
    }*/

}
