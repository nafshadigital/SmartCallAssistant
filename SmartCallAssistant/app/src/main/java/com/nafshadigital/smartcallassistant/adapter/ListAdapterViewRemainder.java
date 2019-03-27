package com.nafshadigital.smartcallassistant.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nafshadigital.smartcallassistant.R;
import com.nafshadigital.smartcallassistant.activity.Dashboard;
import com.nafshadigital.smartcallassistant.helpers.MyToast;
import com.nafshadigital.smartcallassistant.vo.RemainderVO;

import java.util.ArrayList;

public class ListAdapterViewRemainder extends ArrayAdapter<RemainderVO> {
    private final Activity context;
    protected java.util.ArrayList<RemainderVO> remCollection;
    RemainderVO rv;

    public ListAdapterViewRemainder(Activity context,ArrayList<RemainderVO> arrayList) {
        super(context, 0, arrayList);
        this.context = context;
        this. remCollection =arrayList;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_view_remainder, parent, false);
        }

        TextView tvremdate = convertView.findViewById(R.id.tvreminstdate);
        ImageView imgdel = convertView.findViewById(R.id.imgviewdelrem);

        rv = getItem(position);

        tvremdate.setText(rv.st_date);

        imgdel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RemainderVO remainderVO = new RemainderVO(getContext());
                remainderVO.deleteRemainder();
                MyToast.show(context,"Remainder Deleted!");
                Intent in = new Intent(context, Dashboard.class);
                context.startActivity(in);
            }
        });

        return  convertView;
    }
}
