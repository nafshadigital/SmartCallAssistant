package com.nafshadigital.smartcallassistant.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.nafshadigital.smartcallassistant.R;
import com.nafshadigital.smartcallassistant.adapter.ListAdapterViewCountry;
import com.nafshadigital.smartcallassistant.helpers.MyToast;
import com.nafshadigital.smartcallassistant.vo.CountryVO;
import com.nafshadigital.smartcallassistant.vo.UsersVO;
import com.nafshadigital.smartcallassistant.webservice.MyRestAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class EnterMobilenumber extends AppCompatActivity {
    Spinner spincountry;
    EditText txtcountrycode,txtmobileno;
    Button btnok;
    ArrayList<CountryVO> CountryAL;
    String country_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_mobile);

        spincountry = (Spinner) findViewById(R.id.spincountry);
        txtcountrycode = (EditText) findViewById(R.id.txtcountrycode);
        txtmobileno = (EditText) findViewById(R.id.txtphnnumberverify);
        btnok = (Button) findViewById(R.id.btnokverify);

        getcountry();
        spincountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View view, int position, long id) {

              //CountryVO countryVO =  CountryAL.get(position);
                CountryVO countryVO = (CountryVO) spincountry.getSelectedItem();
                country_code = countryVO.country_code;
             //   MyToast.show(getApplicationContext(),""+position + " - " +country_code);

                    txtcountrycode.setText(countryVO.country_code);

            }
            public void onNothingSelected(AdapterView<?> arg0) { }
        });


    }

    public void getcountry() {
        try {
            JSONObject jsonObject = new JSONObject();
            String res = MyRestAPI.PostCall("getCountry", jsonObject);
           // MyToast.show(this,res);
            System.out.println("countryRes="+res);
            CountryAL = new CountryVO().getCountryArrayList(new JSONArray(res));
            ListAdapterViewCountry adapter = new ListAdapterViewCountry(this,android.R.layout.simple_spinner_item, CountryAL);
            spincountry.setAdapter(adapter);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public boolean isValidation(){
        if (txtcountrycode.getText().toString().equals("")) {
            MyToast.show(this, "Enter  CountryCode");
            return false;
        }
        if (txtmobileno.getText().toString().equals("")) {
            MyToast.show(this, "Enter MobileNo");
            return false;
        }
        if(txtmobileno.getText().length() != 9 && txtmobileno.getText().length() != 10)
        {
            MyToast.show(this, "Please enter proper numbers");
            return false;
        }

        return true;
    }
    public void okverify(View view)
    {
        if(isValidation()) {
            UsersVO usersVO = new UsersVO();
            usersVO.country_code = txtcountrycode.getText().toString();
            usersVO.mobile = txtmobileno.getText().toString();

            String res = MyRestAPI.PostCall("signUp", usersVO.toJSONObject());
          //  MyToast.show(this, res);
            btnok.setEnabled(false);
            try {
                JSONObject jsonObject = new JSONObject(res);
                String status = jsonObject.getString("status");
                String id = jsonObject.getString("user_id");
                String otp = jsonObject.getString("otp");
             //   MyToast.show(this, "res=" + status);

                if (status.equals("1")) {
              /*  String phnnum = txtmobileno.getText().toString();
                    try {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(phnnum, null, otp, null, null);
                        //   Toast.makeText(context, "SMS Sent!", Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        //  Toast.makeText(context, "SMS failed, please try again later!", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    } */
                    Intent in = new Intent(this, NumberVerify.class);
                 //   MyToast.show(this, usersVO.id);
                    in.putExtra("userVO", id);
                    startActivity(in);
                }
            } catch (Exception e) {
                btnok.setEnabled(true);
            }
        }
    }
}