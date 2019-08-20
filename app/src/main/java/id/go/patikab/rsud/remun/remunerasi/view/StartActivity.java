package id.go.patikab.rsud.remun.remunerasi.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.ButterKnife;

import id.go.patikab.rsud.remun.remunerasi.R;
import id.go.patikab.rsud.remun.remunerasi.view.Auth.AuthActivity;
import id.go.patikab.rsud.remun.remunerasi.view.Login.Login;
import id.go.patikab.rsud.remun.remunerasi.view.Daftar.Daftar;

import static id.go.patikab.rsud.remun.remunerasi.data.lokal.sharepreference.SharePref.*;

public class StartActivity extends AppCompatActivity {
    SharedPreferences preferences;
    Button btn_masuk,btn_daftar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        btn_masuk = (Button)findViewById(R.id.bt_masuk);
        btn_masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartActivity.this,Login.class));

            }
        });
        btn_daftar = (Button)findViewById(R.id.bt_daftar);
        btn_daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartActivity.this,Daftar.class));
            }
        });
        preferences = getSharedPreferences(pref, Context.MODE_PRIVATE);
        if (preferences.getString(username_device,"") != "") {

            startActivity(new Intent(StartActivity.this, MainApps.class));
            this.finish();

        }

    }
}
