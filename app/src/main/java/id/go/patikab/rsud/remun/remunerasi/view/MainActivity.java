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
import id.go.patikab.rsud.remun.remunerasi.data.lokal.DbInterface;
import id.go.patikab.rsud.remun.remunerasi.view.Auth.AuthActivity;
import id.go.patikab.rsud.remun.remunerasi.view.PembayaranDokter.PembayaranFragment;
import id.go.patikab.rsud.remun.remunerasi.view.profile.ProfileFragment;

import static id.go.patikab.rsud.remun.remunerasi.data.lokal.sharepreference.SharePref.login_session;
import static id.go.patikab.rsud.remun.remunerasi.data.lokal.sharepreference.SharePref.my_token;
import static id.go.patikab.rsud.remun.remunerasi.data.lokal.sharepreference.SharePref.pref;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    String kd_user;
    Toolbar toolbar;

    DbInterface mDb;

    DrawerLayout drawer;
    NavigationView navigationView;
    SharedPreferences sharedPreferences;
    FragmentManager fragmentManager;

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

        loadProfile();

    }
//    private void insertInformasi(){
//
//        Informasi in= new Informasi();
//        in.setId_dokter(kd_user);
//        in.setJudul("Periksa pembayaran terbaru anda ! ");
//        in.setDeskripsi("Dapatkan informasi pembayaran dengan aplikasi remunerasi");
//        in.setLabel("informasi");
//
//        mDb.addtoInformasi(in);
//
//}

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
//        displaySelectedScreen(item.getItemId());
        switch (item.getItemId()) {
            case R.id.nav_remune:
                loadPembayaran();
                break;

            case R.id.nav_profile:
                loadProfile();
                break;
            case R.id.nav_ganti_password:
                ActivityIntentKt.opengantiPassword(this,kd_user);
                break;
            default:
                loadProfile();
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

    private void loadPembayaran() {
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.flContent, new PembayaranFragment())
                .commit();
    }

    private void loadProfile() {
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.flContent, new ProfileFragment())
                .commit();
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }


}