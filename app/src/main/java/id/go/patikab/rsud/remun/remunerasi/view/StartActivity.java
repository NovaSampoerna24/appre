package id.go.patikab.rsud.remun.remunerasi.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import id.go.patikab.rsud.remun.remunerasi.R;
import id.go.patikab.rsud.remun.remunerasi.view.Auth.AuthActivity;
import id.go.patikab.rsud.remun.remunerasi.view.Register.RegisterActivity;

import static id.go.patikab.rsud.remun.remunerasi.data.lokal.sharepreference.SharePref.login_session;
import static id.go.patikab.rsud.remun.remunerasi.data.lokal.sharepreference.SharePref.pref;

public class StartActivity extends AppCompatActivity {
    SharedPreferences preferences;
    @OnClick(R.id.bt_masuk)
    public void btmasuk(){
        startActivity(new Intent(StartActivity.this,AuthActivity.class));

    }
    @OnClick(R.id.bt_daftar)
    public void btdaftar(){
        startActivity(new Intent(StartActivity.this,RegisterActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);

        preferences = getSharedPreferences(pref, Context.MODE_PRIVATE);
        if (!preferences.getString(login_session,"").equals("")) {
            startActivity(new Intent(StartActivity.this, MainActivity.class));
            finish();
        }
    }
}