package com.nafshadigital.smartcallassistant.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.nafshadigital.smartcallassistant.R;
import com.nafshadigital.smartcallassistant.vo.NotificationVO;

public class NotificationDetail extends AppCompatActivity {
NotificationVO notificationVO;
TextView tvdate,tvname,tvmessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_detail);

        tvdate = findViewById(R.id.tvdatenotif);
        tvname = findViewById(R.id.tvnamenotif);
        tvmessage = findViewById(R.id.tvmesgnotif);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        if(bundle !=null && bundle.get("notifVO") != null){
            notificationVO = (NotificationVO) bundle.get("notifVO");

            tvdate.setText(notificationVO.date);
            tvname.setText(notificationVO.name);
            tvmessage.setText(notificationVO.message);
        }
    }

    public boolean onOptionsItemSelected(android.view.MenuItem item){
        finish();
        return true;
    }

    public void backnotifdet(View view){
        Intent in = new Intent(this, Dashboard.class);
        startActivity(in);
    }
}
