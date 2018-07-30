package id.go.patikab.rsud.remun.remunerasi;

import android.content.Intent;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    EditText email, password;
    Button registerButton, loginButton;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = (EditText) findViewById(R.id.uyeEmail);
        password = (EditText) findViewById(R.id.uyeParola);
        registerButton = (Button) findViewById(R.id.yeniUyeButton);
        loginButton = (Button) findViewById(R.id.uyeGirisButton);

        firebaseAuth = FirebaseAuth.getInstance();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emaile = email.getText().toString().trim();
                String passworde = password.getText().toString().trim();

                if (emaile == null || passworde == null || passworde.length() < 6) {

                    if (TextUtils.isEmpty(emaile)) {
                        email.setError("Email belum diisi !");
                    }
                    if (TextUtils.isEmpty(passworde)) {
                        password.setError("Password belum diisi !");
                    }
                    if (passworde.length() < 6) {
                        password.setError("Password harus lebih dari 6 karakter");
                    }
                    Log.d("test","tes");
                } else {
                    Log.d("create",emaile+" "+passworde);
                    createAccount(emaile, passworde);
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

    private void createAccount(String emaile, String passworde) {
        firebaseAuth.createUserWithEmailAndPassword(emaile, passworde)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                            Log.d("test", "test");
                        } else {
                            Toast.makeText(getApplicationContext(), "Format email tidak sesuai", Toast.LENGTH_SHORT).show();
                            Log.d("test1", "test1");
                        }
                    }
                });
    }
}
