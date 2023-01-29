package com.pusvo;

import android.content.Context;
import android.content.Intent;

import androidx.recyclerview.widget.RecyclerView;
import android.text.ClipboardManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecyclerViewAdapter4 extends RecyclerView.Adapter<RecyclerViewAdapter4.ViewHolder> {

    private Context context;
    private List<Data> results;

    public void setClipboard(Context context, String text) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Keterangan telah dicopy", text);
            clipboard.setPrimaryClip(clip);
        }
    }

    public RecyclerViewAdapter4(Context context, List<Data> results) {
        this.context = context;
        this.results = results;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view4, parent, false);
        ViewHolder holder = new ViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Data result = results.get(position);
        holder.textViewJumlah.setText(idr(Integer.parseInt(result.getJumlah())));
        holder.textViewTransaction_id.setText("#"+result.getId().toString());
        holder.textViewTransaction_kode.setText(result.getKode());
        holder.textViewID_depo.setText(result.getId().toString());
        holder.textViewMetode.setText(Enkripsi.getMetodeDetail(result.getMetode()));
        holder.textViewStatus.setText(Enkripsi.statusdeposit(result.getStatus()));
        holder.textViewHarga.setText(result.getJumlah());
        holder.textViewWaktu.setText(Enkripsi.getDate(result.getWaktu()));
        holder.textViewKeterangan.setText(result.getSN());
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.textTransaction_id)
        TextView textViewTransaction_id;
        @BindView(R.id.textTransaction_kode)
        TextView textViewTransaction_kode;
        @BindView(R.id.textID_depo)
        TextView textViewID_depo;
        @BindView(R.id.textJumlah)
        TextView textViewJumlah;
        @BindView(R.id.textMetode)
        TextView textViewMetode;
        @BindView(R.id.textStatus)
        TextView textViewStatus;
        @BindView(R.id.textHarga)
        TextView textViewHarga;
        @BindView(R.id.textWaktu)
        TextView textViewWaktu;
        @BindView(R.id.textKeterangan)
        TextView textViewKeterangan;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            String transaction_id = textViewTransaction_id.getText().toString();
            String transaction_kode = textViewTransaction_kode.getText().toString();
            String metode = textViewMetode.getText().toString();
            String status = textViewStatus.getText().toString();
            String id_depo = textViewID_depo.getText().toString();
            String harga = textViewHarga.getText().toString();
            String jumlah = textViewJumlah.getText().toString();
            if (status.equals("Belum dibayar")) {
                if (metode.equals("IDK On-Chain")) {
                    Intent i = new Intent(context, BayarDepoActivity.class);
                    i.putExtra("jumlah", jumlah);
                    i.putExtra("harga", harga);
                    i.putExtra("transaction_id", transaction_id);
                    i.putExtra("transaction_kode", transaction_kode);
                    i.putExtra("id_depo", id_depo);
                    context.startActivity(i);
                } else {
                    Intent i = new Intent(context, BCADepoActivity.class);
                    i.putExtra("jumlah", jumlah);
                    i.putExtra("harga", harga);
                    i.putExtra("transaction_id", transaction_id);
                    i.putExtra("transaction_kode", transaction_kode);
                    i.putExtra("id_depo", id_depo);
                    i.putExtra("metode", metode);
                    context.startActivity(i);
                }

            }
        }
    }
    public String idr(int idr) {
        return "Rp "+ NumberFormat.getNumberInstance(Locale.US).format(idr).replace(',', '.');
    }

}