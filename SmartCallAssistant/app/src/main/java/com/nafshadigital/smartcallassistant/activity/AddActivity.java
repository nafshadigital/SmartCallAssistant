package com.nafshadigital.smartcallassistant.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.nafshadigital.smartcallassistant.R;
import com.nafshadigital.smartcallassistant.helpers.MyToast;
import com.nafshadigital.smartcallassistant.vo.ActivityVO;
import com.nafshadigital.smartcallassistant.webservice.ActivityService;

public class AddActivity extends AppCompatActivity {
    EditText txtaddact;
    Button btnsave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        txtaddact = (EditText) findViewById(R.id.txtaddactivity);
        btnsave = (Button) findViewById(R.id.btnsaveactivity);
    }

    public boolean onOptionsItemSelected(android.view.MenuItem item){
        finish();
        return true;

    }

    public boolean isValidate() {

        if (txtaddact.getText().toString().equals("")) {
            MyToast.show(this, "Enter  Activity");
            return false;
        }
        return true;
    }

    public void saveact(View view) {
        if (isValidate()) {
            ActivityService activityService = new ActivityService(getApplicationContext());
            ActivityVO activityVO = new ActivityVO();
            activityVO.activity_name = txtaddact.getText().toString();

            int countact = activityService.checkActivity();
           // MyToast.show(this,"count="+countact);
            if(countact < 10) {
                long result = activityService.addActivity(activityVO);
              //  MyToast.show(this, result + " - added.");
                MyToast.show(this,"Success");
                Intent in = new Intent(this, Dashboard.class);
                startActivity(in);
            }else {
                MyToast.show(this,"Exceeding Total Activity Limit(10 Nos)");
            }
        }
    }

}
