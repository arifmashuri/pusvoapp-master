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


public class ProdukAdapter extends RecyclerView.Adapter<ProdukAdapter.ViewHolder> {
    int selectedPosition = -1;
    int SelectedPosition2=-2;
    private Context context;
    private List<Produk> results;
    private Integer metodedefault;
    private Integer idprodukdefault=-1;
    ProdukClickListener produkClickListener;


    public ProdukAdapter(Context context,ProdukClickListener produkClickListener, List<Produk> results,Integer metodedefault,Integer idprodukdefault) {
        this.context = context;
        this.produkClickListener = produkClickListener;
        this.results = results;
        this.metodedefault=metodedefault;
        this.idprodukdefault=idprodukdefault;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_produk, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Produk result = results.get(position);
        holder.textNama.setText(result.getNama());

        if(result.getHargaJual()!=null && !metodedefault.equals(0)) {

            holder.textHarga.setText("("+Enkripsi.tentukanhargarupiah(result.getHargaJual(), metodedefault,0.0)+")");

            holder.textHarga.setVisibility(View.VISIBLE);
        }
        else
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
            produkClickListener.onClick(result.getId(),result.getHargaJual(),result.getDeskripsi());
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