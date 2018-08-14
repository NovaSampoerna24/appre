package id.go.patikab.rsud.remun.remunerasi;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.victor.loading.newton.NewtonCradleLoading;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.go.patikab.rsud.remun.remunerasi.adapter.DetailAdapter;
import id.go.patikab.rsud.remun.remunerasi.api.ApiClient;
import id.go.patikab.rsud.remun.remunerasi.api.ApiInterface;
import id.go.patikab.rsud.remun.remunerasi.entity.DataTindakan;
import id.go.patikab.rsud.remun.remunerasi.entity.DetailTindakan;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static id.go.patikab.rsud.remun.remunerasi.firebase.MyFirebaseInstanceIdService.login_session;
import static id.go.patikab.rsud.remun.remunerasi.firebase.MyFirebaseInstanceIdService.my_token;
import static id.go.patikab.rsud.remun.remunerasi.firebase.MyFirebaseInstanceIdService.pref;

import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    TextView total_pendapatan, emaile, judule, pendapatan_bpjs, pendapatan_umum, txtumum, txtbpjs, txtloadmore;
    Button btndetail, btn_date_awal, btn_date_akhir;
    EditText date_Awal, date_Akhir;
    DatePickerDialog awaldatepicker, akhirdatepicker;
    SimpleDateFormat dateFormater;
    String kd_user;
    LinearLayoutManager layoutManager;
    Toolbar toolbar;
    List<DetailTindakan> detailTindakanList;

    RecyclerView mrecRecyclerView;
    RecyclerView.Adapter madapter;
    RecyclerView.LayoutManager mlayoutManager;

    DrawerLayout drawer;
    NavigationView navigationView;

    int listcount = 5;
    int countplus = 4;
    boolean isScrolling = false;
    int currentItems, totalItems, scrollItems;

    ApiInterface apiInterface;
    DetailAdapter detailAdapter;
    SharedPreferences sharedPreferences;

    NewtonCradleLoading newtonCradleLoading;
    ProgressBar progressBar1, progressBar2, progressBar3, loadMoreProgreses;

    Animation uptodown;
    LinearLayout ln_f, ln_2, liner,loaded;

    @BindView(R.id.btnfilter)
    Button btnfilters;

    @OnClick(R.id.hariIni)
    public void hariIni() {
        showLoad();
        getiListHariIni();
    }

    @OnClick(R.id.mingguIni)
    public void mingguini() {
        showLoad();
        getiListMingguan();
    }

    @OnClick(R.id.bulanIni)
    public void bulan() {
        showLoad();
        getiListBulanan();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        create api
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
//        setting dateformat
        dateFormater = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
//        butterknife bind
        ButterKnife.bind(this);
//        find id
        findViewById();
//        toolbar
        setSupportActionBar(toolbar);

//        progress loading
        newtonCradleLoading.setLoadingColor(R.color.colorAccent);
//        get detail
//        getdatadetail();
//        set layout manager
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mlayoutManager = new LinearLayoutManager(this);
        mrecRecyclerView.setLayoutManager(layoutManager);

//        navigationView drawer
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
//    get kduser from preference
        sharedPreferences = getSharedPreferences(pref, Context.MODE_PRIVATE);
        Log.d("tokene",sharedPreferences.getString(my_token,null)+" ");
        kd_user = sharedPreferences.getString(login_session, null);

        btnfilters.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String btn = btnfilters.getText().toString();
                if (btn == "hide") {
                    ln_f.setVisibility(View.GONE);
                    btnfilters.setText("show");
                } else {
                    ln_f.setVisibility(View.VISIBLE);
                    btnfilters.setText("hide");
                }
            }
        });
        btndetail.setText("Detail");
        btndetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textn = btndetail.getText().toString();
                if (textn == "Detail") {
                    newtonCradleLoading.setVisibility(View.VISIBLE);
                    newtonCradleLoading.start();
                    getListDataDetail();
                } else {
                    mrecRecyclerView.setVisibility(View.GONE);
                    btndetail.setText("Detail");
                    detailisthide();
                    Log.d("test", textn);
                }
            }
        });
        btn_date_awal.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogDateAwal();
            }
        });
        date_Awal.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                showDialogDateAwal();
            }
        });
        date_Awal.setOnClickListener(new OnClickListener() {
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
        date_Akhir.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogDateAkhir();
            }
        });
        date_Akhir.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                showDialogDateAkhir();
            }
        });
        mrecRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                Log.d("scrol", "1");
                Log.d("scrols", " t" + dy + " " + dx);

                if (dy > 0) {
                    currentItems = layoutManager.getChildCount();
                    totalItems = layoutManager.getItemCount();
                    scrollItems = layoutManager.findFirstVisibleItemPosition();

                    if (isScrolling && (currentItems + scrollItems == totalItems)) {
//                    data fetch
                        isScrolling = false;

                        fetchData();
                        Log.d("listadapter", madapter.getItemCount() + " s");
                        Log.d("scrol", "2");
                    }
                    Log.d("scrol", "3");
                    Log.d("sop", isScrolling + " " + currentItems + " " + scrollItems + " " + totalItems);
                }
            }
        });
    }

    private void findViewById() {
        date_Awal = (EditText) findViewById(R.id.date_awal);
        date_Akhir = (EditText) findViewById(R.id.date_akhir);

        progressBar1 = (ProgressBar) findViewById(R.id.progress_bar1);
        progressBar2 = (ProgressBar) findViewById(R.id.progress_bar2);
        progressBar3 = (ProgressBar) findViewById(R.id.progress_bar3);
        loadMoreProgreses = (ProgressBar) findViewById(R.id.loadMore);

        txtloadmore = (TextView) findViewById(R.id.txtloadmore);

        btn_date_awal = (Button) findViewById(R.id.start_d);
        btn_date_akhir = (Button) findViewById(R.id.end_d);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        newtonCradleLoading = (NewtonCradleLoading) findViewById(R.id.newton_cradle_loading);

        emaile = (TextView) findViewById(R.id.email_d);
        judule = (TextView) findViewById(R.id.judul);
        total_pendapatan = (TextView) findViewById(R.id.pendapatan_total);
        pendapatan_umum = (TextView) findViewById(R.id.pendapatan_umum);
        pendapatan_bpjs = (TextView) findViewById(R.id.pendapatan_bpjs);
        txtbpjs = (TextView) findViewById(R.id.txtbpjs);
        txtumum = (TextView) findViewById(R.id.txtumum);

        ln_f = (LinearLayout) findViewById(R.id.ln_filter);

        mrecRecyclerView = (RecyclerView) findViewById(R.id.list_detail);
        btndetail = (Button) findViewById(R.id.btn_detail);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        navigationView = (NavigationView) findViewById(R.id.nav_view);

        liner = (LinearLayout) findViewById(R.id.liner);
        loaded =(LinearLayout)findViewById(R.id.loaded);
    }

    private void detailisthide() {
        btnfilters.setEnabled(true);
        pendapatan_bpjs.setVisibility(View.VISIBLE);
        pendapatan_umum.setVisibility(View.VISIBLE);
        txtbpjs.setVisibility(View.VISIBLE);
        txtumum.setVisibility(View.VISIBLE);
    }

    private void detailisttampil() {
        btnfilters.setEnabled(false);
        ln_f.setVisibility(View.GONE);
        pendapatan_bpjs.setVisibility(View.GONE);
        pendapatan_umum.setVisibility(View.GONE);
        txtbpjs.setVisibility(View.GONE);
        txtumum.setVisibility(View.GONE);
    }

    private void showDialogDateAkhir() {
        Calendar akhircalendar = Calendar.getInstance();
        akhirdatepicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                Calendar newDateAwal = Calendar.getInstance();
                newDateAwal.set(i, i1, i2);
                date_Akhir.setText(dateFormater.format(newDateAwal.getTime()));

                showLoad();
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
//                showLoad();
//                getdatadetail();
            }
        }, awalcalendar.get(Calendar.YEAR), awalcalendar.get(Calendar.MONTH), awalcalendar.get(Calendar.DAY_OF_MONTH));
        awaldatepicker.show();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
    @Override
    protected void onStart() {
        super.onStart();
        sharedPreferences = getSharedPreferences(pref, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(login_session,"").equals("")) {
            startActivity(new Intent(MainActivity.this, AuthActivity.class));
            finish();
        } else {
            String textn = btndetail.getText().toString();
            if (textn == "detail") {
                mrecRecyclerView.setVisibility(View.GONE);
                btndetail.setText("detail");
                detailisthide();
            }

            Date date = Calendar.getInstance().getTime();
            DateFormat formater = new SimpleDateFormat("dd/MMMM/yyyy");
            String date_o = formater.format(date);
            String d = date_o.toString();


            sharedPreferences = getSharedPreferences(pref, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("date_up");
            editor.putString("date_up", d);
            editor.apply();

            date_Awal.setFocusable(false);
            date_Akhir.setFocusable(false);


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
            showLoad();
            getdatadetail();
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
            editor.putString(login_session,null);
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
    private void getdatadetail() {
        String date_aw = date_Awal.getText().toString().trim();
        String date_ak = date_Akhir.getText().toString().trim();
        if (isOnline() == true) {
            try {
                Call<DataTindakan> call = apiInterface.getDataTindakan(kd_user, date_aw, date_ak);
                call.enqueue(new Callback<DataTindakan>() {
                    @Override
                    public void onResponse(Call<DataTindakan> call, Response<DataTindakan> response) {
                        String status ="", total="", nama_dokters="", judul, pendapatan_bpjsr, pendapatan_umumr;
                        status = response.body().getStatus().toString();
                        nama_dokters = response.body().getNama_dokter().toString() ;
                        judul = response.body().getJudul().toString();
                        pendapatan_bpjsr = response.body().getPendapatan_bpjs();
                        pendapatan_umumr = response.body().getPendapatan_umum();
                        if (response.isSuccessful()) {

                            if (status.equals("ok")) {
                                emaile.setText("Nama : "+nama_dokters);
                                judule.setText(judul);
                                pendapatan_bpjs.setText(pendapatan_bpjsr);
                                pendapatan_umum.setText(pendapatan_umumr);
                                if (response.body().getTotal().equals("0")) {
                                    total_pendapatan.setText("Rp. 0");
                                } else {
                                    total_pendapatan.setText(response.body().getTotal().toString());
                                }
                            } else {
                                if (response.body().getTotal().equals("0")) {
                                    total_pendapatan.setText("Rp. 0");
                                } else {
                                    total_pendapatan.setText(response.body().getTotal().toString());
                                }

                                Toast.makeText(MainActivity.this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                            }

                            hideLoad();
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
        } else {
            Toast.makeText(this, "Periksa kembali koneksi internet !", Toast.LENGTH_SHORT).show();
        }
    }

    private void getListDataDetail() {
        String date_aw = date_Awal.getText().toString();
        String date_ak = date_Akhir.getText().toString();
        if (isOnline() == true) {
            try {
                Call<DataTindakan> call = apiInterface.getDataTindakan(kd_user, date_aw, date_ak);
                call.enqueue(new Callback<DataTindakan>() {
                    @Override
                    public void onResponse(Call<DataTindakan> call, Response<DataTindakan> response) {
                        String status, total, nama_dokter, judul, pendapatan_bpjsr, pendapatan_umumr;

                        pendapatan_bpjsr = response.body().getPendapatan_bpjs();
                        pendapatan_umumr = response.body().getPendapatan_umum();


                        if (response.isSuccessful()) {
                            status = response.body().getStatus().toString();
                            total = response.body().getTotal().toString();
                            nama_dokter = response.body().getNama_dokter().toString();
                            judul = response.body().getJudul().toString();

                            pendapatan_bpjs.setText(pendapatan_bpjsr);
                            pendapatan_umum.setText(pendapatan_umumr);
                            if (status.equals("ok")) {
                                emaile.setText("Nama : "+nama_dokter);
                                if (response.body().getTotal().equals("0")) {
                                    total_pendapatan.setText("Rp. 0");
                                } else {
                                    total_pendapatan.setText(response.body().getTotal().toString());
                                }
                                judule.setText(judul);
                                if (response.body().getDetailTindakanList().size() > 0) {
                                    mrecRecyclerView.setVisibility(View.VISIBLE);
//                                    mrecRecyclerView.setMinimumHeight(1000);
                                    Log.d("total :", response.body().getTotal().toString());
                                    detailTindakanList = response.body().getDetailTindakanList();
                                    madapter = new DetailAdapter(detailTindakanList, listcount, MainActivity.this);
                                    mrecRecyclerView.setAdapter(madapter);
                                    btndetail.setText("sembunyikan");

                                    detailisttampil();

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
        } else {
            Toast.makeText(this, "Periksa kembali koneksi internet !", Toast.LENGTH_SHORT).show();
        }
    }

    private void hideLoad() {
        progressBar1.setVisibility(View.GONE);
        progressBar2.setVisibility(View.GONE);
        progressBar3.setVisibility(View.GONE);

        total_pendapatan.setVisibility(View.VISIBLE);
        pendapatan_bpjs.setVisibility(View.VISIBLE);
        pendapatan_umum.setVisibility(View.VISIBLE);
    }

    private void showLoad() {
        progressBar1.setVisibility(View.VISIBLE);
        progressBar2.setVisibility(View.VISIBLE);
        progressBar3.setVisibility(View.VISIBLE);

        total_pendapatan.setVisibility(View.GONE);
        pendapatan_bpjs.setVisibility(View.GONE);
        pendapatan_umum.setVisibility(View.GONE);
    }

    private void getiListBulanan() {
        Date date = Calendar.getInstance().getTime();
        DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
        String date_o = formater.format(date);
        String d = date_o.toString();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat ds = new SimpleDateFormat("yyyy-MM-dd");
        try {
            cal.setTime(ds.parse(d));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        cal.add(Calendar.MONTH, -1);
        Log.d("test2", "" + ds.format(cal.getTime()));
        String tanggal_a = ds.format(cal.getTime());
        date_Awal.setText(tanggal_a);
        date_Akhir.setText(d);
        if (isOnline() == true) {
            try {
                Call<DataTindakan> call = apiInterface.getDataTindakan(kd_user, ds.format(cal.getTime()), d);
                call.enqueue(new Callback<DataTindakan>() {
                    @Override
                    public void onResponse(Call<DataTindakan> call, Response<DataTindakan> response) {
                        String status, total, nama_dokter, judul, pendapatan_bpjsr, pendapatan_umumr;

                        if (response.isSuccessful()) {
                            pendapatan_bpjsr = response.body().getPendapatan_bpjs();
                            pendapatan_umumr = response.body().getPendapatan_umum();
                            status = response.body().getStatus().toString();
                            nama_dokter = response.body().getNama_dokter().toString();
                            judul = response.body().getJudul().toString();
                            pendapatan_bpjs.setText(pendapatan_bpjsr);
                            pendapatan_umum.setText(pendapatan_umumr);
                            if (status.equals("ok")) {
                                emaile.setText("Nama : "+nama_dokter);
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
                            hideLoad();
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
        } else {
            Toast.makeText(this, "Periksa kembali koneksi internet !", Toast.LENGTH_SHORT).show();
        }
    }

    private void getiListMingguan() {
        Date date = Calendar.getInstance().getTime();
        DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
        String date_o = formater.format(date);
        String d = date_o.toString();

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat ds = new SimpleDateFormat("yyyy-MM-dd");
        try {
            cal.setTime(ds.parse(d));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        cal.add(Calendar.DAY_OF_MONTH, -7);
        String tanggal_a = ds.format(cal.getTime());
        date_Awal.setText(tanggal_a);
        date_Akhir.setText(d);
        if (isOnline() == true) {
            try {
                Call<DataTindakan> call = apiInterface.getDataTindakan(kd_user, tanggal_a, d);
                call.enqueue(new Callback<DataTindakan>() {
                    @Override
                    public void onResponse(Call<DataTindakan> call, Response<DataTindakan> response) {
                        String status, total, nama_dokter, judul, pendapatan_bpjsr, pendapatan_umumr;

                        if (response.isSuccessful()) {
                            pendapatan_bpjsr = response.body().getPendapatan_bpjs();
                            pendapatan_umumr = response.body().getPendapatan_umum();

                            status = response.body().getStatus().toString();
                            nama_dokter = response.body().getNama_dokter().toString();
                            judul = response.body().getJudul().toString();
                            pendapatan_bpjs.setText(pendapatan_bpjsr);
                            pendapatan_umum.setText(pendapatan_umumr);
                            if (status.equals("ok")) {
                                emaile.setText("Nama : "+nama_dokter);
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
                            hideLoad();
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

        } else {
            Toast.makeText(this, "Periksa kembali koneksi internet !", Toast.LENGTH_SHORT).show();
        }
    }

    private void getiListHariIni() {
        Date date = Calendar.getInstance().getTime();
        DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
        String date_o = formater.format(date);
        String d = date_o.toString();

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat ds = new SimpleDateFormat("yyyy-MM-dd");
        try {
            cal.setTime(ds.parse(d));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        cal.add(Calendar.DAY_OF_MONTH, -1);
        String tanggal_a = ds.format(cal.getTime());
        date_Awal.setText(tanggal_a);
        date_Akhir.setText(d);
        if (isOnline() == true) {
            try {
                Call<DataTindakan> call = apiInterface.getDataTindakan(kd_user, tanggal_a, d);
                call.enqueue(new Callback<DataTindakan>() {
                    @Override
                    public void onResponse(Call<DataTindakan> call, Response<DataTindakan> response) {
                        String status, total, nama_dokter, judul, pendapatan_bpjsr, pendapatan_umumr;

                        if (response.isSuccessful()) {
                            pendapatan_bpjsr = response.body().getPendapatan_bpjs();
                            pendapatan_umumr = response.body().getPendapatan_umum();

                            status = response.body().getStatus().toString();
                            nama_dokter = response.body().getNama_dokter().toString();
                            judul = response.body().getJudul().toString();
                            pendapatan_bpjs.setText(pendapatan_bpjsr);
                            pendapatan_umum.setText(pendapatan_umumr);
                            if (status.equals("ok")) {
                                emaile.setText("Nama : "+nama_dokter);
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
                            hideLoad();
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
        } else {
            Toast.makeText(this, "Periksa kembali koneksi internet !", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchData() {
        txtloadmore.setVisibility(View.VISIBLE);
        loadMoreProgreses.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                madapter = new DetailAdapter(detailTindakanList, madapter.getItemCount() + countplus, MainActivity.this);
                mrecRecyclerView.setAdapter(madapter);
                mrecRecyclerView.scrollToPosition(madapter.getItemCount()- ((countplus*2)));
//                mrecRecyclerView.smoothScrollToPosition();
                loadMoreProgreses.setVisibility(View.GONE);
                txtloadmore.setVisibility(View.GONE);
            }
        }, 1000);
    }

    //method untuk cek koneksi
    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        //jika ada koneksi return true
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        //jika tidak ada koneksi return false
        return false;
    }
}