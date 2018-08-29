package id.go.patikab.rsud.remun.remunerasi;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.go.patikab.rsud.remun.remunerasi.api.ApiClient;
import id.go.patikab.rsud.remun.remunerasi.api.ApiInterface;
import id.go.patikab.rsud.remun.remunerasi.entity.valueDetailTindakan;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailTindakanActivity extends AppCompatActivity {
    ApiInterface apiInterface;
    @BindView(R.id.dt_gedung)
    TextView txt_gedung;
    @BindView(R.id.dt_pasien)
    TextView txtpasien;
    @BindView(R.id.dt_ttl)
    TextView txtttl;
    @BindView(R.id.dt_alamat)
    TextView txtalamat;
    @BindView(R.id.dt_ruang)
    TextView txtRuang;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        setContentView(R.layout.detail_tindakan);
        ButterKnife.bind(DetailTindakanActivity.this);
        getSupportActionBar().setTitle("Detail Tindakan");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        Intent in = getIntent();
        String id = in.getStringExtra("id");

        progressDialog = new ProgressDialog(DetailTindakanActivity.this);
        progressDialog.setMessage("Loading...");


        getDetailpasien(id);


    }

    private void getDetailpasien(String id) {
        progressDialog.show();
        try {
            if (isOnline() == true) {
                Call<valueDetailTindakan> call = apiInterface.getValueDetailTindakanCall(id);
                call.enqueue(new Callback<valueDetailTindakan>() {
                    @Override
                    public void onResponse(Call<valueDetailTindakan> call, Response<valueDetailTindakan> response) {
                        if (response.isSuccessful()) {
                            txtalamat.setText(response.body().getAlamat());
                            txt_gedung.setText(response.body().getNama_gedung());
                            txtpasien.setText(response.body().getNama_pasien());
                            txtRuang.setText(response.body().getRuang());
                            txtttl.setText(response.body().getTtl());
                            progressDialog.dismiss();
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(DetailTindakanActivity.this, "Tidak ada response", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<valueDetailTindakan> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(DetailTindakanActivity.this, "Tidak dapat menjangkau server", Toast.LENGTH_SHORT).show();
                    }
                });

            } else {
                progressDialog.dismiss();
                Toast.makeText(DetailTindakanActivity.this, "Periksa koneksi anda !", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e)

        {
            progressDialog.dismiss();
            Log.d("exception", e.getMessage());
        }

    }


    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        //jika ada koneksi return true
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        //jika tidak ada koneksi return false
        return false;
    }
}