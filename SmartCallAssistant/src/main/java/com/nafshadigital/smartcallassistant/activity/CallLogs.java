package com.nafshadigital.smartcallassistant.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.provider.ContactsContract;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.nafshadigital.smartcallassistant.R;
import com.nafshadigital.smartcallassistant.adapter.ListAdapterViewCalllog;
import com.nafshadigital.smartcallassistant.vo.CallLogVO;

import java.util.ArrayList;

public class CallLogs extends AppCompatActivity {

    ListView listcalllog;
    public TextView tvcalltype,tvempcalllog;
    ArrayList<CallLogVO> calllogAL;
    LinearLayout linearCalllog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_logs);
        setTitle("Call Logs");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);

        listcalllog = (ListView) findViewById(R.id.listviewcalllog);
        tvcalltype = (TextView) findViewById(R.id.tvcalltype);
        tvempcalllog = (TextView) findViewById(R.id.tvempcalllog);
        linearCalllog = (LinearLayout) findViewById(R.id.linearcalllog);

        tvcalltype.setText("Incoming Calls");
        displayIncomingcall();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
       // MenuInflater menuInflater = getMenuInflater();
       // menuInflater.inflate(R.menu.calllogswitch,menu);

        getMenuInflater().inflate(R.menu.calllogswitch, menu);
        MenuItem item = (MenuItem) menu.findItem(R.id.switchId);
        item.setActionView(R.layout.calllogswitchlayout);
        final Switch switchcall = item
                .getActionView().findViewById(R.id.switchcalllogmenu);

        switchcall.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton cb, boolean on){
                if (switchcall.isChecked()) {
                    tvcalltype.setText("Outgoing Calls");
                    linearCalllog.setBackgroundColor(Color.parseColor("#f3dcba"));
                    displayOutgoingcall();
                }
                else{
                    tvcalltype.setText("Incoming Calls");
                    displayIncomingcall();
                    linearCalllog.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.activity_gradient_bk));
                }
            }
        });
        return true;
    }

    public boolean onOptionsItemSelected(android.view.MenuItem item){
        if(item.getItemId()==R.id.switchId)
        {

        }else {
            finish();
        }
        return true;
    }
 /*   public boolean onOptionsItemSelected(android.view.MenuItem item){
        finish();
        return true;
    } */

    public void displayIncomingcall()
    {
        CallLogVO log = new CallLogVO(getApplicationContext());
        calllogAL = log.getincomingCalllog();
        ListAdapterViewCalllog adapter = new ListAdapterViewCalllog(this,calllogAL);
        listcalllog.setAdapter(adapter);

        tvempcalllog.setVisibility(View.GONE);
        if(calllogAL.size() == 0){
            tvempcalllog.setVisibility(View.VISIBLE);
        }
    }

    public void displayOutgoingcall()
    {
        CallLogVO log = new CallLogVO(getApplicationContext());
        calllogAL = log.getoutgoingCalllog();
        ListAdapterViewCalllog adapter = new ListAdapterViewCalllog(this,calllogAL);
        listcalllog.setAdapter(adapter);

        tvempcalllog.setVisibility(View.GONE);
        if(calllogAL.size() == 0){
            tvempcalllog.setVisibility(View.VISIBLE);
        }
    }
}
