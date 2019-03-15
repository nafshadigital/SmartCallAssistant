package com.nafshadigital.smartcallassistant.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.nafshadigital.smartcallassistant.R;
import com.nafshadigital.smartcallassistant.helpers.MyToast;
import com.nafshadigital.smartcallassistant.vo.UsersVO;
import com.nafshadigital.smartcallassistant.webservice.MyRestAPI;

import org.json.JSONObject;

public class NumberVerify extends AppCompatActivity {
Button btnverify;
String selectedUserID;
EditText txtverifycode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_verify);

        btnverify = (Button) findViewById(R.id.btnverifycode);
        txtverifycode = (EditText) findViewById(R.id.txtverifycode);

        Bundle bundle = getIntent().getExtras();
        if(bundle !=null && bundle.get("userVO")!=null) {
            selectedUserID = bundle.getString("userVO");
          //  MyToast.show(this,selectedUserID);


        }
    }

    public void verifycode (View view)
    {
        UsersVO usersVO = new UsersVO();
        usersVO.user_id = selectedUserID;
        usersVO.verification_code = txtverifycode.getText().toString();

        String res = MyRestAPI.PostCall("checkOTP",  usersVO.toJSONObject());
        //MyToast.show(this,res);
        System.out.println("Result = " + res);

        try {
            JSONObject jsonObject = new JSONObject(res);
            String status = jsonObject.getString("status");
            if(status.equals("1")) {

                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("userID", selectedUserID);
                editor.commit();

                Intent in = new Intent(this, MyProfile.class);
                in.putExtra("userid",selectedUserID);
                startActivity(in);
            }else{
                MyToast.show(this,"Verification code is Wrong.");
            }
        }catch (Exception e){

        }
    }
}
