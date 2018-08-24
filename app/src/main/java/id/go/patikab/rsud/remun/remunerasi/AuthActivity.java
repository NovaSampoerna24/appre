package id.go.patikab.rsud.remun.remunerasi;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.go.patikab.rsud.remun.remunerasi.adapter.SpinAdapter;
import id.go.patikab.rsud.remun.remunerasi.api.ApiClient;
import id.go.patikab.rsud.remun.remunerasi.api.ApiInterface;
import id.go.patikab.rsud.remun.remunerasi.database.DatabaseHandler;
import id.go.patikab.rsud.remun.remunerasi.database.model.DokterData;
import id.go.patikab.rsud.remun.remunerasi.entity.LoginResponse;
import id.go.patikab.rsud.remun.remunerasi.entity.ValDokter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static id.go.patikab.rsud.remun.remunerasi.database.sharepreference.SharePref.login_session;
import static id.go.patikab.rsud.remun.remunerasi.database.sharepreference.SharePref.my_token;
import static id.go.patikab.rsud.remun.remunerasi.database.sharepreference.SharePref.nm_dokter;
import static id.go.patikab.rsud.remun.remunerasi.database.sharepreference.SharePref.pref;

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
    String id_d = null,nama_dokter;
    DokterData[] dokterdatalogin;
    List<DokterData> dokterDataList = new ArrayList<DokterData>();

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
                finish();
            }
        });
        spinnerDokter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                DokterData dokterDataeLogin = (DokterData) adapterspin.getItem(i);
                id_d = dokterDataeLogin.getKode().toString();
                nama_dokter = dokterDataeLogin.getNama().toString();
//                Toast.makeText(AuthActivity.this, id_d + " ", Toast.LENGTH_SHORT).show();
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
                    if (TextUtils.isEmpty(id_d)) {
                        Toast.makeText(AuthActivity.this, "Pilih dokter terlebih dahulu !", Toast.LENGTH_SHORT).show();
                    }
//                    Log.d("test", "" + password.getText().toString().trim() + " " + spinnerDokter.getSelectedItem().toString().trim() + " " + id_d);

                } else {

                    progressDialog.setMessage("Login....");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    preferences = getSharedPreferences(pref, Context.MODE_PRIVATE);
                    String token = preferences.getString(my_token, null);
                    Log.d("token", token + " ");
                    if (isOnline() == true) {
                        signinsavetoken(id_d, ps, token,nama_dokter);
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(AuthActivity.this, "Periksa Koneksi jaringan anda !", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void initSpinnerDokter() {
        final DatabaseHandler db = DatabaseHandler.getInstance(AuthActivity.this);
        dokterDataList = db.getAllrecord();
        if (dokterDataList.size() > 0) {
            spinneradapterfromlokal();
            Log.d("sqlite","lokal");
        } else {
            progressDialog.setMessage("Mengambil data dokter");
            progressDialog.show();
            db.deleteAllrc();
            if (isOnline() == true) {
                try {
                    Call<ValDokter> call = apiInterface.getlistDokterlogin();
                    call.enqueue(new Callback<ValDokter>() {
                        @Override
                        public void onResponse(Call<ValDokter> call, Response<ValDokter> response) {
                            if (response.isSuccessful()) {
                                progressDialog.dismiss();
                                int total = response.body().getDokterList().size();
                                dokterdatalogin = new DokterData[total];

                                for (int i = 0; i < total; i++) {
                                    String namas = response.body().getDokterList().get(i).getNama_dokter();
                                    String kds = response.body().getDokterList().get(i).getKddokter();

                                    DokterData dokterData = new DokterData();
                                    dokterData.setKode(kds);
                                    dokterData.setNama(namas);
                                    db.addRecord(dokterData);
//                                    Log.d("nama", namas + " ");
                                }
                                spinneradapterfromlokal();
//                                Log.d("test", "tes");
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(AuthActivity.this, "Gagal mengambil data dokter !", Toast.LENGTH_SHORT).show();
                            }
                            Log.d("response", response.toString());
                        }

                        @Override
                        public void onFailure(Call<ValDokter> call, Throwable t) {
                            progressDialog.dismiss();
                            Toast.makeText(AuthActivity.this, "Tidak dapat menjangkau server", Toast.LENGTH_SHORT).show();
                            Log.d("messge", t.getMessage());
                        }
                    });
                } catch (Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(AuthActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("Exception", e.getMessage());
                }
            } else {
                progressDialog.dismiss();

                Toast.makeText(AuthActivity.this, "Periksa kembali koneksi jaringan anda !", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void spinneradapterfromlokal() {
        DatabaseHandler db = DatabaseHandler.getInstance(AuthActivity.this);
        dokterDataList = db.getAllrecord();
        DokterData[] dokterdata = new DokterData[dokterDataList.size()];
        for (int i = 0; i < dokterDataList.size(); i++) {

            String kd = dokterDataList.get(i).getKode().toString();
            String namah = dokterDataList.get(i).getNama().toString();
            int id = dokterDataList.get(i).getId();

            dokterdata[i] = new DokterData();
            dokterdata[i].setKode(kd);
            dokterdata[i].setNama(namah);

            Log.d("sqlite", kd + " " + namah + " " + id + " ");
        }
        spinnerDokter = (Spinner) findViewById(R.id.spin_dokter);
        adapterspin = new SpinAdapter(AuthActivity.this, android.R.layout.simple_dropdown_item_1line, dokterdata);
        spinnerDokter.setAdapter(adapterspin);
    }

    @Override
    protected void onStart() {
        super.onStart();
        preferences = getSharedPreferences(pref, Context.MODE_PRIVATE);
        if (!preferences.getString(login_session, "").equals("")) {
            startActivity(new Intent(AuthActivity.this, MainActivity.class));
            finish();
        } else {
//            if (String.valueOf(dataDokters).toString().trim() == "null") {
            initSpinnerDokter();
//                Log.d("sd", "sd");
        }
//            Log.d("obj", String.valueOf(dataDokters).toString().trim() + " ");
        String token = preferences.getString(my_token, null);
        Log.d("tokenmu", token + " ");
//        }
    }

    private void signinsavetoken(String id, String password, String token, final String nama_dokter) {

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
                                editor.putString(nm_dokter,nama_dokter);
                                editor.apply();
                                startActivity(new Intent(AuthActivity.this, MainActivity.class));
                                finish();
                            } else if (status.equals("failed")) {
                                progressDialog.dismiss();
                                Toast.makeText(AuthActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        }
                        if (!response.isSuccessful()) {
                            progressDialog.dismiss();
                            Toast.makeText(AuthActivity.this, "Periksa kembali jaringan anda !", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(AuthActivity.this, "Tidak dapat menjangkau server", Toast.LENGTH_SHORT).show();
                        Log.d("failure", t.getMessage());
                    }
                });
            } catch (Exception e) {
                progressDialog.dismiss();
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("Exception ", e.getMessage());
            }
//        }
//        else {
//
//        }
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
