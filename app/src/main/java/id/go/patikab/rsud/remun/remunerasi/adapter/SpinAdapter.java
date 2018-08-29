package id.go.patikab.rsud.remun.remunerasi.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import id.go.patikab.rsud.remun.remunerasi.AuthActivity;
import id.go.patikab.rsud.remun.remunerasi.entity.DataDokter;

public class SpinAdapter extends ArrayAdapter<DataDokter> {
    private Context context;
    private DataDokter[] dokters;
    private List<DataDokter> dokterList;

    public SpinAdapter(Context context, int textViewResid, DataDokter[] dokters1) {
        super(context,textViewResid,dokters1);
        this.context = context;
        this.dokters = dokters1;
    }

    public SpinAdapter(AuthActivity context, int simple_dropdown_item_1line, List<DataDokter> list) {
        super(context,simple_dropdown_item_1line,list);
        this.context = context;
        this.dokterList = list;
    }


    @Override
    public int getCount() {
        return dokters.length;
//        return dokterList.size();
    }

    @Nullable
    @Override
    public DataDokter getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
}

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView label = (TextView)super.getView(position,convertView,parent);
        label.setTextColor(Color.BLACK);
        label.setText(dokters[position].getNama_dokter());
        return label;

    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
          TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(dokters[position].getNama_dokter());

        return label;
    }
}

