package com.nafshadigital.smartcallassistant.activity;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.nafshadigital.smartcallassistant.R;
import com.nafshadigital.smartcallassistant.helpers.MyToast;
import com.nafshadigital.smartcallassistant.vo.ActivityVO;
import com.nafshadigital.smartcallassistant.vo.SettingsVO;
import com.nafshadigital.smartcallassistant.webservice.ActivityService;

import java.sql.Time;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Remainder extends AppCompatActivity {
    EditText txtfromtime,txttotime;
    Button btnsave;
    TextView tvremaintitle,tvremdate;
    ActivityVO selectedactivityVO;
    EditText txtrplymsg;
    String formattedDate;
    private static final int SYSTEM_ALERT_WINDOW_PERMISSION = 2084;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remainder);

        tvremaintitle = (TextView) findViewById(R.id.tvremaintitle);
        tvremdate = (TextView) findViewById(R.id.remdate);
        txtfromtime = (EditText) findViewById(R.id.txtremfromdate);
        txttotime = (EditText) findViewById(R.id.txtremtodate);
        txtrplymsg = (EditText) findViewById(R.id.rplymsgact);

        btnsave = (Button) findViewById(R.id.btnremsave);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null && bundle.get("actvityVO") != null){
            selectedactivityVO = (ActivityVO) bundle.get("actvityVO");

            tvremaintitle.setText(selectedactivityVO.activity_name);
            if(selectedactivityVO.activity_message == null || selectedactivityVO.activity_message.length() == 0)
                txtrplymsg.setText(selectedactivityVO.activity_name+". Notification to the Caller <time>.");
            else
                txtrplymsg.setText(selectedactivityVO.activity_message);
        }

        String input = txtrplymsg.getText().toString();

        if(input.contains("'"))
        {
            selectedactivityVO.activity_message = input.replace("'", "`");

        }


        Calendar mcurrentTime = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
        formattedDate = df.format(mcurrentTime.getTime());
    //    MyToast.show(this,formattedDate);

        tvremdate.setText(formattedDate);

        datesettings();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !android.provider.Settings.canDrawOverlays(this)) {
            askPermission();
            MyToast.show(this,"Permission granted press back button");
        }
    }

    public boolean onOptionsItemSelected(android.view.MenuItem item){
        finish();
        return true;

    }

    public void remfromdate(View view)
    {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);

        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(Remainder.this,  android.R.style.Theme_Holo_Light_Dialog,new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
               txtfromtime.setText(DBHelper.getTime(selectedHour, selectedMinute));
            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();


    }

    public void remtodate(View view)
    {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;

        mTimePicker = new TimePickerDialog(Remainder.this,android.R.style.Theme_Holo_Light_Dialog, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                txttotime.setText(DBHelper.getTime(selectedHour, selectedMinute));
            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();

    }

    public boolean isValidate(){

        if(txtfromtime.getText().length() == 0) {
            MyToast.show(getApplicationContext(), "Please select start time.");
            return false;
        }
        if(txttotime.getText().length() == 0) {
            MyToast.show(getApplicationContext(), "Please select end time.");
            return false;
        }
        if(txtrplymsg.getText().length() == 0) {
            MyToast.show(getApplicationContext(), "Please enter reply text.");
            return false;
        }

        Date fromDateVal = DBHelper.strinToDate(formattedDate + "" + txtfromtime.getText().toString());
        Date toDateVal = DBHelper.strinToDate(formattedDate + "" + txttotime.getText().toString());
        fromDateVal.setSeconds(new Date().getSeconds());

        //MyToast.show(getApplicationContext(), fromDateVal.toString() + " => " + (new Date()).toString() + " = " + fromDateVal.compareTo(new Date()) );

        if (fromDateVal.compareTo(new Date()) < 0 && !fromDateVal.toString().equals(new Date().toString())) {
            MyToast.show(getApplicationContext(), "Please enter correct start time.");
            return false;
        }

        if (toDateVal.compareTo(fromDateVal) < 0) {
            MyToast.show(getApplicationContext(), "Please enter correct end time.");
            return false;
        }


        return true;
    }
    public void remdatesave(View view)
    {
        if(isValidate()) {

                            //   MyToast.show(this,txtfromtime.getText().toString());
                            SettingsVO settingsVO = new SettingsVO(getApplicationContext());
                            settingsVO.fromtime = formattedDate + "" + txtfromtime.getText().toString();
                            settingsVO.totime = formattedDate + "" + txttotime.getText().toString();
                            settingsVO.activity_id = selectedactivityVO.id;
                            settingsVO.activity_name = selectedactivityVO.activity_name;
                            long res = settingsVO.updateSettings();

                            ActivityService activity = new ActivityService(getApplicationContext());
                            ActivityVO activityVO = new ActivityVO();
                            activityVO.id = selectedactivityVO.id;
                            activityVO.activity_message = txtrplymsg.getText().toString();
                            int ress = activity.updateActivitymsg(activityVO);

                            MyToast.show(this,selectedactivityVO.activity_name + " from " + txtfromtime.getText().toString() + " To " + txttotime.getText().toString());
                            new ActivityService(getApplicationContext()).updateIsactive(selectedactivityVO.id);

                            finish();

                    }
    }

    public void datesettings(){
        SettingsVO settingsVO = new SettingsVO(getApplicationContext());
        settingsVO.getSettings();


        try {
            Date fromdate =null;
            fromdate= DBHelper.strinToDate(settingsVO.fromtime);
            String fromtime=DBHelper.strinToTime(settingsVO.fromtime);
            txtfromtime.setText(fromtime);

            Date todate =null;
            todate=DBHelper.strinToDate(settingsVO.totime);

            String totime=DBHelper.strinToTime(settingsVO.totime);
            txttotime.setText(totime);
        } catch (Exception ex) {
            Log.v("Exception", ex.getLocalizedMessage());
        }

    }

    private void askPermission() {
        Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, SYSTEM_ALERT_WINDOW_PERMISSION);
    }
}
