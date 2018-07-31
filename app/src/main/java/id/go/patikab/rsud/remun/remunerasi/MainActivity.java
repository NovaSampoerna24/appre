package id.go.patikab.rsud.remun.remunerasi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.victor.loading.newton.NewtonCradleLoading;

import java.util.List;

import id.go.patikab.rsud.remun.remunerasi.adapter.DetailAdapter;
import id.go.patikab.rsud.remun.remunerasi.api.ApiClient;
import id.go.patikab.rsud.remun.remunerasi.api.ApiInterface;
import id.go.patikab.rsud.remun.remunerasi.entity.DetailList;
import id.go.patikab.rsud.remun.remunerasi.entity.ValDetail;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    TextView txt_pendatapan;
    Button btndetail;
    FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    LinearLayoutManager layoutManager;

    RecyclerView mrecRecyclerView;
    RecyclerView.Adapter madapter;
    RecyclerView.LayoutManager mlayoutManager;

    List<DetailList> detailListList;
    ApiInterface apiInterface;
    DetailAdapter detailAdapter;


    NewtonCradleLoading newtonCradleLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        newtonCradleLoading = (NewtonCradleLoading)findViewById(R.id.newton_cradle_loading);
        newtonCradleLoading.setLoadingColor(R.color.colorAccent);
//        newtonCradleLoading.setLoadingColor("");

        btndetail = (Button)findViewById(R.id.btn_detail);
        btndetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              String textn = btndetail.getText().toString();
                if (textn == "detail") {
                    newtonCradleLoading.setVisibility(View.VISIBLE);
                    newtonCradleLoading.start();
                    getlistDetail();
                }else{
                    hiddenlist();
                    Log.d("test",textn);
                }
            }
        });

        mrecRecyclerView = (RecyclerView)findViewById(R.id.list_detail);
        mlayoutManager = new LinearLayoutManager(this);
        mrecRecyclerView.setLayoutManager(mlayoutManager);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        firebaseAuth = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    startActivity(new Intent(getApplicationContext(), AuthActivity.class));
                    finish();
                }
            }
        };

        final FirebaseUser user = firebaseAuth.getCurrentUser();
        Log.d("email : ", user.getEmail());
        String email = user.getEmail().toString();
        Toast.makeText(this, "Selamat datang " + user.getEmail(), Toast.LENGTH_SHORT).show();
        TextView t = (TextView)findViewById(R.id.email_d);

//        t.setText(email);
    }
    private void hiddenlist() {
        mrecRecyclerView.setVisibility(View.GONE);
        btndetail.setText("detail");
    }

    private void getlistDetail() {
        try {
            Call<ValDetail> call = apiInterface.getDetail();
            call.enqueue(new Callback<ValDetail>() {
                @Override
                public void onResponse(Call<ValDetail> call, Response<ValDetail> response) {
//                    Toast.makeText(MainActivity.this, response.body().getDetailListList().size(), Toast.LENGTH_SHORT).show();
                    mrecRecyclerView.setVisibility(View.VISIBLE);
                    Log.d("test 1",response.body().getResponses().toString());
                    List<DetailList>detailLists = response.body().getDetailListList();
                    madapter= new DetailAdapter(detailLists);
                    mrecRecyclerView.setAdapter(madapter);
                    btndetail.setText("sembunyikan");
                    newtonCradleLoading.stop();
                    newtonCradleLoading.setVisibility(View.GONE);
                }
                @Override
                public void onFailure(Call<ValDetail> call, Throwable t) {
                    Log.d("trowable : ", t.getMessage());
                }
            });
        } catch (Exception e) {
            Log.d("err : ", e.getMessage());
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
        try {
            Call<ValDetail> call = apiInterface.getDetail();
            call.enqueue(new Callback<ValDetail>() {
                @Override
                public void onResponse(Call<ValDetail> call, Response<ValDetail> response) {
                    txt_pendatapan = (TextView)findViewById(R.id.pendapatan);
                    txt_pendatapan.setText(response.body().getTotalDapat().toString());
                }

                @Override
                public void onFailure(Call<ValDetail> call, Throwable t) {
                    Log.d("error",t.getMessage());
                }
            });
        }catch (Exception e){
            Log.d("err : ", e.getMessage());
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (authStateListener != null) {
            firebaseAuth.removeAuthStateListener(authStateListener);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(id== R.id.nav_logout){
            Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
                startActivity(new Intent(getApplicationContext(), AuthActivity.class));
                finish();
        }
//
//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}