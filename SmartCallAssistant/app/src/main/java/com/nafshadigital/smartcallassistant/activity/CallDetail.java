package com.nafshadigital.smartcallassistant.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.nafshadigital.smartcallassistant.R;
import com.nafshadigital.smartcallassistant.vo.CallLogVO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CallDetail extends AppCompatActivity {
CallLogVO callLogVO;
 String callid;
    TextView txtdate,txttime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_detail);
        setTitle("Call Log Details");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        TextView txtnum = findViewById(R.id.tvphnnumberdet);
        txtdate = findViewById(R.id.tvphncalldatedet);
        txttime = findViewById(R.id.tvphncalltimedet);

        Bundle bundle = getIntent().getExtras();

        if(bundle != null && bundle.get("callLog") != null){

            callid = bundle.getString("callLog");

            CallLogVO callLog = new CallLogVO(getApplicationContext());
            callLogVO = callLog.getCalllogById(callid);
            txtnum.setText(callLogVO.number);
       //     MyToast.show(this,callLogVO.dt_log);


            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
            try {
                Date mDate = format.parse(callLogVO.dt_log);
             //   System.out.println("Date"+mDate);
                SimpleDateFormat dateformat = new SimpleDateFormat("E dd MMM yyyy");
                txtdate.setText(dateformat.format(mDate));

                SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss a");
                txttime.setText(timeFormat.format(mDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }

         //   MyToast.show(this,callid);
        }
    }

    public boolean onOptionsItemSelected(android.view.MenuItem item){
        finish();
        return true;
    }

   /* public void backCalllog(View view){
        finish();
    } */
}
