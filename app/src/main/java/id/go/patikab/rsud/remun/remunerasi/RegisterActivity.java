package id.go.patikab.rsud.remun.remunerasi;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.ButterKnife;
import id.go.patikab.rsud.remun.remunerasi.api.ApiClient;
import id.go.patikab.rsud.remun.remunerasi.api.ApiInterface;
import id.go.patikab.rsud.remun.remunerasi.entity.RegisterResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static id.go.patikab.rsud.remun.remunerasi.firebase.MyFirebaseInstanceIdService.my_token;
import static id.go.patikab.rsud.remun.remunerasi.firebase.MyFirebaseInstanceIdService.pref;

public class RegisterActivity extends AppCompatActivity {
    EditText  password, device_token, repassword;
    Button registerButton, loginButton;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    SharedPreferences preferences;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        progressDialog = new ProgressDialog(RegisterActivity.this);

        password = (EditText) findViewById(R.id.uyeParola);
        registerButton = (Button) findViewById(R.id.yeniUyeButton);
        loginButton = (Button) findViewById(R.id.uyeGirisButton);
        repassword = (EditText) findViewById(R.id.passwordulangi);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        firebaseAuth = FirebaseAuth.getInstance();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String passworde = password.getText().toString();
                String passwordulang = repassword.getText().toString();

                if ( passworde == null || passworde.length() < 6 || passwordulang == null || !TextUtils.equals(passworde, passwordulang)) {

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
//                    saveToServer(username, passworde, passwordulang);
                }
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AuthActivity.class));
            }
        });
        if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
    }

//    private void saveToServer(String nama_d, String emaile, String passworde, String passwordulang) {
//        preferences = getSharedPreferences(pref, Context.MODE_PRIVATE);
//        String token = preferences.getString(my_token, null);
//        try {
//            Call<RegisterResponse> call = apiInterface.getresponse(nama_d, emaile, passworde, passwordulang, token);
//            call.enqueue(new Callback<RegisterResponse>() {
//                @Override
//                public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
//                    String message = response.body().getStatus();
//                    Log.d("message :", message);
//                }
//
//                @Override
//                public void onFailure(Call<RegisterResponse> call, Throwable t) {
//                    Log.d("trowable : ", t.getMessage());
//                    progressDialog.dismiss();
//                }
//            });
//        } catch (Exception e) {
//            Log.d("message err : ", e.getMessage());
//        }
//    }
}
