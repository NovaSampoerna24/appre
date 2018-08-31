package id.go.patikab.rsud.remun.remunerasi;

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
import android.support.annotation.NonNull;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.go.patikab.rsud.remun.remunerasi.adapter.SpinAdapter;
import id.go.patikab.rsud.remun.remunerasi.api.ApiClient;
import id.go.patikab.rsud.remun.remunerasi.api.ApiInterface;
import id.go.patikab.rsud.remun.remunerasi.database.DatabaseHandler;
import id.go.patikab.rsud.remun.remunerasi.database.model.DokterData;
import id.go.patikab.rsud.remun.remunerasi.entity.DataDokter;
import id.go.patikab.rsud.remun.remunerasi.entity.RegisterResponse;
import id.go.patikab.rsud.remun.remunerasi.entity.ValDokter;


import static id.go.patikab.rsud.remun.remunerasi.database.sharepreference.SharePref.login_session;
import static id.go.patikab.rsud.remun.remunerasi.database.sharepreference.SharePref.my_token;
import static id.go.patikab.rsud.remun.remunerasi.database.sharepreference.SharePref.nm_dokter;
import static id.go.patikab.rsud.remun.remunerasi.database.sharepreference.SharePref.pref;

import id.go.patikab.rsud.remun.remunerasi.AuthActivity;
import id.go.patikab.rsud.remun.remunerasi.page_dialog.CustomDialogDetail;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    String id_d, nama_dokter;
    EditText password, repassword;
    Button registerButton, loginButton;

    ProgressDialog progressDialog;

    SharedPreferences preferences;
    ApiInterface apiInterface;

    SpinnerAdapter adapterspin;
    DokterData[] dokterdataregister;
    List<DokterData> dokterDataList = new ArrayList<DokterData>();
    @BindView(R.id.spin_dokter)
    Spinner spinnerDokter;

    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        progressDialog = new ProgressDialog(RegisterActivity.this);

        password = (EditText) findViewById(R.id.uyeParola);
        registerButton = (Button) findViewById(R.id.yeniUyeButton);
        loginButton = (Button) findViewById(R.id.uyeGirisButton);
        repassword = (EditText) findViewById(R.id.passwordulangi);
        spinnerDokter = findViewById(R.id.spin_dokter);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        spinnerDokter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                DokterData dokterDataeLogin = (DokterData) adapterspin.getItem(i);
                id_d = dokterDataeLogin.getKode().toString();
                nama_dokter = dokterDataeLogin.getNama().toString();
//                Toast.makeText(RegisterActivity.this, id_d + " ", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String passworde = password.getText().toString();
                String passwordulang = repassword.getText().toString();

                if (TextUtils.isEmpty(id_d) || passworde == null || passworde.length() < 6 || passwordulang == null || !TextUtils.equals(passworde, passwordulang)) {
                    if (TextUtils.isEmpty(id_d)) {
                        AlertDialog.Builder ab = new AlertDialog.Builder(RegisterActivity.this);
                        ab.setMessage("Plih dokter terlebih dahulu, Jika list dokter belum keluar , silahkan refresh kembali halaman  dengan menggeser kebawah");
                        ab.setCancelable(false);
                        ab.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                arg0.dismiss();
                            }
                        });
                        ab.show();
                    }
                    if (TextUtils.isEmpty(passworde)) {
                        password.setError("Password belum diisi !");
                    }
                    if (passwordulang == null) {
                        repassword.setError("Ulangi password disini !");
                    }
                    if (passworde.length() < 6) {
                        password.setError("Password harus lebih dari 6 karakter");
                    }
                    if (!TextUtils.equals(passworde, passwordulang)) {
                        repassword.setError("Password tidak sama !");
                    }

                } else {
                    progressDialog.setMessage("Register....");
                    progressDialog.show();
                    if (isOnline() == true) {
                        saveToServer(id_d, passworde, passwordulang, nama_dokter);
                    } else {
                        progressDialog.dismiss();
                        dialog_failure();
//                        Toast.makeText(RegisterActivity.this, "Periksa kembali koneksi jaringan anda !", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AuthActivity.class));
                finish();
            }
        });
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        refresh();
                    }
                }
        );
    }

    private void refresh() {
        initSpinnerDokterregister();
        swipeRefreshLayout.setRefreshing(false);
    }

    public void initSpinnerDokterregister() {
        final DatabaseHandler db = DatabaseHandler.getInstance(RegisterActivity.this);
        dokterDataList = db.getAllrecord2();
        if (dokterDataList.size() > 0) {
            spinneradapterfromlokal2();
            Log.d("sqlite", "lokal");
        } else {
            progressDialog.setMessage("Mengambil data dokter ...");
            progressDialog.show();
            if (isOnline() == true) {
                Call<ValDokter> call = apiInterface.getDokter();
                call.enqueue(new Callback<ValDokter>() {
                    @Override
                    public void onResponse(Call<ValDokter> call, Response<ValDokter> response) {
                        if (response.isSuccessful()) {
                            progressDialog.dismiss();
                            int total = response.body().getDokterList().size();
                            dokterdataregister = new DokterData[total];

                            for (int i = 0; i < total; i++) {
                                String namas = response.body().getDokterList().get(i).getNama_dokter();
                                String kds = response.body().getDokterList().get(i).getKddokter();

                                DokterData dokterData = new DokterData();
                                dokterData.setKode(kds);
                                dokterData.setNama(namas);
                                db.addRecord2(dokterData);
//                                Log.d("nama", namas + " ");
                            }
                            spinneradapterfromlokal2();
//                            Log.d("test", "tes");
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, "Gagal mengambil data dokter !", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ValDokter> call, Throwable t) {
                        dialog_failure();
                    }
                });

            } else {
                dialog_failure();
//                Toast.makeText(RegisterActivity.this, "Periksa kembali koneksi jaringan anda !", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void spinneradapterfromlokal2() {
        final DatabaseHandler db = DatabaseHandler.getInstance(RegisterActivity.this);
        List<DokterData> dokterDataList;
        dokterDataList = db.getAllrecord2();
        DokterData[] dokterdata = new DokterData[dokterDataList.size()];
        for (int i = 0; i < dokterDataList.size(); i++) {

            String kd = dokterDataList.get(i).getKode().toString();
            String namah = dokterDataList.get(i).getNama().toString();
            int id = dokterDataList.get(i).getId();

            dokterdata[i] = new DokterData();
            dokterdata[i].setKode(kd);
            dokterdata[i].setNama(namah);

        }
        spinnerDokter = (Spinner) findViewById(R.id.spin_dokter);
        adapterspin = new SpinAdapter(RegisterActivity.this, android.R.layout.simple_dropdown_item_1line, dokterdata);
        spinnerDokter.setAdapter(adapterspin);
    }

    private void saveToServer(String id_dokter, String passworde, String passwordulang, final String nm_dk) {

        preferences = getSharedPreferences(pref, Context.MODE_PRIVATE);
        String token_d = preferences.getString(my_token, null);

        try {
            Call<RegisterResponse> call = apiInterface.getresponse(id_dokter, passworde, token_d, passwordulang);
            call.enqueue(new Callback<RegisterResponse>() {
                @Override
                public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                    String status = response.body().getStatus();
                    String message = response.body().getMessage();
                    if (response.isSuccessful()) {
                        if (status.equals("ok")) {
                            preferences = getSharedPreferences(pref, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString(login_session, response.body().getDataUser().get(0).getKDDOKTER());
                            editor.putString(nm_dokter, nm_dk);
                            editor.apply();
                            progressDialog.dismiss();
                            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                            finish();
                            deleterecordlokal();
                        } else if (status.equals("failed")) {
                            progressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                    }
                    Log.d("message :", message);
                }

                @Override
                public void onFailure(Call<RegisterResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    dialog_failure();
//                    Toast.makeText(RegisterActivity.this, "Tidak dapat menjangkau server", Toast.LENGTH_SHORT).show();
                    Log.d("trowable : ", t.getMessage());
                }
            });
        } catch (Exception e) {
            progressDialog.dismiss();
            dialog_failure();
//            Toast.makeText(RegisterActivity.this, "Exception to connect", Toast.LENGTH_SHORT).show();
            Log.d("message err : ", e.getMessage());
        }
    }

    private void deleterecordlokal() {
        DatabaseHandler db = DatabaseHandler.getInstance(RegisterActivity.this);
        db.deleteAllrc();
        db.deleteAllrc2();
    }

    @Override
    protected void onStart() {
        super.onStart();
        preferences = getSharedPreferences(pref, Context.MODE_PRIVATE);
        if (!preferences.getString(login_session, "").equals("")) {
            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            finish();
        } else {
            initSpinnerDokterregister();
        }
    }

    private void dialog_failure() {
        CustomDialogDetail cdd = new CustomDialogDetail(RegisterActivity.this);
        cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        cdd.show();
    }

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
