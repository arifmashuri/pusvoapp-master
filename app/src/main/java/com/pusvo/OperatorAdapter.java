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


public class OperatorAdapter extends RecyclerView.Adapter<OperatorAdapter.ViewHolder> {
    int selectedPosition = -1;
    int SelectedPosition2=-2;
    private Context context;
    private List<Operator> results;
    private Integer idprodukdefault=-1;
    OperatorClickListener operatorClickListener;



    public OperatorAdapter(Context context, OperatorClickListener operatorClickListener, List<Operator> results) {
        this.context = context;
        this.operatorClickListener = operatorClickListener;
        this.results = results;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_produk, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Operator result = results.get(position);
        holder.textNama.setText(result.getNama());
        holder.textHarga.setVisibility(View.GONE);
        if(selectedPosition==-1 && idprodukdefault!=null && idprodukdefault.equals(result.getId())) {
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
            operatorClickListener.onClick(result.getId());
    }

    @Override
    public int getItemCount() {
        return results.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_nama)
        TextView textNama;
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