package id.go.patikab.rsud.remun.remunerasi;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;


import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
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
    Context context;

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
        initSpinnerDokter();
//        spinnerDokter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                String selectName = adapterView.getItemAtPosition(i).toString();
//                Toast.makeText(AuthActivity.this, "Kamu memilih dokter " + selectName, Toast.LENGTH_SHORT).show();
//            }
//        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (TextUtils.isEmpty(password.getText())) {

                    if (TextUtils.isEmpty(password.getText())) {
                        password.setError("Password belum diisi !");
                    }
                } else {

                    progressDialog.setMessage("Login....");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    preferences = getSharedPreferences(pref, Context.MODE_PRIVATE);
                    String token = "";
                    token = preferences.getString(my_token, null);
//                    signinsavetoken(username,password,token);
                }
            }
        });
    }

    private void initSpinnerDokter() {
        progressDialog.show();

        Call<ValDokter> call = apiInterface.getDokter();
        call.enqueue(new Callback<ValDokter>() {
            @Override
            public void onResponse(Call<ValDokter> call, Response<ValDokter> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    List<String> nama = new ArrayList<String>();
                    List<DataDokter> list = new ArrayList<DataDokter>();


                    for (int i = 0; i < response.body().getDokterList().size(); i++) {
                        String namas = response.body().getDokterList().get(i).getNama_dokter();
                        String kds = response.body().getDokterList().get(i).getKddokter();
//                       list.add(new DataDokter("",""));
                        nama.add(namas + "-" + kds);


//                    ArrayMap<String,String> adapter = new ArrayMap<String, String>(AuthActivity.this,android.R.layout.simple_dropdown_item_1line,list)
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(AuthActivity.this, android.R.layout.simple_spinner_dropdown_item, nama);
//                        ArrayAdapter<DataDokter> adapter = new ArrayAdapter<DataDokter>(AuthActivity.this, android.R.layout.simple_list_item_1, list);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerDokter.setAdapter(adapter);

                        Log.d("test", "tes");
                    }
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(AuthActivity.this, "Gagal mengambil data dokter !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ValDokter> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("messge", t.getMessage());
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        preferences = getSharedPreferences(pref, Context.MODE_PRIVATE);
        if (preferences.contains(login_session)) {
            startActivity(new Intent(AuthActivity.this, MainActivity.class));
        }

    }

    private void signinsavetoken(String username, String password, String token) {
        try {
            Call<LoginResponse> call = apiInterface.getloginresponse(username, password, token);
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus().equals("ok")) {
                            preferences = getSharedPreferences(pref, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString(login_session, response.body().getDataUser().get(0).getId());
                            editor.apply();
                            startActivity(new Intent(AuthActivity.this, MainActivity.class));
                        }
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Log.d("failur", t.getMessage());
                }
            });
        } catch (Exception e) {
            Log.d("Exception ", e.getMessage());
        }
    }
}
