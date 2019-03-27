package com.nafshadigital.smartcallassistant.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.nafshadigital.smartcallassistant.R;
import com.nafshadigital.smartcallassistant.helpers.MyToast;
import com.nafshadigital.smartcallassistant.vo.ActivityVO;
import com.nafshadigital.smartcallassistant.vo.RemainderVO;
import com.nafshadigital.smartcallassistant.webservice.ActivityService;

import java.util.ArrayList;

public class CallmebackActivity extends AppCompatActivity {

    ActivityVO selectedactivityVO;
    TextView txtactname;
    EditText txtrplymsg;
    Button btnsave;
    ListView listremainder;
    ArrayList<RemainderVO> RemAL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_callmeback);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        txtactname = findViewById(R.id.tvactivname);
        txtrplymsg = findViewById(R.id.rplymsgact);
        btnsave = findViewById(R.id.btnsaveremain);
        listremainder = findViewById(R.id.listremainder);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null && bundle.get("actvityVO") != null){
            selectedactivityVO = (ActivityVO) bundle.get("actvityVO");

            txtactname.setText(selectedactivityVO.activity_name);
            txtrplymsg.setText("Sorry, I'm in "+selectedactivityVO.activity_name+" now. will call you later. Thanks");

        }

     //   listremainderdet();
    }

    public boolean onOptionsItemSelected(android.view.MenuItem item){
        finish();
        return true;

    }

    public void saveremain(View view)
    {
        ActivityService activity = new ActivityService(getApplicationContext());
        ActivityVO activityVO = new ActivityVO();
        activityVO.id = selectedactivityVO.id;
        activityVO.activity_message = txtrplymsg.getText().toString();
        int ress = activity.updateActivitymsg(activityVO);
        MyToast.show(this,selectedactivityVO.id);
        MyToast.show(this, ""+ress +"Success: Added to reminder.");


         ActivityService activityService = new ActivityService(getApplicationContext());
         int result = activityService.updateIsactive(selectedactivityVO.id);

            Intent in = new Intent(this, Dashboard.class);
            startActivity(in);

        }

     /*   public void listremainderdet(){
            RemainderVO remain = new RemainderVO(getApplicationContext());
            RemAL = remain.getRemainder();
            ListAdapterViewRemainder adapter = new ListAdapterViewRemainder(this,RemAL);
            listremainder.setAdapter(adapter);
        } */

}

