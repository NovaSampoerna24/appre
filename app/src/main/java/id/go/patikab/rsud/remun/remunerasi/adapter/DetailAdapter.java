package id.go.patikab.rsud.remun.remunerasi.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import id.go.patikab.rsud.remun.remunerasi.R;
import id.go.patikab.rsud.remun.remunerasi.entity.DetailList;
import id.go.patikab.rsud.remun.remunerasi.entity.ValDetail;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ViewHolder> {
    List<DetailList>detailLists;
    public  DetailAdapter(List<DetailList> detailListses){
        this.detailLists = detailListses;
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
        holder.txt_nm_tindakan.setText("Tindakan : "+detailLists.get(position).getNama_tindakan());
        holder.txt_pengolahan.setText("Pengolahan : "+detailLists.get(position).getPengolahan());
        holder.txt_persentase.setText("Persentase : "+detailLists.get(position).getPersentase_pribadi());
        holder.txt_dapat.setText("Jumlah dapat : "+detailLists.get(position).getDapat());
    }

    @Override
    public int getItemCount() {
        return detailLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_nm_tindakan,txt_pengolahan,txt_persentase,txt_dapat;
        public ViewHolder(View itemView) {
            super(itemView);
            txt_nm_tindakan = (TextView)itemView.findViewById(R.id.nm_tindakan);
            txt_pengolahan = (TextView)itemView.findViewById(R.id.pengolahan);
            txt_persentase = (TextView)itemView.findViewById(R.id.persentase_p);
            txt_dapat = (TextView)itemView.findViewById(R.id.dapat_p);

        }
    }
}
