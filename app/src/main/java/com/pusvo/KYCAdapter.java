package com.pusvo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class KYCAdapter extends RecyclerView.Adapter<KYCAdapter.ViewHolder> {
    int selectedPosition = -1;
    int SelectedPosition2=-2;
    private Context context;
    private List<Listkyc> results;
    private Integer metodedefault;
    private Integer idkycdefault=-1;
    KYCClickListener kycClickListener;


    public KYCAdapter(Context context, KYCClickListener kycClickListener, List<Listkyc> results, Integer metodedefault, Integer idkycdefault) {
        this.context = context;
        this.kycClickListener = kycClickListener;
        this.results = results;
        this.metodedefault=metodedefault;
        this.idkycdefault=idkycdefault;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_produk, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Listkyc result = results.get(position);
        holder.textNama.setText(result.getBankdetail().getNama());
        holder.textNorek.setText(result.getNorek());
        holder.textHarga.setText(result.getNama());
        holder.textNorek.setVisibility(View.VISIBLE);
//        if(result.getId()!=null && !metodedefault.equals(0)) {
//
//            holder.textHarga.setText("testing hahaha");
//
//            holder.textHarga.setVisibility(View.VISIBLE);
//        }
//        else
//            holder.textHarga.setVisibility(View.GONE);

        //holder.textHarga.setVisibility(View.GONE);

        if(selectedPosition==-1 && idkycdefault!=null && idkycdefault.equals(result.getId())) {
            holder.cardmetode.setBackgroundResource(R.drawable.card_view_border);
                SelectedPosition2=holder.getBindingAdapterPosition();
        }
        else {
            if(position==selectedPosition)
                holder.cardmetode.setBackgroundResource(R.drawable.card_view_border);
            else
                holder.cardmetode.setBackgroundResource(R.drawable.card_view_border2);
        }


        if(position==selectedPosition)
            kycClickListener.onClick(result.getId(),result.getBankId(),result.getNorek());

    }

    @Override
    public int getItemCount() {
        return results.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_nama)
        TextView textNama;
        @BindView(R.id.text_norek)
        TextView textNorek;
        @BindView(R.id.text_harga)
        TextView textHarga;

        @BindView(R.id.cardview_metode) CardView cardmetode;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            cardmetode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int copyOfLastCheckedPosition = selectedPosition;
                    selectedPosition = getBindingAdapterPosition();
                    notifyItemChanged(copyOfLastCheckedPosition);
                    notifyItemChanged(selectedPosition);
                    notifyItemChanged(SelectedPosition2);
                }
            });
        }
    }


}