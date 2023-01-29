package com.pusvo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;



public class DesaAdapter extends ArrayAdapter<String> {
    private List<String> cityList = new ArrayList<>();

    private List<String> ipdrodukList = new ArrayList<>();
    DesaAdapter(@NonNull Context context, int resource, int spinnerText, List<String> cityList, List<String> ipdrodukList) {
        super(context, resource, spinnerText, cityList);
        //super(context, resource);
        this.cityList = cityList;
        this.ipdrodukList = ipdrodukList;
    }


    @Override
    public String getItem(int position) {
        return cityList.get(position);
    }


    public String getIDProduk(int position) {
        return ipdrodukList.get(position);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position);

    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position);
    }


    private View initView(int position) {
        String statu = getItem(position);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.state_list, null);

        TextView textView = (TextView) v.findViewById(R.id.spinnerText);
        textView.setText(statu);
        return v;

    }
}
