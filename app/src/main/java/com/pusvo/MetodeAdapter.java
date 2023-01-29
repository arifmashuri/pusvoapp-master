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


public class MetodeAdapter extends RecyclerView.Adapter<MetodeAdapter.ViewHolder> {
    int selectedPosition = -1;
    int SelectedPosition2=-2;
    private Context context;
    private List<Metode> results;
    private Double harga;
    private Integer metodedefault;
    ItemClickListener itemClickListener;


    public MetodeAdapter(Context context,ItemClickListener itemClickListener, List<Metode> results,Double harga,Integer metodedefault) {
        this.context = context;
        this.itemClickListener = itemClickListener;
        this.results = results;
        this.harga = harga;
        this.metodedefault=metodedefault;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_metode, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Metode result = results.get(position);
        holder.textMetode.setText(result.getNama());

        if(harga!=null) {
            holder.textHarga.setText("("+Enkripsi.tentukanhargarupiah(harga, result.getId(),0.0)+")");
            holder.textHarga.setVisibility(View.VISIBLE);
        }
        else
            holder.textHarga.setVisibility(View.GONE);



        if(selectedPosition==-1 && metodedefault.equals(result.getId()) && result.getStatus()==1) {
            holder.cardmetode.setBackgroundResource(R.drawable.card_view_border);
            if(metodedefault.equals(result.getId()))
                SelectedPosition2=holder.getBindingAdapterPosition();
        }
        else {
            if(position==selectedPosition && result.getStatus()==1)
            holder.cardmetode.setBackgroundResource(R.drawable.card_view_border);
            else
            holder.cardmetode.setBackgroundResource(R.drawable.card_view_border2);
        }


        if(position==selectedPosition && result.getStatus()==1)
            itemClickListener.onClick(result.getId());
        if(position==selectedPosition && result.getStatus()==0)
            itemClickListener.onClick(metodedefault);
        if(result.getStatus()==0) {
            holder.cardmetode.setBackgroundResource(R.drawable.card_view_border3);
        }
    }

    @Override
    public int getItemCount() {
        return results.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_metode)
        TextView textMetode;
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