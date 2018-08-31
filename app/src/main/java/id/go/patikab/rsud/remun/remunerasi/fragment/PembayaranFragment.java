package id.go.patikab.rsud.remun.remunerasi.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.Unbinder;
import id.go.patikab.rsud.remun.remunerasi.AuthActivity;
import id.go.patikab.rsud.remun.remunerasi.MainActivity;
import id.go.patikab.rsud.remun.remunerasi.R;
import id.go.patikab.rsud.remun.remunerasi.adapter.DetailAdapter;
import id.go.patikab.rsud.remun.remunerasi.api.ApiClient;
import id.go.patikab.rsud.remun.remunerasi.api.ApiInterface;
import id.go.patikab.rsud.remun.remunerasi.entity.DataTindakan;
import id.go.patikab.rsud.remun.remunerasi.entity.DetailTindakan;
import id.go.patikab.rsud.remun.remunerasi.page_dialog.CustomDialogDetail;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static id.go.patikab.rsud.remun.remunerasi.database.sharepreference.SharePref.login_session;
import static id.go.patikab.rsud.remun.remunerasi.database.sharepreference.SharePref.my_token;
import static id.go.patikab.rsud.remun.remunerasi.database.sharepreference.SharePref.pref;

public class PembayaranFragment extends Fragment {
    TextView total_pendapatan,
            emaile, judule,
            pendapatan_bpjs,
            pendapatan_umum,
            txtumum,
            txtbpjs,
            txtloadmore;

    Button btndetail,
            btn_date_awal,
            btn_date_akhir;

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
    int currentItems, totalItems, scrollItems;

    ApiInterface apiInterface;
    SharedPreferences sharedPreferences;

    NewtonCradleLoading newtonCradleLoading;
    ProgressBar progressBar1,
            progressBar2,
            progressBar3,
            loadMoreProgreses;
    Animation uptodown;
    LinearLayout ln_f, ln_2, liner, loaded;
    NestedScrollView scrolloadm;

    Unbinder ubin;
    Context context;

    public ActionBar getActionBar() {
        return ((AppCompatActivity) getActivity()).getSupportActionBar();
    }

    @BindView(R.id.btnfilter)
    Button btnfilters;

    @OnClick(R.id.hariIni)
    public void hariIni() {
        showLoad();
        getiListHariIni();
        mrecRecyclerView.setVisibility(View.GONE);
        btndetail.setText("Detail");
    }

    @OnClick(R.id.mingguIni)
    public void mingguini() {
        showLoad();
        getiListMingguan();
        mrecRecyclerView.setVisibility(View.GONE);
        btndetail.setText("Detail");
    }

    @OnClick(R.id.bulanIni)
    public void bulan() {
        showLoad();
        getiListBulanan();
        mrecRecyclerView.setVisibility(View.GONE);
        btndetail.setText("Detail");
    }

    @OnClick(R.id.btnfilter)
    public void btnfilter() {
        String btn = btnfilters.getText().toString();
        if (btn == "hide") {
            ln_f.setVisibility(View.GONE);
            btnfilters.setText("show");
        } else {
            ln_f.setVisibility(View.VISIBLE);
            btnfilters.setText("hide");
        }
    }

    @OnClick(R.id.btn_detail)
    public void btn_detail() {
        String textn = btndetail.getText().toString();
        if (textn == "Detail") {
            newtonCradleLoading.setVisibility(View.VISIBLE);
            newtonCradleLoading.start();
            getListDataDetail();
        } else {
            mrecRecyclerView.setVisibility(View.GONE);
            btndetail.setText("Detail");
        }
    }

    @OnClick(R.id.start_d)
    public void start_d() {
        showDialogDateAwal();
    }

    @OnClick(R.id.date_awal)
    public void date_awal() {
        showDialogDateAwal();
    }

    @OnClick(R.id.end_d)
    public void end_d() {
        showDialogDateAkhir();
    }

    @OnClick(R.id.date_akhir)
    public void date_akhir() {
        showDialogDateAkhir();
    }

    @OnFocusChange(R.id.date_awal)
    public void date_a() {
        showDialogDateAwal();
    }

    @OnFocusChange(R.id.date_akhir)
    public void date_k() {
        showDialogDateAkhir();
    }

    public PembayaranFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pembayaran, container, false);
        findViewById1(view);
        ubin = ButterKnife.bind(this, view);
        // Inflate the layout for this fragment
        btndetail.setText("Detail");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActionBar().setTitle("Remunerasi");
        date_Awal.setFocusable(false);
        date_Akhir.setFocusable(false);
        //        setting dateformat
        dateFormater = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        newtonCradleLoading.setLoadingColor(R.color.colorAccent);

        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mlayoutManager = new LinearLayoutManager(getActivity());
        mrecRecyclerView.setLayoutManager(layoutManager);

        sharedPreferences = getActivity().getSharedPreferences(pref, Context.MODE_PRIVATE);
        Log.d("tokene", sharedPreferences.getString(my_token, null) + " ");
        kd_user = sharedPreferences.getString(login_session, null);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        scrolloadm.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (v.getChildAt(v.getChildCount() - 1) != null) {
                    if ((scrollY >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight())) &&
                            scrollY > oldScrollY) {

                        currentItems = layoutManager.getChildCount();
                        totalItems = layoutManager.getItemCount();
                        scrollItems = layoutManager.findFirstVisibleItemPosition();
                        if ((currentItems + scrollItems) >= totalItems) {
                            fetchData();
                        }
                    }
                }
            }
        });
        hariIni();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void findViewById1(View view) {
        date_Awal = (EditText) view.findViewById(R.id.date_awal);
        date_Akhir = (EditText) view.findViewById(R.id.date_akhir);

        progressBar1 = (ProgressBar) view.findViewById(R.id.progress_bar1);
        progressBar2 = (ProgressBar) view.findViewById(R.id.progress_bar2);
        progressBar3 = (ProgressBar) view.findViewById(R.id.progress_bar3);
        loadMoreProgreses = (ProgressBar) view.findViewById(R.id.loadMore);

        txtloadmore = (TextView) view.findViewById(R.id.txtloadmore);

        btn_date_awal = (Button) view.findViewById(R.id.start_d);
        btn_date_akhir = (Button) view.findViewById(R.id.end_d);

        toolbar = (Toolbar) view.findViewById(R.id.toolbar);

        newtonCradleLoading = (NewtonCradleLoading) view.findViewById(R.id.newton_cradle_loading);

        emaile = (TextView) view.findViewById(R.id.email_d);
        judule = (TextView) view.findViewById(R.id.judul);
        total_pendapatan = (TextView) view.findViewById(R.id.pendapatan_total);
        pendapatan_umum = (TextView) view.findViewById(R.id.pendapatan_umum);
        pendapatan_bpjs = (TextView) view.findViewById(R.id.pendapatan_bpjs);
        txtbpjs = (TextView) view.findViewById(R.id.txtbpjs);
        txtumum = (TextView) view.findViewById(R.id.txtumum);

        ln_f = (LinearLayout) view.findViewById(R.id.ln_filter);

        mrecRecyclerView = (RecyclerView) view.findViewById(R.id.list_detail);
        btndetail = (Button) view.findViewById(R.id.btn_detail);

        drawer = (DrawerLayout) view.findViewById(R.id.drawer_layout);

        navigationView = (NavigationView) view.findViewById(R.id.nav_view);

        liner = (LinearLayout) view.findViewById(R.id.liner);
        loaded = (LinearLayout) view.findViewById(R.id.loaded);

        scrolloadm = (NestedScrollView) view.findViewById(R.id.nestedScroll);
    }


    private void showDialogDateAkhir() {
        Calendar akhircalendar = Calendar.getInstance();
        akhirdatepicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
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
        awaldatepicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
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

    private void getdatadetail() {
        String date_aw = date_Awal.getText().toString().trim();
        String date_ak = date_Akhir.getText().toString().trim();
        if (kd_user != null) {
            if (isOnline() == true) {
                try {
                    Call<DataTindakan> call = apiInterface.getDataTindakan(kd_user, date_aw, date_ak);
                    call.enqueue(new Callback<DataTindakan>() {
                        @Override
                        public void onResponse(Call<DataTindakan> call, Response<DataTindakan> response) {
                            String status = "", total = "", nama_dokters = "", judul, pendapatan_bpjsr, pendapatan_umumr;
                            status = response.body().getStatus().toString();
                            nama_dokters = response.body().getNama_dokter().toString();
                            judul = response.body().getJudul().toString();
                            pendapatan_bpjsr = response.body().getPendapatan_bpjs();
                            pendapatan_umumr = response.body().getPendapatan_umum();
                            if (response.isSuccessful()) {

                                if (status.equals("ok")) {
                                    emaile.setText(nama_dokters);
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

                                    Toast.makeText(getActivity(), "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                                }

                                hideLoad();
                            }
                        }

                        @Override
                        public void onFailure(Call<DataTindakan> call, Throwable t) {
                            hideLoad();
                            Toast.makeText(getActivity(), "Tidak dapat menjangkau server !", Toast.LENGTH_SHORT).show();

                            Log.d("Failure", t.getMessage());
                        }
                    });
                } catch (Exception e) {
                    hideLoad();
                    Toast.makeText(getActivity(), "Exception to connect !", Toast.LENGTH_SHORT).show();

                    Log.d("Exception", e.getMessage());
                }
            } else {
                Toast.makeText(getActivity(), "Periksa kembali koneksi internet !", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void getListDataDetail() {
        String date_aw = date_Awal.getText().toString();
        String date_ak = date_Akhir.getText().toString();
        if (kd_user != null) {
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
                                    emaile.setText(nama_dokter);
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
                                        madapter = new DetailAdapter(detailTindakanList, listcount, getActivity());
                                        mrecRecyclerView.setAdapter(madapter);
                                        btndetail.setText("Sembunyikan");

                                    } else {
                                        Toast.makeText(getActivity(), "Detail Tindakan kosong ! perbarui tindakan anda", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(getActivity(), "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                                }

                            }
                            newtonCradleLoading.stop();
                            newtonCradleLoading.setVisibility(View.GONE);

                        }

                        @Override
                        public void onFailure(Call<DataTindakan> call, Throwable t) {
                            newtonCradleLoading.stop();
                            newtonCradleLoading.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "Tidak dapat menjangkau server", Toast.LENGTH_SHORT).show();
                            Log.d("Failure", t.getMessage());
                        }
                    });

                } catch (Exception e) {
                    newtonCradleLoading.stop();
                    newtonCradleLoading.setVisibility(View.GONE);
                    Log.w("Exception", e.getMessage());
                }
            } else {
                newtonCradleLoading.stop();
                newtonCradleLoading.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Periksa kembali koneksi internet !", Toast.LENGTH_SHORT).show();
            }
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
        if (kd_user != null) {
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
                                    emaile.setText(" " + nama_dokter);
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
                                    Toast.makeText(getActivity(), "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                                }
                                hideLoad();
                            }
                        }

                        @Override
                        public void onFailure(Call<DataTindakan> call, Throwable t) {
                            hideLoad();
                            Toast.makeText(getActivity(), "Tidak dapat menjangkau server", Toast.LENGTH_SHORT).show();
                            Log.d("Failure", t.getMessage());
                        }
                    });
                } catch (Exception e) {
                    hideLoad();
                    Toast.makeText(getActivity(), "Exception to connect !", Toast.LENGTH_SHORT).show();
                    Log.d("Exception", e.getMessage());
                }
            } else {
                hideLoad();
                Toast.makeText(getActivity(), "Periksa kembali koneksi internet !", Toast.LENGTH_SHORT).show();
            }
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
        if (kd_user != null) {
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
                                    emaile.setText(" " + nama_dokter);
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
                                    Toast.makeText(getActivity(), "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                                }
                                hideLoad();
                            }
                        }

                        @Override
                        public void onFailure(Call<DataTindakan> call, Throwable t) {
                            hideLoad();
                            Toast.makeText(getActivity(), "Tidak dapat menjangkau server", Toast.LENGTH_SHORT).show();
                            Log.d("Failure", t.getMessage());
                        }
                    });
                } catch (Exception e) {
                    hideLoad();
                    Toast.makeText(getActivity(), "Exception to connect", Toast.LENGTH_SHORT).show();
                    Log.d("Exception", e.getMessage());
                }

            } else {
                hideLoad();
                Toast.makeText(getActivity(), "Periksa kembali koneksi internet !", Toast.LENGTH_SHORT).show();
            }
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
        if (kd_user != null) {
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
                                    emaile.setText(" " + nama_dokter);
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
                                    Toast.makeText(getActivity(), "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                                }
                                hideLoad();
                            }
                        }

                        @Override
                        public void onFailure(Call<DataTindakan> call, Throwable t) {
                            hideLoad();
                            dialog_failure();
//                            Toast.makeText(getActivity(), "Tidak dapat menjangkau server !", Toast.LENGTH_SHORT).show();
                            Log.d("Failure", t.getMessage());
                        }
                    });
                } catch (Exception e) {
                    hideLoad();
                    Toast.makeText(getActivity(), "Exception to connect !", Toast.LENGTH_SHORT).show();
                    Log.d("Exception", e.getMessage());
                }
            } else {
                hideLoad();
                Toast.makeText(getActivity(), "Periksa kembali koneksi internet !", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void fetchData() {
        txtloadmore.setVisibility(View.VISIBLE);
        loadMoreProgreses.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                madapter = new DetailAdapter(detailTindakanList, madapter.getItemCount() + countplus, getActivity());
                mrecRecyclerView.setAdapter(madapter);
                mrecRecyclerView.scrollToPosition(madapter.getItemCount() - ((countplus * 2)));
//                mrecRecyclerView.smoothScrollToPosition();
                loadMoreProgreses.setVisibility(View.GONE);
                txtloadmore.setVisibility(View.GONE);
            }
        }, 500);
    }
    //method untuk cek koneksi
    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        //jika ada koneksi return true
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        //jika tidak ada koneksi return false
        return false;
    }
//    dialog failure
    private void dialog_failure() {
        CustomDialogDetail cdd = new CustomDialogDetail(context);
        cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        cdd.show();
    }
}
