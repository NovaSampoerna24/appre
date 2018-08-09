package id.go.patikab.rsud.remun.remunerasi.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import id.go.patikab.rsud.remun.remunerasi.R;
import id.go.patikab.rsud.remun.remunerasi.entity.DetailTindakan;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ViewHolder> {
    List<DetailTindakan>detailTindakanList;
    public  DetailAdapter(List<DetailTindakan> detailTindakans){
        this.detailTindakanList = detailTindakans;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        ViewHolder vh =new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txt_nm_tindakan.setText("Tindakan : "+detailTindakanList.get(position).getTindakan());
        holder.tanggal.setText("Tanggal : "+detailTindakanList.get(position).getTanggal());
        holder.tarif.setText("Tarif : "+  detailTindakanList.get(position).getTarif());
    }

    @Override
    public int getItemCount() {
        return detailTindakanList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_nm_tindakan,tanggal,tarif;
        public ViewHolder(View itemView) {
            super(itemView);
            txt_nm_tindakan = (TextView)itemView.findViewById(R.id.nm_tindakan);
            tanggal = (TextView)itemView.findViewById(R.id.tanggal);
            tarif = (TextView)itemView.findViewById(R.id.tarif);

        }
    }
}
