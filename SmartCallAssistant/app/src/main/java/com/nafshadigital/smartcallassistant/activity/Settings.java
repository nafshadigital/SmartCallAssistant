package com.nafshadigital.smartcallassistant.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.nafshadigital.smartcallassistant.R;
import com.nafshadigital.smartcallassistant.helpers.MyToast;
import com.nafshadigital.smartcallassistant.vo.RemainderVO;
import com.nafshadigital.smartcallassistant.vo.SettingsVO;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Settings extends AppCompatActivity {

    EditText txtfromtime,txttotime;
    Switch switchsendsms,switchfavmute,switchvibratemute;
    TextView tvactivityname;
    LinearLayout linearschedule,linearfavorites;
    String fromtime,totime;
    SettingsVO settingsVO;
    String formattedDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        settingsVO = new SettingsVO(getApplicationContext());

        tvactivityname = findViewById(R.id.tvactivityname);
        txtfromtime = findViewById(R.id.txtfromsch);
        txttotime = findViewById(R.id.txttosch);
        switchsendsms = findViewById(R.id.switchsmsmute);
        switchfavmute = findViewById(R.id.switchfavmute);
        switchvibratemute = findViewById(R.id.switchvibratemute);
        linearschedule = findViewById(R.id.linearschedule);
        linearfavorites = findViewById(R.id.linearfavorites);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Calendar mcurrentTime = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
        formattedDate = df.format(mcurrentTime.getTime());
     //   MyToast.show(this,formattedDate);

        loadSettigs();



        switchsendsms.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton cb, boolean on){
                if (switchsendsms.isChecked()) {
                    settingsVO.smsmute = "yes";
                    long res = settingsVO.addsettings();
                }
                else{
                    settingsVO.smsmute = "No";
                    long res = settingsVO.addsettings();
                }

            }
        });

        switchfavmute.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(switchfavmute.isChecked()) {
                    settingsVO.favmute = "yes";
                    long res = settingsVO.addsettings();
                }
                else{
                    settingsVO.favmute = "No";
                    long res = settingsVO.addsettings();
                }
            }
        });

        switchvibratemute.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(switchvibratemute.isChecked()) {
                    settingsVO.vibmute = "yes";
                    long res = settingsVO.addsettings();
                }
                else{
                    settingsVO.vibmute = "No";
                    long res = settingsVO.addsettings();
                }
            }
        });

        linearfavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Settings.this,FavouriteActivity.class);
                startActivity(in);
            }
        });


    }

    public boolean onOptionsItemSelected(android.view.MenuItem item){
        finish();
        return true;

    }

    public void remainseting(View view)
    {
        Intent in = new Intent(this,Remainder.class);
      //  in.putExtra("actvityVO",settingsVO);
        startActivity(in);
    }
  /*  public void txtfromschtime(View view)
    {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(Settings.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                txtfromtime.setText(DBHelper.getTime(selectedHour, selectedMinute));
                settingsVO.fromtime = formattedDate + "" +txtfromtime.getText().toString();
                long res = settingsVO.addsettings();
             //   MyToast.show(getApplicationContext(),""+res);

            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();

    }

    public void txttoschtime(View view)
    {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(Settings.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                txttotime.setText(DBHelper.getTime(selectedHour, selectedMinute));
                settingsVO.totime = formattedDate + "" +txttotime.getText().toString();
                long res = settingsVO.addsettings();
              //  MyToast.show(getApplicationContext(),""+res);

            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }  */

    public void saveschedule(View view){


        int timecal = Integer.parseInt(txtfromtime.getText().toString()) - Integer.parseInt(txttotime.getText().toString());
        RemainderVO remainderVO = new RemainderVO(getApplicationContext());
        remainderVO.st_date = txtfromtime.getText().toString();
        remainderVO.end_date = txttotime.getText().toString();
        remainderVO.type = "CM";
        remainderVO.value = ""+timecal;
        long res = remainderVO.insertRemainder();
        MyToast.show(this,""+res);

        SettingsVO settingsVO = new SettingsVO(getApplicationContext());


        long resu = settingsVO.addsettings();
    }

    public void loadSettigs() {
        settingsVO = new SettingsVO(getApplicationContext());
        settingsVO.getSettings();

        if(settingsVO.activity_name.equals(""))
        {
            tvactivityname.setText("No Activity");
            linearschedule.setVisibility(View.GONE);
        }else{
            tvactivityname.setText(settingsVO.activity_name);
            linearschedule.setVisibility(View.VISIBLE);
        }


        if(settingsVO.smsmute.equals("yes"))
        {
            switchsendsms.setChecked(true);
        }else{
            switchsendsms.setChecked(false);
        }

        if(settingsVO.favmute.equals("yes"))
        {
            switchfavmute.setChecked(true);
        }else{
            switchfavmute.setChecked(false);
        }

        if(settingsVO.vibmute.equals("yes"))
        {
            switchvibratemute.setChecked(true);
        }else{
            switchvibratemute.setChecked(false);
        }


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

       // txttotime.setText(settingsVO.totime);
    }

}
