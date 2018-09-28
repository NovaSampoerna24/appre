package id.go.patikab.rsud.remun.remunerasi.config.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import id.go.patikab.rsud.remun.remunerasi.view.DetailTindakan.DetailTindakanActivity;
import id.go.patikab.rsud.remun.remunerasi.R;
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.DetailTindakan;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ViewHolder> {
    List<DetailTindakan> detailTindakanList;
    int count = 0;
    Context context;


    public DetailAdapter(List<DetailTindakan> detailTindakans, int counte, Context context) {
        this.context = context;
        this.detailTindakanList = detailTindakans;
        if (counte < detailTindakans.size()) {
            this.count = counte;
        } else {
            this.count = detailTindakans.size();
            Toast.makeText(context, "Semua data sudah tampil !", Toast.LENGTH_SHORT).show();
        }
        Log.d("sd", detailTindakans.size() + " sd " + count);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.txt_nm_tindakan.setText( detailTindakanList.get(position).getTindakan());
        holder.tanggal.setText(detailTindakanList.get(position).getTanggal());
        holder.tarif.setText( detailTindakanList.get(position).getTarif());
        holder.tr_jl1.setText( detailTindakanList.get(position).getTarif_jl1());
        holder.jp_sebelum_proporsi.setText(detailTindakanList.get(position).getJp_kotor());
        holder.jp_sesudah_proporsi.setText(detailTindakanList.get(position).getTarif_setelah());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                CustomDialogDetail cdd = new CustomDialogDetail(context,detailTindakanList.get(position));
//                cdd.show();
                Intent i = new Intent(context, DetailTindakanActivity.class);
                i.putExtra("id",detailTindakanList.get(position).getId());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return count;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_nm_tindakan, tanggal, tarif, tr_jl1, jp_sebelum_proporsi, jp_sesudah_proporsi, tr_jtl2;

        public ViewHolder(View itemView) {
            super(itemView);
            txt_nm_tindakan = (TextView) itemView.findViewById(R.id.nm_tindakan);
            tanggal = (TextView) itemView.findViewById(R.id.tanggal);
            tarif = (TextView) itemView.findViewById(R.id.tarif);
            tr_jl1 = (TextView) itemView.findViewById(R.id.tarif_jl1);
            jp_sebelum_proporsi = (TextView) itemView.findViewById(R.id.jp_kotor);
            jp_sesudah_proporsi = (TextView)itemView.findViewById(R.id.jp_sesudah_proporsi);
//            tr_jtl1 = (TextView) itemView.findViewById(R.id.tarif_jtl1);
//            tr_jtl2 = (TextView) itemView.findViewById(R.id.tarif_jtl2);
        }
    }
}
