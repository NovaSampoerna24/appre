package id.go.patikab.rsud.remun.remunerasi;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;


import com.google.gson.JsonObject;
import com.google.gson.internal.bind.DateTypeAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.go.patikab.rsud.remun.remunerasi.adapter.SpinAdapter;
import id.go.patikab.rsud.remun.remunerasi.api.ApiClient;
import id.go.patikab.rsud.remun.remunerasi.api.ApiInterface;
import id.go.patikab.rsud.remun.remunerasi.entity.DataDokter;
import id.go.patikab.rsud.remun.remunerasi.entity.LoginResponse;
import id.go.patikab.rsud.remun.remunerasi.entity.ValDokter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static id.go.patikab.rsud.remun.remunerasi.firebase.MyFirebaseInstanceIdService.login_session;
import static id.go.patikab.rsud.remun.remunerasi.firebase.MyFirebaseInstanceIdService.my_token;
import static id.go.patikab.rsud.remun.remunerasi.firebase.MyFirebaseInstanceIdService.pref;

public class AuthActivity extends AppCompatActivity {
    EditText username, password;
    Button loginButton, registerButton;

    ProgressDialog progressDialog;
    SharedPreferences preferences;
    ApiInterface apiInterface;
    @BindView(R.id.spin_dokter)
    Spinner spinnerDokter;
    SpinnerAdapter adapterspin;
    Context context;
    String id_d = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        ButterKnife.bind(this);

        progressDialog = new ProgressDialog(AuthActivity.this);

        password = (EditText) findViewById(R.id.passwordLogin);
        loginButton = (Button) findViewById(R.id.girisButton);
        registerButton = (Button) findViewById(R.id.registerButton);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AuthActivity.this, RegisterActivity.class));
            }
        });
        spinnerDokter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                DataDokter dataDokter = (DataDokter) adapterspin.getItem(i);
                id_d = dataDokter.getKddokter().toString();
//                Toast.makeText(AuthActivity.this, dataDokter.getKddokter(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ps = password.getText().toString().trim();
                if (TextUtils.isEmpty(password.getText()) || TextUtils.isEmpty(id_d)) {
                    if (TextUtils.isEmpty(password.getText())) {
                        password.setError("Password belum diisi !");
                    }
                    if ( TextUtils.isEmpty(id_d)) {
                        Toast.makeText(context, "Pilih dokter terlebih dahulu !", Toast.LENGTH_SHORT).show();
                    }
                    Log.d("test", "" + password.getText().toString().trim() + " " + spinnerDokter.getSelectedItem().toString().trim() + " " + id_d);

                } else {

                    progressDialog.setMessage("Login....");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    preferences = getSharedPreferences(pref, Context.MODE_PRIVATE);
                    String token = preferences.getString(my_token, null);
                    Log.d("token", token + " ");
                    signinsavetoken(id_d, ps, token);
                }
            }
        });
    }

    public void initSpinnerDokter() {
        progressDialog.setMessage("Mengambil data dokter");
        progressDialog.show();
        if (isOnline() == true) {
            try {
                Call<ValDokter> call = apiInterface.getlistDokterlogin();
                call.enqueue(new Callback<ValDokter>() {
                    @Override
                    public void onResponse(Call<ValDokter> call, Response<ValDokter> response) {
                        if (response.isSuccessful()) {
                            progressDialog.dismiss();
                            List<String> nama = new ArrayList<String>();
                            List<DataDokter> list = new ArrayList<DataDokter>();
                            int total = response.body().getDokterList().size();
                            DataDokter[] dataDokters = new DataDokter[total];

                            for (int i = 0; i < total; i++) {
                                String namas = response.body().getDokterList().get(i).getNama_dokter();
                                String kds = response.body().getDokterList().get(i).getKddokter();
                                dataDokters[i] = new DataDokter();
                                dataDokters[i].setKddokter(kds);
                                dataDokters[i].setNama_dokter(namas);

                            }

                            spinnerDokter = (Spinner) findViewById(R.id.spin_dokter);
                            adapterspin = new SpinAdapter(AuthActivity.this, android.R.layout.simple_dropdown_item_1line, dataDokters);
                            spinnerDokter.setAdapter(adapterspin);

                            Log.d("test", "tes");
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(AuthActivity.this, "Gagal mengambil data dokter !", Toast.LENGTH_SHORT).show();
                        }
                        Log.d("response", response.toString());
                    }

                    @Override
                    public void onFailure(Call<ValDokter> call, Throwable t) {
                        Log.d("messge", t.getMessage());
                    }
                });
            } catch (Exception e) {
                Log.d("Exception", e.getMessage());
            }

        } else {
            Toast.makeText(context, "Periksa kembali koneksi jaringan anda !", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        preferences = getSharedPreferences(pref, Context.MODE_PRIVATE);
        if (preferences.contains(login_session)) {
            startActivity(new Intent(AuthActivity.this, MainActivity.class));
            finish();
        }
        String token = preferences.getString(my_token, null);
        Log.d("tokenmu", token + " ");
        initSpinnerDokter();
    }

    private void signinsavetoken(String id, String password, String token) {
        if (isOnline() == true) {
            try {
                Call<LoginResponse> call = apiInterface.getloginresponse(id, password, token);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        String status = response.body().getStatus().toString();
                        String message = response.body().getMessage().toString();
                        if (response.isSuccessful()) {
                            if (status.equals("ok")) {
                                progressDialog.dismiss();
                                preferences = getSharedPreferences(pref, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString(login_session, response.body().getDataUser().get(0).getKDDOKTER());
                                editor.apply();
                                startActivity(new Intent(AuthActivity.this, MainActivity.class));
                                finish();
                            } else if (status.equals("failed")) {
                                progressDialog.dismiss();
                                Toast.makeText(AuthActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        }
                        if (!response.isSuccessful()) {
                            Toast.makeText(AuthActivity.this, "Periksa kembali jaringan anda !", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Log.d("failure", t.getMessage());
                    }
                });
            } catch (Exception e) {
                Log.d("Exception ", e.getMessage());
            }
        } else {
            Toast.makeText(this, "Periksa Koneksi jaringan anda !", Toast.LENGTH_SHORT).show();
        }
    }

    //method untuk cek koneksi
    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        //jika ada koneksi return true
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        //jika tidak ada koneksi return false
        return false;
    }
}
