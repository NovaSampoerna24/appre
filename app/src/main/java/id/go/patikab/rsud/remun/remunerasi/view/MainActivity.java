package id.go.patikab.rsud.remun.remunerasi.view;


import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import butterknife.ButterKnife;
import id.go.patikab.rsud.remun.remunerasi.R;
import id.go.patikab.rsud.remun.remunerasi.config.util.ActivityIntentKt;
import id.go.patikab.rsud.remun.remunerasi.view.Auth.AuthActivity;
import id.go.patikab.rsud.remun.remunerasi.view.PembayaranDokter.PembayaranFragment;
import id.go.patikab.rsud.remun.remunerasi.view.profil.*;

import static id.go.patikab.rsud.remun.remunerasi.data.lokal.sharepreference.SharePref.*;
public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    String kd_user;
    Toolbar toolbar;

    FragmentManager fragmentManager;

    DrawerLayout drawer;
    NavigationView navigationView;
    SharedPreferences sharedPreferences;

    AkunFragment fragmentprofil;
    PembayaranFragment fragmentpembayaran;
    ProfilFragment profilFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        butterknife bind
        ButterKnife.bind(this);
//        find id
        findViewById();
//        toolbar
        setSupportActionBar(toolbar);
//        cek session
        sharedPreferences = getSharedPreferences(pref, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(login_session, "").equals("")) {
            startActivity(new Intent(MainActivity.this, AuthActivity.class));
            Log.d("tokene", sharedPreferences.getString(my_token, null) + " ");
            finish();
        }
//        navigationView drawer
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        sharedPreferences = getSharedPreferences(pref, Context.MODE_PRIVATE);
//        Log.d("tokene", sharedPreferences.getString(my_token, null) + " ");
        kd_user = sharedPreferences.getString(login_session, null);
//        loadProfile();
        fragmentprofil = new AkunFragment();
        fragmentpembayaran = new PembayaranFragment();
        profilFragment = new ProfilFragment();

        fragmentManager = getSupportFragmentManager();

        if(savedInstanceState == null){
            fragmentManager.beginTransaction()
                    .replace(R.id.flContent,profilFragment )
                    .commit();
        }
    }

    private void findViewById() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

//        check apakah menu sudah diklik
        if (!item.isChecked()) {
            item.setCheckable(true);
        }
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (item.getItemId()) {

            case R.id.nav_profile:
                fragmentManager.beginTransaction()
                        .replace(R.id.flContent,profilFragment)
                        .commit();
                break;

            case R.id.nav_remune:
                fragmentManager.beginTransaction()
                        .replace(R.id.flContent, fragmentpembayaran)
                        .commit();
                break;
//          case R.id.nav_jadwal:
//          break;
            default:
                break;
        }

        if (id == R.id.nav_logout) {
            Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
            ActivityIntentKt.logout(this);
            finish();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }


}