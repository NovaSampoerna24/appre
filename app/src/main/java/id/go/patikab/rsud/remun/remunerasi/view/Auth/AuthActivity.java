package id.go.patikab.rsud.remun.remunerasi.view.Auth;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.go.patikab.rsud.remun.remunerasi.R;
import id.go.patikab.rsud.remun.remunerasi.view.MainApps;
import id.go.patikab.rsud.remun.remunerasi.view.Register.RegisterActivity;
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.DokterGetData;
import id.go.patikab.rsud.remun.remunerasi.data.lokal.DatabaseHandler;
import id.go.patikab.rsud.remun.remunerasi.data.lokal.object.DokterData;

import id.go.patikab.rsud.remun.remunerasi.config.adapter.SpinAdapter;
import id.go.patikab.rsud.remun.remunerasi.view.page_dialog.CustomDialogDetail;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static id.go.patikab.rsud.remun.remunerasi.data.lokal.sharepreference.SharePref.*;
import id.go.patikab.rsud.remun.remunerasi.data.api.ApiInterface;
import id.go.patikab.rsud.remun.remunerasi.data.api.ApiClient;
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.AuthResponse;
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.ServerResponse;

public class AuthActivity extends AppCompatActivity {
    EditText password;
    String id_d,
            nama_dokter,kd_user;
    Button loginButton, registerButton;

    ProgressDialog progressDialog;
    SharedPreferences preferences;
    ApiInterface apiInterface;

    SpinnerAdapter adapterspin;
    SwipeRefreshLayout swipe;

    DokterData[] dokterdatalogin;
    List<DokterData> dokterDataList = new ArrayList<DokterData>();
    @BindView(R.id.spin_dokter)
    Spinner spinnerDokter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        progressDialog = new ProgressDialog(this);
        ButterKnife.bind(this);
        //cek session
        preferences = getSharedPreferences(pref, Context.MODE_PRIVATE);
        kd_user = preferences.getString(login_session, "");
        String token = preferences.getString(my_token, null);
        if (kd_user == "") {
            initSpinnerDokter();
        } else {
            startActivity(new Intent(AuthActivity.this, MainApps.class));
            finish();
        }
//        Log.d("tokenmu", token + " ");


        password = (EditText) findViewById(R.id.passwordLogin);
        loginButton = (Button) findViewById(R.id.girisButton);
        registerButton = (Button) findViewById(R.id.registerButton);


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
                DokterData dokter = (DokterData) adapterspin.getItem(i);
                id_d = dokter.getKode().toString();
                nama_dokter = dokter.getNama().toString();
//                Toast.makeText(AuthActivity.this, id_d + " ", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        swipe = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipe.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        refresh();
                    }
                }
        );
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ps = password.getText().toString().trim();
                if (TextUtils.isEmpty(password.getText()) || TextUtils.isEmpty(id_d)) {
                    if (TextUtils.isEmpty(id_d)) {
                        AlertDialog.Builder ab = new AlertDialog.Builder(AuthActivity.this);
                        ab.setMessage("Plih dokterdefault terlebih dahulu, Jika list dokterdefault belum keluar , silahkan refresh kembali halaman  dengan menggeser kebawah");
                        ab.setCancelable(false);
                        ab.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                arg0.dismiss();
                            }
                        });
                        ab.show();
                    }
                    if (TextUtils.isEmpty(password.getText())) {
                        password.setError("Password belum diisi !");
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
                        signinsavetoken(id_d, ps, token, nama_dokter);
                    } else {
                        progressDialog.dismiss();
                        dialog_failure();
//                        Toast.makeText(AuthActivity.this, "Periksa Koneksi jaringan anda !", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void refresh() {
        DatabaseHandler db = DatabaseHandler.getInstance(AuthActivity.this);
        db.deleteListLogin();
        initSpinnerDokter();
        swipe.setRefreshing(false);
    }

    private void initSpinnerDokter() {
        progressDialog = new ProgressDialog(this);
        final DatabaseHandler db = DatabaseHandler.getInstance(AuthActivity.this);
        dokterDataList = db.getAllRecordListLogin();
        if (dokterDataList.size() > 0) {
            spinneradapterfromlokal();
//            Log.d("sqlite", "lokal");
        } else {
            progressDialog.setMessage("Mengambil data dokterdefault");
            progressDialog.show();
            db.deleteListLogin();
            if (isOnline() == true) {
                try {
                    Call<DokterGetData> call = apiInterface.getlistDokterlogin();
                    call.enqueue(new Callback<DokterGetData>() {
                        @Override
                        public void onResponse(Call<DokterGetData> call, Response<DokterGetData> response) {
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
                                    db.insertDokterListLogin(dokterData);
//                                    Log.d("nama", namas + " ");
                                }
                                spinneradapterfromlokal();
//                                Log.d("test", "tes");
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(AuthActivity.this, "Gagal mengambil data dokterdefault !", Toast.LENGTH_SHORT).show();
                            }
                            Log.d("response code ", response.code() +" -- ");
                        }

                        @Override
                        public void onFailure(Call<DokterGetData> call, Throwable t) {
                            progressDialog.dismiss();
                            dialog_failure();
//                            Toast.makeText(AuthActivity.this, "Tidak dapat menjangkau server", Toast.LENGTH_SHORT).show();
                            Log.d("messge", t.getMessage().toString() + " --");
                        }
                    });
                } catch (Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(AuthActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("Exception", e.getMessage().toString() + " --");
                }
            } else {
                progressDialog.dismiss();
                Toast.makeText(AuthActivity.this, "Periksa kembali koneksi jaringan anda !", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void spinneradapterfromlokal() {
        DatabaseHandler db = DatabaseHandler.getInstance(AuthActivity.this);
        dokterDataList = db.getAllRecordListLogin();
        DokterData[] dokterdata = new DokterData[dokterDataList.size()];
        for (int i = 0; i < dokterDataList.size(); i++) {

            String kd = dokterDataList.get(i).getKode().toString();
            String namah = dokterDataList.get(i).getNama().toString();
            int id = dokterDataList.get(i).getId();

            dokterdata[i] = new DokterData();
            dokterdata[i].setKode(kd);
            dokterdata[i].setNama(namah);
//            Log.d("sqlite", kd + " " + namah + " " + id + " ");
        }
        spinnerDokter = (Spinner) findViewById(R.id.spin_dokter);
        adapterspin = new SpinAdapter(AuthActivity.this, android.R.layout.simple_dropdown_item_1line, dokterdata);
        spinnerDokter.setAdapter(adapterspin);
    }

    private void dialog_failure() {
        CustomDialogDetail cdd = new CustomDialogDetail(AuthActivity.this);
        cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        cdd.show();
    }

    private void signinsavetoken(String id, String password, String token, final String nama_dokter) {
        try {
           Map mape = new HashMap<String,String>();
            mape.put("id_dokter",id);
            mape.put("device_token",token);
            mape.put("package_name",getApplicationContext().getPackageName().toString());
            Call<AuthResponse> call = apiInterface.getloginresponse(id, password, token ,mape);
            call.enqueue(new Callback<AuthResponse>() {
                @Override
                public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                    String status = response.body().getStatus().toString();
                    String message = response.body().getMessage().toString();
                    if (response.isSuccessful()) {
                        if (status.equals("ok")) {
                            String idm = response.body().getDataUser().get(0).getKddokter().toString();
                            String signaturee = response.body().getDataUser().get(0).getSignature().toString();
                            progressDialog.dismiss();
                            preferences = getSharedPreferences(pref, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString(login_session, idm);
                            editor.putString(nm_dokter, nama_dokter);
                            editor.putString(signature,signaturee);
                            editor.putString("psw", password);
                            editor.apply();
                            insert_profile(id_d, nama_dokter);
                            finish();
                            startActivity(new Intent(AuthActivity.this, MainApps.class));
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
                public void onFailure(Call<AuthResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    dialog_failure();
//                        Toast.makeText(AuthActivity.this, "Tidak dapat menjangkau server", Toast.LENGTH_SHORT).show();
                    Log.d("failure", t.getMessage().toString());
                }
            });
        } catch (Exception e) {
            progressDialog.dismiss();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d("Exception ", e.getMessage().toString() + " --");
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

    private void insert_profile(String id, String nm) {

        Call<ServerResponse> call = apiInterface.insert_profile(id, nm);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("tg", "Sukses insert profil");
                }

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.d("fail", t.getMessage().toString() + " --");
            }
        });
    }

}
