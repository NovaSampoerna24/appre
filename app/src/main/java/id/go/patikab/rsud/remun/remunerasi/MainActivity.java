package id.go.patikab.rsud.remun.remunerasi;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import butterknife.ButterKnife;
import id.go.patikab.rsud.remun.remunerasi.api.ApiInterface;
import id.go.patikab.rsud.remun.remunerasi.entity.LoginResponse;
import id.go.patikab.rsud.remun.remunerasi.fragment.JadwalFragment;
import id.go.patikab.rsud.remun.remunerasi.fragment.PembayaranFragment;
import id.go.patikab.rsud.remun.remunerasi.fragment.ProfileFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static id.go.patikab.rsud.remun.remunerasi.database.sharepreference.SharePref.login_session;
import static id.go.patikab.rsud.remun.remunerasi.database.sharepreference.SharePref.my_token;
import static id.go.patikab.rsud.remun.remunerasi.database.sharepreference.SharePref.nm_dokter;
import static id.go.patikab.rsud.remun.remunerasi.database.sharepreference.SharePref.pref;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    String kd_user;
    Toolbar toolbar;

    DrawerLayout drawer;
    NavigationView navigationView;
    SharedPreferences sharedPreferences;
    ApiInterface apiInterface;

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

//        navigationView drawer
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        sharedPreferences = getSharedPreferences(pref, Context.MODE_PRIVATE);
//        Log.d("tokene", sharedPreferences.getString(my_token, null) + " ");
        kd_user = sharedPreferences.getString(login_session, null);

        FragmentManager fragmentManager = getSupportFragmentManager();
        try {
            fragmentManager.beginTransaction().replace(R.id.flContent, PembayaranFragment.class.newInstance()).commit();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
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
        sharedPreferences = getSharedPreferences(pref, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(login_session, "").equals("")) {
            startActivity(new Intent(MainActivity.this, AuthActivity.class));
            Log.d("tokene", sharedPreferences.getString(my_token, null) + " ");
            finish();
        }

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
        Fragment fragmentt = null;
        Class fragmentclass;
        switch (item.getItemId()) {
//            case R.id.nav_jadwal:
//                fragmentclass = JadwalFragment.class;
//                break;
//            case R.id.nav_profile:
//                fragmentclass = ProfileFragment.class;
//                break;

            case R.id.nav_remune:
                fragmentclass = PembayaranFragment.class;
                break;
            default:
                fragmentclass = PembayaranFragment.class;
        }
        try {
            fragmentt = (Fragment) fragmentclass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.flContent, fragmentt)
                .detach(fragmentt)
                .attach(fragmentt)
                .addToBackStack(null).commit();

        if (id == R.id.nav_logout) {
            Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
            sharedPreferences = getSharedPreferences(pref, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(login_session, null);
            editor.commit();
            startActivity(new Intent(getApplicationContext(), AuthActivity.class));
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