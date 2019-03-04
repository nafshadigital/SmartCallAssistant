package com.nafshadigital.smartcallassistant.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.nafshadigital.smartcallassistant.R;
import com.nafshadigital.smartcallassistant.helpers.MyToast;
import com.nafshadigital.smartcallassistant.vo.ProfileVO;
import com.nafshadigital.smartcallassistant.vo.UsersVO;
import com.nafshadigital.smartcallassistant.webservice.MyRestAPI;

import org.json.JSONException;
import org.json.JSONObject;

public class MyProfile extends AppCompatActivity {
    EditText txtname,txtemail,txtmobno,txtcode;
    String android_id,device_id,name,email;
    Button btnupdate;
    String selectedUserID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        setTitle("My Profile");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        android_id = android.provider.Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        try{
            TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
            device_id = telephonyManager.getDeviceId();
        }catch (SecurityException e){

        }


        txtname = (EditText) findViewById(R.id.txtnameprofile);
        txtemail = (EditText) findViewById(R.id.txtemailprofile);
      //  txtmobno = (EditText) findViewById(R.id.txtmobilenoprofile);
     //   txtcode = (EditText) findViewById(R.id.txtcodeprofile);
        btnupdate = (Button) findViewById(R.id.btnupdateprofile);

        Bundle bundle = getIntent().getExtras();
        if(bundle !=null && bundle.get("userid")!=null) {
            selectedUserID = bundle.getString("userid");
          //  MyToast.show(this,"Myprofileid="+ selectedUserID);
        }

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        selectedUserID =  sharedPreferences.getString("userID", "");

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isValidate()){

                    ProfileVO profileVO = new ProfileVO(getApplicationContext());
                    profileVO.name = txtname.getText().toString();
                    profileVO.email = txtemail.getText().toString();
                  //  profileVO.mobile_no = txtmobno.getText().toString();
                 //   profileVO.code = txtcode.getText().toString();

                    int result = profileVO.updateProfile();
                    //MyToast.show(getApplicationContext(),"update="+result);

                    UsersVO usersVO = new UsersVO();
                    usersVO.id = selectedUserID;
                    //usersVO.android_id = android_id;
                   // usersVO.device_id = device_id;
                    usersVO.name = txtname.getText().toString();
                    usersVO.email = txtemail.getText().toString();
                    String res = MyRestAPI.PostCall("updateAccount",usersVO.toJSONObject());
                    try {
                        JSONObject json = new JSONObject(res);
                        MyToast.show(getApplicationContext(),json.getString("message"));
                    }catch (JSONException e) {

                    }

                    Intent in = new Intent(MyProfile.this,Dashboard.class);
                    in.putExtra("dashuserId",selectedUserID);
                    startActivity(in);
                }
            }
        });

        UsersVO usersVO = new UsersVO();
        usersVO.id = selectedUserID;
       // usersVO.android_id = android_id;
        //usersVO.device_id = device_id;


        String res = MyRestAPI.PostCall("getprofile", usersVO.toJSONObject());

        try {
            JSONObject jsonObject = new JSONObject(res);
             name = jsonObject.getString("name");
             email = jsonObject.getString("email");

            if(name.equals("null") && email.equals("null")){
                txtname.setText("");
                txtemail.setText("");
            }else{
                txtname.setText(name);
                txtemail.setText(email);
            }
        }catch (Exception e){

        }

    }

    public  boolean isValidate(){
        if(txtname.getText().toString().equals("")){
            MyToast.show(this,"Enter your name !");
            return false;
        }

        if (!isValidEmail(txtemail.getText().toString())) {
          //  txtemail.setError("Invalid Email Address");
            MyToast.show(this,"Enter valid E-mail address !");
            return false;
        }

        /*else if(txtemail.getText().toString().equals(""))
        {
            MyToast.show(this,"Enter Email");
            return false;
        }
     /*   else if(txtmobno.getText().toString().equals("")){
            MyToast.show(this,"Enter MobileNo");
            return false;
        }
        else if(txtcode.getText().toString().equals("")){
            MyToast.show(this,"Enter Code");
            return false;
        } */
        return true;
    }

    public static boolean isValidEmail(String target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public boolean onOptionsItemSelected(android.view.MenuItem item){
        finish();
        return true;
        }

    public void skipprofile(View view){
        Intent in = new Intent(this,Dashboard.class);
        startActivity(in);
    }
}
