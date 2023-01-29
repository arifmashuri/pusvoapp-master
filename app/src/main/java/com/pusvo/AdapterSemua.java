package com.pusvo;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AdapterSemua extends RecyclerView.Adapter<AdapterSemua.ViewHolder> {

    private Context context;
    private List<Data> results;


    public AdapterSemua(Context context, List<Data> results) {
        this.context = context;
        this.results = results;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_semua, parent, false);
        ViewHolder holder = new ViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //TODO PR cek harga apakah sesuai
        Data result = results.get(position);
        holder.textViewhp.setText(result.getHp());
        holder.transaction_kode=result.getKode();
        holder.textViewTransaction_id.setText(result.getId().toString());
        holder.textViewProduk.setText(result.getProdukdetail().getNama());
//        if(result.getKode().length()>29 && !result.getMetode().equals(13))
        if(result.getKode().length()>29)
        holder.textViewHarga.setText(Enkripsi.rupiah(Double.parseDouble(Enkripsi.tentukanhargaadapter(result.getHargajual(),result.getMetode(),result.getRand()).toString())));
        //if(result.getKode().length()>29 && result.getMetode().equals(13))
//        holder.textViewHarga.setText(Enkripsi.tentukanharga(result.getHargajual(),result.getMetode()).toString());
        //holder.textViewHarga.setText(Enkripsi.idkrupiah(Double.parseDouble(Integer.toString(Enkripsi.hargafix(Integer.parseInt(result.getHargajual()))))/1000));
        //holder.textViewHarga.setText(Enkripsi.rupiah(Double.parseDouble(Enkripsi.tentukanharga(result.getHargajual(),result.getMetode()).toString())));
        if(result.getKode().length()<29)
        holder.textViewHarga.setText(Enkripsi.rupiah(Double.parseDouble(Enkripsi.tentukanhargaadaptertopup(result.getHargajual(),result.getMetode(),result.getRand()).toString())));
        //holder.textViewHarga.setText(Enkripsi.rupiah(Double.parseDouble(Enkripsi.tentukanharga(result.getHargajual(),result.getMetode()).toString())));
        holder.textViewMetode.setText(Enkripsi.getMetodeDetail(result.getMetode()));
        holder.textViewWaktu.setText(Enkripsi.getDate(result.getWaktu()));

        holder.status=result.getStatus();
        if(result.getStatus()==3) {
            holder.textstatus.setText(R.string.transaksi_sukses);
            holder.textstatus.setTextColor(context.getResources().getColor(R.color.background));
            holder.textViewKeterangan.setText(result.getSN());
            holder.linearKeterangan.setVisibility(View.VISIBLE);
        }
        if(result.getStatus()==4) {
            holder.textstatus.setText(R.string.transaksi_gagal_hubungi_kami);
            holder.textstatus.setTextColor(context.getResources().getColor(R.color.merah));
        }
        if(result.getStatus()==7) {
            holder.textstatus.setText(R.string.transaksi_kurang);
            holder.textstatus.setTextColor(context.getResources().getColor(R.color.merah));
        }
        if(result.getStatus()==0) {
            holder.textstatus.setText(R.string.transaksi_belum);
            holder.textstatus.setTextColor(context.getResources().getColor(R.color.text_info));
        }
        if(result.getStatus()==2) {
            holder.textstatus.setText(R.string.transaksi_proses);
            holder.textstatus.setTextColor(context.getResources().getColor(R.color.text_info));
        }
        if(result.getStatus()==5) {
            holder.textstatus.setText(R.string.transaksi_batal);
        }
        if(result.getStatus()==6) {
            holder.textstatus.setText(R.string.transaksi_refund);
            holder.textstatus.setTextColor(context.getResources().getColor(R.color.text_info));
        }
        if(result.getKode().length()==28)
            holder.judulhp.setText(R.string.hporrek);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.texthp) TextView textViewhp;
        @BindView(R.id.textTransaction_id) TextView textViewTransaction_id;
        @BindView(R.id.textProduk) TextView textViewProduk;
        @BindView(R.id.textHarga) TextView textViewHarga;
        @BindView(R.id.textMetode) TextView textViewMetode;
        @BindView(R.id.textWaktu) TextView textViewWaktu;
        @BindView(R.id.textKeterangan) TextView textViewKeterangan;
        @BindView(R.id.judulketerangan) TextView judulketerangan;
        @BindView(R.id.judulhp) TextView judulhp;
        @BindView(R.id.textstatus) TextView textstatus;
        @BindView(R.id.linearKeterangan) TableRow linearKeterangan;

        Integer status;
String transaction_kode;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (status == 0) {
                if (transaction_kode.length() > 29) {
                    Intent i = new Intent(context, BayarActivity.class);
                    i.putExtra("transaction_kode", transaction_kode);
                    context.startActivity(i);
                } else {
                    Intent i = new Intent(context, BayarTopupActivity.class);
                    i.putExtra("transaction_kode", transaction_kode);
                    context.startActivity(i);
                }

//                }
            }
            if (status == 7) {
                PackageManager pm = context.getPackageManager();
                String transaction_id = textViewTransaction_id.getText().toString();
                Intent sendIntent = new Intent("android.intent.action.MAIN");
                sendIntent.setAction(Intent.ACTION_VIEW);
                sendIntent.setPackage("com.whatsapp");

                String url = "whatsapp://send?phone=6283844662524&text=Halo Pusvo, mohon refund transaksi dengan ID order " + transaction_id + " " +
                        "Keterangan : pembayaran kurang";
                sendIntent.setData(Uri.parse(url));
                if (Enkripsi.isPackageInstalled("com.whatsapp", pm)) {
                    context.startActivity(sendIntent);
                } else {
                    Toast.makeText(context, "Aplikasi Whatsapp belum terinstal", Toast.LENGTH_SHORT).show();
                }

            }


            if (status == 4) {
                PackageManager pm = context.getPackageManager();
                Intent sendIntent = new Intent("android.intent.action.MAIN");
                sendIntent.setAction(Intent.ACTION_VIEW);
                sendIntent.setPackage("com.whatsapp");
//                String url = "https://api.whatsapp.com/send?phone=" + "6283844662524" + "&text=" + "your message";
                String url = "whatsapp://send?phone=6283844662524&text=Halo Pusvo, tolong refund transaksi dengan ID order " + textViewTransaction_id.getText().toString();
                sendIntent.setData(Uri.parse(url));

                if (Enkripsi.isPackageInstalled("com.whatsapp", pm)) {
                    context.startActivity(sendIntent);
                } else {
                    Toast.makeText(context, "Aplikasi Whatsapp belum terinstal", Toast.LENGTH_SHORT).show();
                }

            }

            if (status == 2 || status == 3) {
                Intent i = new Intent(context, RincianTransaksi.class);
                i.putExtra("transaction_kode", transaction_kode);
                context.startActivity(i);
            }


        }


        }


}