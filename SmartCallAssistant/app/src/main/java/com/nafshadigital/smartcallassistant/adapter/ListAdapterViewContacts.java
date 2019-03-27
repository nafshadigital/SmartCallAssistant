package com.nafshadigital.smartcallassistant.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nafshadigital.smartcallassistant.R;

import java.util.ArrayList;


public class ListAdapterViewContacts extends ArrayAdapter<String> {
    private final Activity context;
    private java.util.ArrayList<String> catCollection;
    private String contacts;

    public ListAdapterViewContacts(Activity context,ArrayList<String> arrayList) {
        super(context, 0, arrayList);
        this.context = context;
        this. catCollection =arrayList;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_view_contacts, parent, false);
        }

        TextView txtbusname = convertView.findViewById(R.id.tvcontnamenum);

        contacts = getItem(position);

        txtbusname.setText(contacts);

        return convertView;
    }

}
