package com.nafshadigital.smartcallassistant.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nafshadigital.smartcallassistant.R;
import com.nafshadigital.smartcallassistant.interfaces.TransparentWindowEvent;
import com.nafshadigital.smartcallassistant.vo.RemainderVO;
import com.nafshadigital.smartcallassistant.vo.SettingsVO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NotifyActivity extends FrameLayout {

    TextView txtduration,tvfromtime,tvtotime,tvactname,tvdatenot;
    TextView txtduration1,txtdurcompat,tvdurationmute2compat;
    Button btnoff,btncancel;
    ImageView imgnottrans;
    AudioManager am;
    ArrayList<RemainderVO> RemAL;
    LinearLayout linearnotify,linearcompat;
    String fromtime,totime;
    private Thread backgroundThread;
    public boolean isCompact = false;
    Context context;
    Intent intent;
    final boolean keepRunning1 = true;

    public NotifyActivity(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.activity_notify, this);

    }

    public void init(){
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),"fonts/alarm-clock.otf");
        txtduration = findViewById(R.id.tvdurationmute);
        txtduration1 = findViewById(R.id.tvdurationmute1);
        txtdurcompat = findViewById(R.id.tvdurationmutecompat);
        tvdurationmute2compat = findViewById(R.id.tvdurationmute2compat);
        imgnottrans = findViewById(R.id.imgarrowtrans);

        linearnotify = findViewById(R.id.linearnotify);
        linearcompat = findViewById(R.id.linearnotifcompat);


        if(isCompact == true){
            linearcompat.setVisibility(VISIBLE);
            linearnotify.setVisibility(GONE);
        }else {
            linearcompat.setVisibility(GONE);
            linearnotify.setVisibility(VISIBLE);
        }

        linearcompat.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    getContext().stopService(new Intent(getContext(), NotifyTransparent.class));
                    getContext().startService(new Intent(getContext(), NotifyTransparent.class));
                } else if (Settings.canDrawOverlays(getContext())) {
                    getContext().stopService(new Intent(getContext(), NotifyTransparent.class));
                    getContext().startService(new Intent(getContext(), NotifyTransparent.class));
                }
            }
        });

        txtduration.setTypeface(tf);
        txtdurcompat.setTypeface(tf);
        txtduration1.setTypeface(tf);
        tvdurationmute2compat.setTypeface(tf);

        tvfromtime = findViewById(R.id.fromtimeact);
        tvtotime = findViewById(R.id.totimeact);
        tvactname = findViewById(R.id.tvnotifactname);
        tvdatenot = findViewById(R.id.tvdateactnoti);

        btnoff = findViewById(R.id.btnoffmute);
     //   btncancel = (Button) findViewById(R.id.btncancelnotif);
        am = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);

        this.backgroundThread=new Thread(runnable);


        this.backgroundThread.start();

        btnoff.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);

                SettingsVO settingsVO = new SettingsVO(getContext());
                settingsVO.ismobilemute = "0";
                settingsVO.fromtime = "";
                settingsVO.totime = "";
                settingsVO.updateIsmobilemute();

                System.out.println("BGSERVICE update 0: "+settingsVO.ismobilemute);

                if(transWinEvent != null) {
                    transWinEvent.onCancelActivityHandler();
                }
            }
        });


    }
    TransparentWindowEvent transWinEvent;
    public void SetOnCancelActivityHandlerListener(TransparentWindowEvent listener)
    {
        transWinEvent=listener;
    }

    private  final Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        public void run() {
            TASK();
            handler.postDelayed(this, 1000);
        }
    };

    public void TASK()
    {
        SettingsVO settingsVO = new SettingsVO(getContext());
        settingsVO.getSettings();

        fromtime = settingsVO.fromtime;
        totime = settingsVO.totime;

        tvactname.setText(settingsVO.activity_name);

        if(settingsVO.ismobilemute.equals("0")){
            this.setVisibility(GONE);
        }else{
            this.setVisibility(VISIBLE);
        }

        final Date fromdate = DBHelper.strinToDate(fromtime);
        final Date todate = DBHelper.strinToDate(totime);

        if(settingsVO.fromtime.equals("") && settingsVO.totime.equals("")){

        }else{
            String ftime = DBHelper.getTime(fromdate.getHours(),fromdate.getMinutes());
            String ttime = DBHelper.getTime(todate.getHours(),todate.getMinutes());

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
            tvdatenot.setText(df.format(fromdate.getTime()));

            tvfromtime.setText(ftime);
            tvtotime.setText(ttime);
        }


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
                    txtdurcompat.setText(diff);
                    System.out.println("Notify Activity: " + diff + " " + mills);
                }else{
                    String diff = m + ":" + s; // updated value every1 second
                    txtduration.setText(diff);
                    txtdurcompat.setText(diff);
                }

            }
        }catch (Exception e)
        {

        }
    }

}
