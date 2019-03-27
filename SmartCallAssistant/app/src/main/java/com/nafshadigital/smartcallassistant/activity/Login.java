package com.nafshadigital.smartcallassistant.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.nafshadigital.smartcallassistant.R;

public class Login extends AppCompatActivity {
Button btnlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnlogin = findViewById(R.id.btnlogin);



    }

    public  void login(View view){
        Intent i = new Intent(Login.this,Dashboard.class);
        startActivity(i);
    }
}
