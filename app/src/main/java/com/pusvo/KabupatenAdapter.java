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


public class KabupatenAdapter extends ArrayAdapter<Listkabupaten> {
    private List<Listkabupaten> stateList = new ArrayList<>();

    KabupatenAdapter(@NonNull Context context, int resource, int spinnerText, @NonNull List<Listkabupaten> stateList) {
        super(context, resource, spinnerText, stateList);
        this.stateList = stateList;
    }

    @Override
    public Listkabupaten getItem(int position) {
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
        Listkabupaten statu = getItem(position);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.state_list, null);

        TextView textView = (TextView) v.findViewById(R.id.spinnerText);
        if(statu==null || statu.getNama()==null || statu.getNama().isEmpty())
            textView.setText("-");
        else
        textView.setText(statu.getNama());
        return v;

    }
}
