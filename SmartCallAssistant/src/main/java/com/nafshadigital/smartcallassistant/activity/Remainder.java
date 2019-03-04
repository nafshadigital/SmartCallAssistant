package com.nafshadigital.smartcallassistant.activity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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
import java.util.GregorianCalendar;

public class Remainder extends AppCompatActivity {
    static EditText txtfromtime,txttotime;
    Button btnsave;
    TextView tvremaintitle,tvremdate;
    ActivityVO selectedactivityVO;
    EditText txtrplymsg;
    String formattedFromDate;
    String formattedToDate;
    private static final int SYSTEM_ALERT_WINDOW_PERMISSION = 2084;
    public static final int RequestPermissionCode = 1;
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
                txtrplymsg.setText(selectedactivityVO.activity_name+". Please call me after <time>.");
            else
                txtrplymsg.setText(selectedactivityVO.activity_message);
        }

        Calendar mcurrentTime = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
        formattedFromDate = df.format(mcurrentTime.getTime());
        formattedToDate = df.format(mcurrentTime.getTime());
    //    MyToast.show(this,formattedFromDate);

        SimpleDateFormat dfDisplay = new SimpleDateFormat("dd-MM-yyyy");
        tvremdate.setText(df.format(mcurrentTime.getTime()));

        datesettings();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !android.provider.Settings.canDrawOverlays(this)) {
            askPermission();
            MyToast.show(this,"Permission granted press back button");
        }

        requestDoNotDisturbPermission();
    }

    public boolean onOptionsItemSelected(android.view.MenuItem item){
        finish();
        return true;
    }


    public void remfromdate(View view)
    {

        /*
        final View dialogView = View.inflate(this, R.layout.date_time_picker, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        dialogView.findViewById(R.id.date_time_set).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.date_picker);
                TimePicker timePicker = (TimePicker) dialogView.findViewById(R.id.time_picker);

                Calendar calendar = new GregorianCalendar(datePicker.getYear(),
                        datePicker.getMonth(),
                        datePicker.getDayOfMonth(),
                        timePicker.getCurrentHour(),
                        timePicker.getCurrentMinute());

                long time = calendar.getTimeInMillis();
                alertDialog.dismiss();

                txtfromtime.setText(datePicker.getDayOfMonth() + "/" + datePicker.getMonth() + "/" + datePicker.getYear() + " " + DBHelper.getTime(timePicker.getCurrentHour(), timePicker.getCurrentMinute()));
            }});
        alertDialog.setView(dialogView);
        alertDialog.show();

        /* */
       // showTruitonDatePickerDialog(view);
       // showTruitonTimePickerDialog(view);
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

        Date fromDateVal = DBHelper.strinToDate(formattedFromDate + "" + txtfromtime.getText().toString());
        Date toDateVal = DBHelper.strinToDate(formattedToDate + "" + txttotime.getText().toString());
        if(txtfromtime.getText().toString().toLowerCase().contains("pm") && txttotime.getText().toString().toLowerCase().contains("am")) {
            Calendar mcurrentTimeTo = Calendar.getInstance();
            mcurrentTimeTo.add(Calendar.DATE, 1);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
            formattedToDate = df.format(mcurrentTimeTo.getTime());
            toDateVal = DBHelper.strinToDate(formattedToDate  + "" + txttotime.getText().toString());
        }
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
                            settingsVO.fromtime = formattedFromDate + "" + txtfromtime.getText().toString();
                            settingsVO.totime = formattedToDate + "" + txttotime.getText().toString();
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

    private void requestDoNotDisturbPermission() {

            NotificationManager notificationManager =   (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                    && !notificationManager.isNotificationPolicyAccessGranted()) {

                Intent intent = new Intent(
                        android.provider.Settings
                                .ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);

                startActivity(intent);
            }

    }

    public void showTruitonDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            txtfromtime.setText(day + "/" + (month + 1) + "/" + year);
        }
    }

    public void showTruitonTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public static class TimePickerFragment extends DialogFragment implements
            TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
            txtfromtime.setText(txtfromtime.getText() + " -" + hourOfDay + ":" + minute);
        }
    }

}
