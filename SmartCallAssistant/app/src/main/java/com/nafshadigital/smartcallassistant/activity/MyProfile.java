package com.nafshadigital.smartcallassistant.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.nafshadigital.smartcallassistant.R;
import com.nafshadigital.smartcallassistant.helpers.MyToast;
import com.nafshadigital.smartcallassistant.network.ApiInterface;
import com.nafshadigital.smartcallassistant.network.SmartCallAssistantApiClient;
import com.nafshadigital.smartcallassistant.vo.MyProfileResponse;
import com.nafshadigital.smartcallassistant.vo.ProfileVO;
import com.nafshadigital.smartcallassistant.vo.UpdateAccountResponse;
import com.nafshadigital.smartcallassistant.vo.UsersVO;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyProfile extends AppCompatActivity {
    EditText txtname,txtemail,txtmobno,txtcode;
    String android_id,device_id,name,email;
    Button btnupdate;
    String selectedUserID;
    private static final String TAG = "MyProfile";
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


        txtname = findViewById(R.id.txtnameprofile);
        txtemail = findViewById(R.id.txtemailprofile);
      //  txtmobno = (EditText) findViewById(R.id.txtmobilenoprofile);
     //   txtcode = (EditText) findViewById(R.id.txtcodeprofile);
        btnupdate = findViewById(R.id.btnupdateprofile);

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
                    usersVO.android_id = android_id;
                    usersVO.device_id = device_id;
                    usersVO.name = txtname.getText().toString();
                    usersVO.email = txtemail.getText().toString();

                    Call<UpdateAccountResponse> call= SmartCallAssistantApiClient.getClient()
                            .create(ApiInterface.class).updateAccount(usersVO);
                    call.enqueue(new Callback<UpdateAccountResponse>() {
                        @Override
                        public void onResponse(Call<UpdateAccountResponse> call, Response<UpdateAccountResponse> response) {
                            try {

                                UpdateAccountResponse updateAccountResponse=response.body();
                                if(updateAccountResponse!=null) {
                                    if(updateAccountResponse.getMessage()!=null)
                                    MyToast.show(getApplicationContext(), updateAccountResponse.getMessage());

                                    if (updateAccountResponse.getError().equals("0")) {
                                        Intent in = new Intent(MyProfile.this, Dashboard.class);
                                        in.putExtra("dashuserId", selectedUserID);
                                        startActivity(in);
                                        finish();
                                    }
                                }

                            }catch (Exception e) {
                                Log.e(TAG, "onResponse: ", e);
                            }
                        }

                        @Override
                        public void onFailure(Call<UpdateAccountResponse> call, Throwable t) {

                        }
                    });






                }
            }
        });

        UsersVO usersVO = new UsersVO();
        usersVO.id = selectedUserID;
        usersVO.android_id = android_id;
        usersVO.device_id = device_id;


        Call<MyProfileResponse> call= SmartCallAssistantApiClient.getClient()
                .create(ApiInterface.class).getProfile(usersVO);

        call.enqueue(new Callback<MyProfileResponse>() {
            @Override
            public void onResponse(Call<MyProfileResponse> call, Response<MyProfileResponse> response) {
                try {
                    MyProfileResponse myProfileResponse=response.body();
                    if(myProfileResponse!=null && myProfileResponse.getUserRecord()!=null){
                        if(myProfileResponse.getUserRecord().size()>0){
                            name=myProfileResponse.getUserRecord().get(0).getName();
                            email=myProfileResponse.getUserRecord().get(0).getEmail();
                            if(name.equals("null") && email.equals("null")){
                                txtname.setText("");
                                txtemail.setText("");
                            }else{
                                txtname.setText(name);
                                txtemail.setText(email);
                            }
                        }
                    }



                }catch (Exception e){
                }
            }

            @Override
            public void onFailure(Call<MyProfileResponse> call, Throwable t) {

            }
        });



    }

    public  boolean isValidate()
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        String email = txtemail.getText().toString();

        if(txtname.getText().toString().equals("")){
            MyToast.show(this,"Enter Name");
            return false;
        }

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;

        if(!pat.matcher(email).matches())
        {
            MyToast.show(this,"Email address is invalid !");
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

    public boolean onOptionsItemSelected(android.view.MenuItem item){
        finish();
        return true;
        }

    public void skipprofile(View view){
        Intent in = new Intent(this,Dashboard.class);
        startActivity(in);
        finish();
    }
}
