package com.nafshadigital.smartcallassistant.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.nafshadigital.smartcallassistant.R;
import com.nafshadigital.smartcallassistant.helpers.MyToast;
import com.nafshadigital.smartcallassistant.helpers.PrefUtils;
import com.nafshadigital.smartcallassistant.network.ApiInterface;
import com.nafshadigital.smartcallassistant.network.SmartCallAssistantApiClient;
import com.nafshadigital.smartcallassistant.vo.UsersVO;
import com.nafshadigital.smartcallassistant.vo.VerifyOTPResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NumberVerify extends AppCompatActivity {
Button btnverify;
String selectedUserID;
EditText txtverifycode;
    private static final String TAG = "NumberVerify";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_verify);

        btnverify = findViewById(R.id.btnverifycode);
        txtverifycode = findViewById(R.id.txtverifycode);

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




        try {
            Call<VerifyOTPResponse> call= SmartCallAssistantApiClient.getClient()
                    .create(ApiInterface.class).verifyOTP(usersVO);

            call.enqueue(new Callback<VerifyOTPResponse>() {
                @Override
                public void onResponse(Call<VerifyOTPResponse> call, Response<VerifyOTPResponse> response) {
                    VerifyOTPResponse verifyOTPResponse=response.body();

                    if(verifyOTPResponse!=null && verifyOTPResponse.getStatus().equals("1")) {

                        PrefUtils.setUserId(selectedUserID,NumberVerify.this);

                        Intent in = new Intent(NumberVerify.this, MyProfile.class);
                        in.putExtra("userid",selectedUserID);
                        startActivity(in);
                    }else{
                        MyToast.show(NumberVerify.this,"Verification code is Wrong.");
                    }
                }

                @Override
                public void onFailure(Call<VerifyOTPResponse> call, Throwable t) {
                    Log.d(TAG, "onFailure: ",t);
                }
            });


        }catch (Exception e){
            Log.e(TAG, "verifycode: ",e );
        }
    }
}
