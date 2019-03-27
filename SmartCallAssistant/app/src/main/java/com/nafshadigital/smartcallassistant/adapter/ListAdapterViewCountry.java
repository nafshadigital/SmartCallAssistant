package com.nafshadigital.smartcallassistant.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nafshadigital.smartcallassistant.R;
import com.nafshadigital.smartcallassistant.vo.CountryVO;

import java.util.ArrayList;


public class ListAdapterViewCountry extends ArrayAdapter<CountryVO> {
    private final Activity context;
    protected java.util.ArrayList<CountryVO> catCollection;
    CountryVO rv;

    public ListAdapterViewCountry(Activity context,int textViewResourceId,ArrayList<CountryVO> arrayList) {
        super(context, textViewResourceId, arrayList);
        this.context = context;
        this. catCollection =arrayList;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {   // This view starts when we click the spinner.
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_view_country, parent, false);
        }

        TextView txtconname = convertView.findViewById(R.id.txtcountryname);

        rv = getItem(position);

        txtconname.setText(rv.country);

        return  convertView;
    }
}
