package com.nafshadigital.smartcallassistant.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.nafshadigital.smartcallassistant.R;
import com.nafshadigital.smartcallassistant.activity.Dashboard;
import com.nafshadigital.smartcallassistant.activity.Remainder;
import com.nafshadigital.smartcallassistant.helpers.MyToast;
import com.nafshadigital.smartcallassistant.vo.ActivityVO;
import com.nafshadigital.smartcallassistant.webservice.ActivityService;

import java.util.ArrayList;


public class ListAdapterViewActivity extends ArrayAdapter<ActivityVO> {
    private final Activity context;
    protected java.util.ArrayList<ActivityVO> actCollection;
    ActivityVO rv;
    int selectedPosition = -1;

    public ListAdapterViewActivity(Activity context,ArrayList<ActivityVO> arrayList) {
        super(context, 0, arrayList);
        this.context = context;
        this. actCollection =arrayList;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_view_activity, parent, false);
        }

        final RadioButton chkactivity = convertView.findViewById(R.id.chkactivity);
        ImageView imgremain = convertView.findViewById(R.id.imgremainder);
        ImageView imgright = convertView.findViewById(R.id.imgrightarrow);
        ImageView imgdelete = convertView.findViewById(R.id.imgstatact);

        LinearLayout layoutactivity = convertView.findViewById(R.id.layoutactivity);


        rv = getItem(position);

        chkactivity.setChecked(position == selectedPosition);
        chkactivity.setTag(position);
        chkactivity.setText(rv.activity_name);

        chkactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition = (Integer)v.getTag();
                notifyDataSetChanged();
                for (ActivityVO activityVo: actCollection ) {
                    activityVo.is_active = "0";
                }
                rv = actCollection.get(position);
                ActivityService activityService = new ActivityService(getContext());
                int result = activityService.updateIsactive(rv.id);
                rv.is_active = "1";

                Intent in = new Intent(context, Remainder.class);
                rv = actCollection.get(position);
                in.putExtra("actvityVO",rv);
                context.startActivity(in);
            }
        });

        layoutactivity.setBackgroundColor(Color.parseColor("#f1f1f1"));
        chkactivity.setChecked(false);
        if(rv.is_active != null && rv.is_active.equals("1"))
        {
            layoutactivity.setBackgroundColor(Color.parseColor("#f6dfbd"));
            chkactivity.setChecked(true);
        }

        if(rv.is_default.equals("1"))
        {
            imgdelete.setVisibility(View.INVISIBLE);
        }
        else if(rv.is_default.equals("0"))
        {
            imgdelete.setVisibility(View.VISIBLE);
        }

        imgdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("Do you want to delete the Activity("+rv.activity_name+")");
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
                        ActivityService activity = new ActivityService(getContext());
                        activity.deleteActivity(rv.id);
                        MyToast.show(context,"Success");
                        Intent in = new Intent(context, Dashboard.class);
                        context.startActivity(in);
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();



            }
        });

        imgright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context, Remainder.class);
                rv = actCollection.get(position);
                in.putExtra("actvityVO",rv);
                context.startActivityForResult(in, 1);
                //context.startActivity(in);
            }
        });

        imgremain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(context, Remainder.class);
                rv = actCollection.get(position);
                in.putExtra("actvityVO",rv);
                context.startActivityForResult(in, 1);
            }
        });



        return  convertView;
    }

}
