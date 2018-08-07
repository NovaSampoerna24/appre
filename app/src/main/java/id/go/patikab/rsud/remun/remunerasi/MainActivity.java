package id.go.patikab.rsud.remun.remunerasi;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.victor.loading.newton.NewtonCradleLoading;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import id.go.patikab.rsud.remun.remunerasi.adapter.DetailAdapter;
import id.go.patikab.rsud.remun.remunerasi.api.ApiClient;
import id.go.patikab.rsud.remun.remunerasi.api.ApiInterface;
import id.go.patikab.rsud.remun.remunerasi.entity.DataTindakan;
import id.go.patikab.rsud.remun.remunerasi.entity.DetailList;
import id.go.patikab.rsud.remun.remunerasi.entity.DetailTindakan;
import id.go.patikab.rsud.remun.remunerasi.entity.ValDetail;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static id.go.patikab.rsud.remun.remunerasi.firebase.MyFirebaseInstanceIdService.date_up;
import static id.go.patikab.rsud.remun.remunerasi.firebase.MyFirebaseInstanceIdService.login_session;
import static id.go.patikab.rsud.remun.remunerasi.firebase.MyFirebaseInstanceIdService.my_token;
import static id.go.patikab.rsud.remun.remunerasi.firebase.MyFirebaseInstanceIdService.pref;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    TextView total_pendapatan, emaile, judule;
    Button btndetail, btn_date_awal, btn_date_akhir;
    EditText date_Awal, date_Akhir;
    DatePickerDialog awaldatepicker, akhirdatepicker;
    SimpleDateFormat dateFormater;

    String kd_user;

    LinearLayoutManager layoutManager;

    RecyclerView mrecRecyclerView;
    RecyclerView.Adapter madapter;
    RecyclerView.LayoutManager mlayoutManager;

    List<DetailList> detailListList;
    ApiInterface apiInterface;
    DetailAdapter detailAdapter;
    SharedPreferences sharedPreferences;

    NewtonCradleLoading newtonCradleLoading;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dateFormater = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        date_Awal = (EditText) findViewById(R.id.date_awal);

        date_Akhir = (EditText) findViewById(R.id.date_akhir);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar1);
        btn_date_awal = (Button) findViewById(R.id.start_d);
        btn_date_akhir = (Button) findViewById(R.id.end_d);

        btn_date_awal.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogDateAwal();
            }
        });
        btn_date_akhir.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogDateAkhir();
            }
        });
        newtonCradleLoading = (NewtonCradleLoading) findViewById(R.id.newton_cradle_loading);
        newtonCradleLoading.setLoadingColor(R.color.colorAccent);
//        newtonCradleLoading.setLoadingColor("");

        getdatadetail();
        btndetail = (Button) findViewById(R.id.btn_detail);
        btndetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textn = btndetail.getText().toString();
                if (textn == "detail") {
                    newtonCradleLoading.setVisibility(View.VISIBLE);
                    newtonCradleLoading.start();
                    getListDataDetail();
                } else {
                    hiddenlist();
                    Log.d("test", textn);
                }
            }
        });

        mrecRecyclerView = (RecyclerView) findViewById(R.id.list_detail);
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


        sharedPreferences = getSharedPreferences(pref, Context.MODE_PRIVATE);
        kd_user = sharedPreferences.getString(login_session, null);
//        Toast.makeText(this, kd_user, Toast.LENGTH_SHORT).show();

        emaile = (TextView) findViewById(R.id.email_d);
        judule = (TextView) findViewById(R.id.judul);
        total_pendapatan = (TextView) findViewById(R.id.pendapatan_total);
    }

    private void showDialogDateAkhir() {
        Calendar akhircalendar = Calendar.getInstance();
        akhirdatepicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                Calendar newDateAwal = Calendar.getInstance();
                newDateAwal.set(i, i1, i2);
                date_Akhir.setText(dateFormater.format(newDateAwal.getTime()));
                progressBar.setVisibility(View.VISIBLE);
                total_pendapatan.setVisibility(View.GONE);
                getdatadetail();
            }
        }, akhircalendar.get(Calendar.YEAR), akhircalendar.get(Calendar.MONTH), akhircalendar.get(Calendar.DAY_OF_MONTH));
        akhirdatepicker.show();
    }


    private void showDialogDateAwal() {
        Calendar awalcalendar = Calendar.getInstance();
        awaldatepicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                Calendar newDateAwal = Calendar.getInstance();
                newDateAwal.set(i, i1, i2);
                date_Awal.setText(dateFormater.format(newDateAwal.getTime()));
                progressBar.setVisibility(View.VISIBLE);
                total_pendapatan.setVisibility(View.GONE);
                getdatadetail();
            }
        }, awalcalendar.get(Calendar.YEAR), awalcalendar.get(Calendar.MONTH), awalcalendar.get(Calendar.DAY_OF_MONTH));
        awaldatepicker.show();
    }


    private void getdatadetail() {
        String date_aw = date_Awal.getText().toString().trim();
        String date_ak = date_Akhir.getText().toString().trim();
        try {
            Call<DataTindakan> call = apiInterface.getDataTindakan(kd_user, date_aw, date_ak);
            call.enqueue(new Callback<DataTindakan>() {
                @Override
                public void onResponse(Call<DataTindakan> call, Response<DataTindakan> response) {
                    String status, total, nama_dokter, judul;
                    status = response.body().getStatus().toString();
                    nama_dokter = response.body().getNama_dokter().toString();
                    judul = response.body().getJudul().toString();
                    if (response.isSuccessful()) {
                        if (status.equals("ok")) {
                            emaile.setText(nama_dokter);
                            if (response.body().getTotal().equals("0")) {
                                total_pendapatan.setText("Rp. 0");
                            } else {
                                total_pendapatan.setText(response.body().getTotal().toString());
                            }
                            judule.setText(judul);

                        } else {
                            if (response.body().getTotal().equals("0")) {
                                total_pendapatan.setText("Rp. 0");
                            } else {
                                total_pendapatan.setText(response.body().getTotal().toString());
                            }
                            Toast.makeText(MainActivity.this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                        }
                        progressBar.setVisibility(View.GONE);
                        total_pendapatan.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<DataTindakan> call, Throwable t) {
                    Log.d("Failure", t.getMessage());
                }
            });
        } catch (Exception e) {
            Log.d("Exception", e.getMessage());
        }
    }

    private void hiddenlist() {
        mrecRecyclerView.setVisibility(View.GONE);
        btndetail.setText("detail");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void getListDataDetail() {
        String date_aw = date_Awal.getText().toString();
        String date_ak = date_Akhir.getText().toString();
        Log.d("filter", kd_user + " " + date_aw + " " + date_ak);
        try {
            Call<DataTindakan> call = apiInterface.getDataTindakan(kd_user, date_aw, date_ak);
            call.enqueue(new Callback<DataTindakan>() {
                @Override
                public void onResponse(Call<DataTindakan> call, Response<DataTindakan> response) {
                    String status, total, nama_dokter, judul;
                    status = response.body().getStatus().toString();
                    total = response.body().getTotal().toString();
                    nama_dokter = response.body().getNama_dokter().toString();
                    judul = response.body().getJudul().toString();
                    if (response.isSuccessful()) {
                        if (status.equals("ok")) {
                            emaile.setText(nama_dokter);
                            if (response.body().getTotal().equals("0")) {
                                total_pendapatan.setText("Rp. 0");
                            } else {
                                total_pendapatan.setText(response.body().getTotal().toString());
                            }
                            judule.setText(judul);
                            if (response.body().getDetailTindakanList().size() > 0) {
                                mrecRecyclerView.setVisibility(View.VISIBLE);
                                Log.d("total :", response.body().getTotal().toString());
                                List<DetailTindakan> detailTindakanList = response.body().getDetailTindakanList();
                                madapter = new DetailAdapter(detailTindakanList);
                                mrecRecyclerView.setAdapter(madapter);
                                btndetail.setText("sembunyikan");
                            } else {
                                Toast.makeText(MainActivity.this, "Detail Tindakan kosong ! perbarui tindakan anda", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                        }
                    }
                    newtonCradleLoading.stop();
                    newtonCradleLoading.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<DataTindakan> call, Throwable t) {
                    Log.d("Failure", t.getMessage());
                }
            });

        } catch (Exception e) {
            Log.w("Exception", e.getMessage());
        }
    }
//    private void getlistDetail() {
//        try {
//            Call<ValDetail> call = apiInterface.getDetail();
//            call.enqueue(new Callback<ValDetail>() {
//                @Override
//                public void onResponse(Call<ValDetail> call, Response<ValDetail> response) {
////                    Toast.makeText(MainActivity.this, response.body().getDetailListList().size(), Toast.LENGTH_SHORT).show();
//
//                    mrecRecyclerView.setVisibility(View.VISIBLE);
//                    Log.d("test 1", response.body().getResponses().toString());
//                    List<DetailList> detailLists = response.body().getDetailListList();
//                    madapter = new DetailAdapter(detailLists);
//                    mrecRecyclerView.setAdapter(madapter);
//
//                    btndetail.setText("sembunyikan");
//                    newtonCradleLoading.stop();
//                    newtonCradleLoading.setVisibility(View.GONE);
//                }
//
//                @Override
//                public void onFailure(Call<ValDetail> call, Throwable t) {
//                    Log.d("trowable : ", t.getMessage());
//                }
//            });
//        } catch (Exception e) {
//            Log.d("err : ", e.getMessage());
//        }
//    }

    @Override
    protected void onStart() {
        super.onStart();

        Date date = Calendar.getInstance().getTime();
        DateFormat formater = new SimpleDateFormat("dd/MMMM/yyyy HH:mm:ss");
        String date_o = formater.format(date);
        String d = date_o.toString();

        sharedPreferences = getSharedPreferences(pref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("date_up");
        editor.putString("date_up", d);
        editor.apply();

        if (btndetail.getText() != "detail") {
            btndetail.setText("detail");
        }
//        sharedPreferences = getSharedPreferences(pref, Context.MODE_PRIVATE);
//        if (sharedPreferences.contains("date_up")) {
//            String date_u = sharedPreferences.getString("date_up", null);
//            date_update = (TextView) findViewById(R.id.date_update);
//            date_update.setText(date_u);
//        }

        Intent i = getIntent();
        if (i != null) {
            Bundle b = i.getExtras();
            if (b != null) {
                Set<String> keys = b.keySet();
                for (String key : keys) {
                    Log.d("getintent", "Bundle contains : key = " + key);
                }
            } else {
                Log.w("getintent", "oncreate bundle null");
            }

        } else {
            Log.w("getintent", "intent is null");

        }
        progressBar.setVisibility(View.VISIBLE);
        total_pendapatan.setVisibility(View.GONE);
        getdatadetail();
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
        if (id == R.id.nav_logout) {
            Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
            sharedPreferences = getSharedPreferences(pref, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
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