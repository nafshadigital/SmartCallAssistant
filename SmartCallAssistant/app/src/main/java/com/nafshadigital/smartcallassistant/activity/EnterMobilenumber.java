package com.nafshadigital.smartcallassistant.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.nafshadigital.smartcallassistant.R;
import com.nafshadigital.smartcallassistant.adapter.ListAdapterViewCountry;
import com.nafshadigital.smartcallassistant.helpers.MyToast;
import com.nafshadigital.smartcallassistant.vo.CountryVO;
import com.nafshadigital.smartcallassistant.vo.UsersVO;
import com.nafshadigital.smartcallassistant.webservice.MyRestAPI;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

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

        getCountry();
        spincountry.setSelection(86); // Default to India
        spincountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View view, int position, long id) {

                CountryVO countryVO = (CountryVO) spincountry.getSelectedItem();
                country_code = countryVO.country_code;
                txtcountrycode.setText(countryVO.country_code);
            }
            public void onNothingSelected(AdapterView<?> arg0) { }
        });
    }

    public void getCountry() {
        try {
            JSONObject jsonObject = new JSONObject();
            String res = MyRestAPI.PostCall("getCountry", jsonObject);
            CountryAL = new CountryVO().getCountryArrayList(new JSONArray(res));
            ListAdapterViewCountry adapter = new ListAdapterViewCountry(this,android.R.layout.simple_spinner_item, CountryAL);
            spincountry.setAdapter(adapter);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public boolean isValidation(){

        btnok.setEnabled(false); // Prevent the double click

        if (txtcountrycode.getText().toString().equals("")) {
            MyToast.show(this, "Enter Country calling code");
            return false;
        }
        if (txtmobileno.getText().toString().equals("")) {
            MyToast.show(this, "Enter Mobile number");
            return false;
        }

        if(txtmobileno.getText().length() < 6 || txtmobileno.getText().length() > 10)
        {
            MyToast.show(this, "Enter valid Mobile number");
            return false;
        }
        if(txtcountrycode.getText().toString().equals("+91"))
        {
            Calendar rightNow = Calendar.getInstance();
            int currentHour = rightNow.get(Calendar.HOUR_OF_DAY);

            if(currentHour < 9 || currentHour > 21) {
                alertWindow("India SMS Timing","SMS can only be delivered between 9 A.M and 9 P.M");
                return false;
            }
        }
        String mobileNumber = txtcountrycode.getText().toString() + "-" + txtmobileno.getText().toString();
        String confirmMessage = "Is this your correct phone number? \n" + mobileNumber;
        String windowTitle = "Confirm Your Number";

        return confirmWindow(windowTitle,confirmMessage);
    }

    public boolean confirmWindow(String title, String message)
    {
        final boolean[] userAnswer = {false};
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        userAnswer[0] = true;
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        userAnswer[0] = false;
                    }
                });
        alertDialog.show();
        return  userAnswer[0];
    }
    public void alertWindow(String title,String message)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        alertDialog.show();
        //Center the OK Button
        final Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL);
        LinearLayout.LayoutParams positiveButtonLL = (LinearLayout.LayoutParams) positiveButton.getLayoutParams();
        positiveButtonLL.gravity = Gravity.CENTER;
        positiveButton.setLayoutParams(positiveButtonLL);

    }
    public void okverify(View view)
    {
        if(isValidation()) {
            UsersVO usersVO = new UsersVO();
            usersVO.country_code = txtcountrycode.getText().toString();
            usersVO.mobile = txtmobileno.getText().toString();

            String res = MyRestAPI.PostCall("signUp", usersVO.toJSONObject());
          //  MyToast.show(this, res);

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
                     MyToast.show(this,"Success");
                    Intent in = new Intent(this, NumberVerify.class);
                 //   MyToast.show(this, usersVO.id);
                    in.putExtra("userVO", id);
                    startActivity(in);
                }

            } catch (Exception e) {

            }
        }
    }
}
