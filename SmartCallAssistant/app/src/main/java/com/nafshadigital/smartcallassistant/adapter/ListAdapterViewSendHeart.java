package com.nafshadigital.smartcallassistant.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.nafshadigital.smartcallassistant.R;
import com.nafshadigital.smartcallassistant.helpers.MyToast;
import com.nafshadigital.smartcallassistant.vo.FavoriteVO;
import com.nafshadigital.smartcallassistant.vo.SendHeartVO;
import com.nafshadigital.smartcallassistant.vo.SyncContactVO;
import com.nafshadigital.smartcallassistant.webservice.MyRestAPI;

import java.util.ArrayList;

public class ListAdapterViewSendHeart extends ArrayAdapter<SyncContactVO> {
    private final Activity context;
    protected ArrayList<SyncContactVO> remCollection;
    SyncContactVO rv;

    public ListAdapterViewSendHeart(Activity context, ArrayList<SyncContactVO> arrayList) {
        super(context, 0, arrayList);
        this.context = context;
        this. remCollection =arrayList;
    }

    LottieAnimationView lottieAnimationView;

    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_view_sendheart, parent, false);
        }

        TextView tvname = (TextView) convertView.findViewById(R.id.tvnamecon);
        TextView tvphnnum = (TextView) convertView.findViewById(R.id.tvphnnumcon);
        ImageView imgdel = (ImageView) convertView.findViewById(R.id.sendheart);

        this.lottieAnimationView = (LottieAnimationView) convertView.findViewById(R.id.animation_view);

        rv = getItem(position);

        //tvname.setText(rv.name);
        tvphnnum.setText(rv.phone);

        imgdel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                rv = remCollection.get(position);
                SyncContactVO SyncContactVO = new SyncContactVO(getContext());
                String name = rv.phone;
                alertDialogBuilder.setMessage("Do you want to Send a Heart(♥) to ? " + name);

                alertDialogBuilder.setPositiveButton("No",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                            }
                        });

                alertDialogBuilder.setNegativeButton("Yes",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        rv = remCollection.get(position);
                        FavoriteVO favoriteVO = new FavoriteVO(getContext());
                        int id = Integer.parseInt(rv.id);
                        MyToast.show(getContext(),"Success");

                        sendRandomHearts(id);

                        /*
                        rv = remCollection.get(position);
                        SyncContactVO SyncContactVO = new SyncContactVO(getContext());
                        int id = Integer.parseInt(rv.id);
                        MyToast.show(getContext(),"Success");

                        ((FavouriteActivity) context).display();
                        //Intent in = new Intent(context, FavouriteActivity.class);
                        // context.startActivity(in);
                        */
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        return  convertView;
    }

    public void sendRandomHearts(int id)
    {
        SharedPreferences sharedPreference = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        String userid = sharedPreference.getString("userID","");

        SendHeartVO users = new SendHeartVO();
        users.sender_id = userid;          // From User ID
        users.receiver_id = id +"";     // To User ID

        String savedId = MyRestAPI.PostCall("sendHeart",users.toJSONObject());
        System.out.println("Send Heart Result = " + savedId + users.toJSONObject());

    }

}
