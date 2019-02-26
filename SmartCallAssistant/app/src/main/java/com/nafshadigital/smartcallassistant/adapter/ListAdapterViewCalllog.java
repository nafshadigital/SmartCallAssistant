package com.nafshadigital.smartcallassistant.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nafshadigital.smartcallassistant.R;
import com.nafshadigital.smartcallassistant.activity.CallDetail;
import com.nafshadigital.smartcallassistant.activity.CallLogs;
import com.nafshadigital.smartcallassistant.activity.FavouriteActivity;
import com.nafshadigital.smartcallassistant.helpers.MyToast;
import com.nafshadigital.smartcallassistant.vo.ActivityVO;
import com.nafshadigital.smartcallassistant.vo.CallLogVO;
import com.nafshadigital.smartcallassistant.vo.FavoriteVO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class ListAdapterViewCalllog extends ArrayAdapter<CallLogVO> {
    private final Activity context;
    protected java.util.ArrayList<CallLogVO> actCollection;
    CallLogVO rv;
    public static String month_name;
    public static String date;
    public static String date_num;
    public final static String monthAgo =  " months";
    public final static String daysAgo =  " days";
    public final static String hoursAgo = " hours";
    public final static String minAgo =  " minutes";
    public final static String secAgo = " seconds";
    static int second = 1000; // milliseconds
    static int minute = 60;
    static int hour = minute * 60;
    static int day = hour * 24;
    static int week = day * 7;
    static int month = day * 30;
    static int year = month * 12;

    public ListAdapterViewCalllog(Activity context,ArrayList<CallLogVO> arrayList) {
        super(context, 0, arrayList);
        this.context = context;
        this. actCollection =arrayList;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_view_calllog, parent, false);
        }

        ImageView imgincome = (ImageView) convertView.findViewById(R.id.imgincomelog);
        ImageView imgoutgoing = (ImageView) convertView.findViewById(R.id.imgoutgolog);
        ImageView imgdelcall = (ImageView) convertView.findViewById(R.id.imgcallogdel);
        ImageView imgcalldet = (ImageView) convertView.findViewById(R.id.imgreclogrigarrow);
        TextView txtbusname = (TextView) convertView.findViewById(R.id.tvcalllognumber);
        TextView txtcalldate = (TextView) convertView.findViewById(R.id.txtcalllogdate);



        rv = getItem(position);

        if(rv.incoming.equals("1"))
        {
            imgincome.setVisibility(View.VISIBLE);
        }else if(rv.outgoing.equals("1"))
        {
            imgoutgoing.setVisibility(View.VISIBLE);
        }
        txtbusname.setText(rv.number);


        String postdate = rv.dt_log;
//MyToast.show(context,postdate);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a", Locale.ENGLISH);
        try {
            Date mDate = sdf.parse(postdate);
            long timeInMilliseconds = mDate.getTime();
            txtcalldate.setText(DateDifference(timeInMilliseconds));
         //   MyToast.show(getContext(),DateDifference(timeInMilliseconds));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        imgdelcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("Do you want to remove Yes or No ?");
                alertDialogBuilder.setPositiveButton("No",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                            }
                        });

                alertDialogBuilder.setNegativeButton("Yes",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        rv = actCollection.get(position);
                        CallLogVO callLogVO = new CallLogVO(getContext());
                        int id = Integer.parseInt(rv.id);
                        callLogVO.deletecall(id);
                        MyToast.show(getContext(),"Success");
                        // Intent in = new Intent(context,CallLogVO.class);
                        //  context.startActivity(in);
                        if(((CallLogs)context).tvcalltype.getText().equals("Outgoing Calls")) {
                            ((CallLogs)context).displayOutgoingcall();
                        }else {
                            ((CallLogs)context).displayIncomingcall();
                        }

                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });


        imgcalldet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent incalldet = new Intent(context, CallDetail.class);
                CallLogVO callLogVO = actCollection.get(position);
                incalldet.putExtra("callLog",callLogVO.id);
                context.startActivity(incalldet);
            }
        });

        return  convertView;
    }

    @SuppressLint("SimpleDateFormat")
    public static String DateDifference(long fromDate) {
        long diff = 0;


        long currentDate = System.currentTimeMillis();
        // get difference in milli seconds
        diff = currentDate - fromDate;

        int diffInSec = Math.abs((int) (diff / (second)));
        String difference = "";
        if(diffInSec < minute)
        {
            difference = diffInSec+secAgo;
        }
        else if((diffInSec / hour) < 1)
        {
            difference = (diffInSec/minute)+minAgo;
        }
        else if((diffInSec/ day) < 1)
        {
            difference = (diffInSec/hour)+hoursAgo;
        }
        else if((diffInSec/ week) < 1)
        {
            difference = (diffInSec/day)+daysAgo;
        }
        else if((diffInSec/month)<1)
        {
            Date frmDate = new Date();
            frmDate.setTime(fromDate);
            SimpleDateFormat sdfMonDate = new SimpleDateFormat("dd-MMM", Locale.ENGLISH);
            month_name = sdfMonDate.format(frmDate);

            difference=month_name;
          //  difference = (diffInSec / week)+weekAgo;
        }
        else if((diffInSec/year)<1)
        {
            Date frmDate = new Date();
            frmDate.setTime(fromDate);
            SimpleDateFormat sdfDate = new SimpleDateFormat("dd-mm-yyyy", Locale.ENGLISH);
            date = sdfDate.format(frmDate);
            difference = date;
        }
        else
        {
            // return date
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(fromDate);

            SimpleDateFormat format_before = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

            difference = format_before.format(c.getTime());
            System.out.println("Currentdate"+difference);
        }


        Log.e("time difference is: ","" + difference);
        return difference;
    }

}
