package com.nafshadigital.smartcallassistant.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nafshadigital.smartcallassistant.R;
import com.nafshadigital.smartcallassistant.activity.FavouriteActivity;
import com.nafshadigital.smartcallassistant.helpers.MyToast;
import com.nafshadigital.smartcallassistant.vo.FavoriteVO;

import java.util.ArrayList;

public class ListAdapterViewFavorites extends ArrayAdapter<FavoriteVO> {
    private final Activity context;
    protected java.util.ArrayList<FavoriteVO> remCollection;
    FavoriteVO rv;

    public ListAdapterViewFavorites(Activity context,ArrayList<FavoriteVO> arrayList) {
        super(context, 0, arrayList);
        this.context = context;
        this. remCollection =arrayList;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_view_favorites, parent, false);
        }

        TextView tvname = convertView.findViewById(R.id.tvnamecon);
        TextView tvphnnum = convertView.findViewById(R.id.tvphnnumcon);
        ImageView imgdel = convertView.findViewById(R.id.imgdelfav);

        rv = getItem(position);

        tvname.setText(rv.name);
        tvphnnum.setText(rv.phnnumber);

        imgdel.setOnClickListener(new View.OnClickListener() {
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
                        rv = remCollection.get(position);
                        FavoriteVO favoriteVO = new FavoriteVO(getContext());
                        int id = Integer.parseInt(rv.id);
                        favoriteVO.deleteFavourite(id);
                        MyToast.show(getContext(),"Success");

                        ((FavouriteActivity) context).display();
                        //Intent in = new Intent(context, FavouriteActivity.class);
                        // context.startActivity(in);
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        return  convertView;
    }
}
