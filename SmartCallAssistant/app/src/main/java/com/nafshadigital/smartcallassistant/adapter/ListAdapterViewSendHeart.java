package com.nafshadigital.smartcallassistant.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.nafshadigital.smartcallassistant.R;
import com.nafshadigital.smartcallassistant.helpers.MyToast;
import com.nafshadigital.smartcallassistant.helpers.PrefUtils;
import com.nafshadigital.smartcallassistant.network.ApiInterface;
import com.nafshadigital.smartcallassistant.network.SmartCallAssistantApiClient;
import com.nafshadigital.smartcallassistant.vo.FavoriteVO;
import com.nafshadigital.smartcallassistant.vo.SendHeartVO;
import com.nafshadigital.smartcallassistant.vo.SyncContactVO;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListAdapterViewSendHeart extends ArrayAdapter<SyncContactVO> {
    private final Activity context;
    protected ArrayList<SyncContactVO> remCollection;
    SyncContactVO rv;
    private static final String TAG = "ListAdapterViewSendHear";
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

        TextView tvname = convertView.findViewById(R.id.tvnamecon);
        TextView tvphnnum = convertView.findViewById(R.id.tvphnnumcon);
        ImageView imgdel = convertView.findViewById(R.id.sendheart);

        this.lottieAnimationView = convertView.findViewById(R.id.animation_view);

        rv = getItem(position);

        //tvname.setText(rv.name);
        tvphnnum.setText(rv.name);

        imgdel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                rv = remCollection.get(position);
                SyncContactVO SyncContactVO = new SyncContactVO(getContext());
                String name = rv.name;
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


                        System.out.println("OK-->" + rv.phone + " " + rv.name);
                        MyToast.show(getContext(),"Success");

                        sendRandomHearts(rv.phone,rv.name);

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

    public void sendRandomHearts(String receiver_phone,String receiver_name)
    {

        String userid = PrefUtils.getUserId(context);

        SendHeartVO users = new SendHeartVO();
        users.sender_id = userid;               // From User ID
        users.receiver_phone = receiver_phone;  // To Reeiver Phone Number, as we will not know the User ID of the
                                                // Phone Number associated

        Call<ResponseBody> call= SmartCallAssistantApiClient.getClient()
                .create(ApiInterface.class).sendHeart(users);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
             ResponseBody responseBody=response.body();
             if(responseBody!=null) {
                 try {
                     Log.d(TAG, "onResponse: "+responseBody.string());
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "onFailure: ",t );
            }
        });

    }
}
