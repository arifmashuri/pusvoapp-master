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



public class StatuAdapter extends ArrayAdapter<Statu> {
    private List<Statu> stateList = new ArrayList<>();

    StatuAdapter(@NonNull Context context, int resource, int spinnerText, @NonNull List<Statu> stateList) {
        super(context, resource, spinnerText, stateList);
        this.stateList = stateList;
    }

    @Override
    public Statu getItem(int position) {
        return stateList.get(position);
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
        Statu statu = getItem(position);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.state_list, null);

        TextView textView = (TextView) v.findViewById(R.id.spinnerText);
        textView.setText(statu.getStateName());
        return v;

    }
}
