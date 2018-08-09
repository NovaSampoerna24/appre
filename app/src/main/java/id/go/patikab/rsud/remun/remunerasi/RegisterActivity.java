package id.go.patikab.rsud.remun.remunerasi;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import com.google.android.gms.common.api.Api;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.go.patikab.rsud.remun.remunerasi.adapter.SpinAdapter;
import id.go.patikab.rsud.remun.remunerasi.api.ApiClient;
import id.go.patikab.rsud.remun.remunerasi.api.ApiInterface;
import id.go.patikab.rsud.remun.remunerasi.entity.DataDokter;
import id.go.patikab.rsud.remun.remunerasi.entity.RegisterResponse;
import id.go.patikab.rsud.remun.remunerasi.entity.ValDokter;


import static id.go.patikab.rsud.remun.remunerasi.firebase.MyFirebaseInstanceIdService.login_session;
import static id.go.patikab.rsud.remun.remunerasi.firebase.MyFirebaseInstanceIdService.my_token;
import static id.go.patikab.rsud.remun.remunerasi.firebase.MyFirebaseInstanceIdService.pref;

import id.go.patikab.rsud.remun.remunerasi.AuthActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    EditText password, device_token, repassword;
    Button registerButton, loginButton;
    ProgressDialog progressDialog;
    SharedPreferences preferences;
    ApiInterface apiInterface;
    @BindView(R.id.spin_dokter)
    Spinner spinnerDokter;
    SpinnerAdapter adapterspin;
    Context context;
    String id_d;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        preferences = getSharedPreferences(pref, Context.MODE_PRIVATE);
        if (preferences.contains(login_session)) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        progressDialog = new ProgressDialog(RegisterActivity.this);

        password = (EditText) findViewById(R.id.uyeParola);
        registerButton = (Button) findViewById(R.id.yeniUyeButton);
        loginButton = (Button) findViewById(R.id.uyeGirisButton);
        repassword = (EditText) findViewById(R.id.passwordulangi);
        spinnerDokter = findViewById(R.id.spin_dokter);

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


        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        initSpinnerDokterregister();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String passworde = password.getText().toString();
                String passwordulang = repassword.getText().toString();

                if (passworde == null || passworde.length() < 6 || passwordulang == null || !TextUtils.equals(passworde, passwordulang)) {

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
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    saveToServer(id_d, passworde, passwordulang);
                }
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AuthActivity.class));
            }
        });
    }

    public void initSpinnerDokterregister(){
        progressDialog.show();
        if (isOnline() == true) {
            Call<ValDokter> call = apiInterface.getDokter();
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
                        adapterspin = new SpinAdapter(RegisterActivity.this, android.R.layout.simple_dropdown_item_1line, dataDokters);
                        spinnerDokter.setAdapter(adapterspin);

                        Log.d("test", "tes");
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, "Gagal mengambil data dokter !", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ValDokter> call, Throwable t) {

                }
            });

        }else{
            Toast.makeText(context, "Periksa kembali koneksi jaringan anda !", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveToServer(String id_dokter, String passworde, String passwordulang) {
        progressDialog.dismiss();
        preferences = getSharedPreferences(pref, Context.MODE_PRIVATE);
        String token_d = preferences.getString(my_token, null);
        if (isOnline() == true) {
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
                                editor.apply();

                                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                finish();
                            } else if (status.equals("failed")) {
                                Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        }
                        Log.d("message :", message);
                    }

                    @Override
                    public void onFailure(Call<RegisterResponse> call, Throwable t) {
                        Log.d("trowable : ", t.getMessage());
                        progressDialog.dismiss();
                    }
                });
            } catch (Exception e) {
                Log.d("message err : ", e.getMessage());
            }

        } else {
            Toast.makeText(context, "Periksa kembali koneksi jaringan anda !", Toast.LENGTH_SHORT).show();
        }

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
