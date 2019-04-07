package com.nafshadigital.smartcallassistant.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.nafshadigital.smartcallassistant.R;
import com.nafshadigital.smartcallassistant.helpers.MyToast;

public class Help extends AppCompatActivity {
    Button btnmail;
    EditText txtmessage,txttomail;
    String subject,message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        setTitle("Help/FeedBack");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        txttomail = findViewById(R.id.txttomailhelp);
        txtmessage = findViewById(R.id.txtmessagehelp);
        btnmail = findViewById(R.id.btnhelpemail);

        //txttomail.setText("nafshadigital@gmail.com");
        txttomail.setText("waheed.rahuman@gmail.com");
        txttomail.setEnabled(false);
        subject = "Feedback from Smart Call Assistant App";
    }

    public boolean onOptionsItemSelected(android.view.MenuItem item){
       finish();
       return true;
    }

    public boolean isVaildation(){
     /*   if(txttomail.getText().toString().equals("")){
            MyToast.show(this,"Enter E-Mail");
            return false;
        } */

        if (!txttomail.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")) {
            MyToast.show(this,"Invalid EmailId");
            return false;
        }

        if(txtmessage.getText().toString().equals("")){
            MyToast.show(this,"Enter Message");
            return false;
        }
        return true;
    }

    public void sendmail(View view){
        message = txtmessage.getText().toString();
        if(isVaildation()){
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", txttomail.getText().toString(), null));
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            intent.putExtra(Intent.EXTRA_TEXT, message);
            startActivity(Intent.createChooser(intent, "Choose an Email client :"));
            finish();
        }
    }
}
