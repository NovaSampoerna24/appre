package id.go.patikab.rsud.remun.remunerasi.view.PembayaranDokter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
import id.go.patikab.rsud.remun.remunerasi.R;
import id.go.patikab.rsud.remun.remunerasi.config.adapter.DetailAdapter;
import id.go.patikab.rsud.remun.remunerasi.data.api.ApiClient;
import id.go.patikab.rsud.remun.remunerasi.data.api.ApiInterface;
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.Inacbgs;
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.TindakanGetData;
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.DetailTindakan;
import id.go.patikab.rsud.remun.remunerasi.view.page_dialog.CustomDialogDetail;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static id.go.patikab.rsud.remun.remunerasi.data.lokal.sharepreference.SharePref.login_session;
import static id.go.patikab.rsud.remun.remunerasi.data.lokal.sharepreference.SharePref.my_token;
import static id.go.patikab.rsud.remun.remunerasi.data.lokal.sharepreference.SharePref.nm_dokter;
import static id.go.patikab.rsud.remun.remunerasi.data.lokal.sharepreference.SharePref.pref;

public class PembayaranFragment extends Fragment {

    String judul = "",
            pendapatan_bpjsr = "",
            pendapatan_umumr = "",
            total = "",
            nama_dokter = "",
            status = "",
            kd_user = "",
            txt_btn_detail,
            txt_btn_filter = "hide",
            ngarep,
            mburi;
    TextView judule,
            emaile,
            pendapatan_bpjs,
            pendapatan_umum,
            total_pendapatan,
            txtumum,
            txtbpjs,
            txtloadmore,
            ttpd_inacbgs;

    Button btndetail,
            btn_date_awal,
            btn_date_akhir;

    EditText date_Awal,
            date_Akhir;
    ProgressBar loadMoreProgreses;

    Unbinder ubin;

    LinearLayout ln_f,
            loaded;
    RelativeLayout liner;


    DatePickerDialog awaldatepicker,
            akhirdatepicker;
    SimpleDateFormat dateFormater;
    LinearLayoutManager layoutManager;

    NestedScrollView scrolloadm;
    List<DetailTindakan> detailTindakanList;

    RecyclerView mrecRecyclerView;
    RecyclerView.Adapter madapter;
    RecyclerView.LayoutManager mlayoutManager;

    int listcount = 5,
            countplus = 4,
            currentItems,
            totalItems,
            scrollItems,
            wulan,
            dino,
            taun,
            kr,
            rn,
            dino_d_b,
            dr;
    Calendar cb;
    DrawerLayout drawer;
    NavigationView navigationView;

    ApiInterface apiInterface;
    SharedPreferences sharedPreferences;

    NewtonCradleLoading newtonCradleLoading;

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
        txt_btn_filter = btnfilters.getText().toString();
        if (txt_btn_filter == "hide") {
            ln_f.setVisibility(View.GONE);
            btnfilters.setText("show");
        } else {
            ln_f.setVisibility(View.VISIBLE);
            btnfilters.setText("hide");
        }
    }

    @OnClick(R.id.text_filter)
    public void btnfilter1() {
        txt_btn_filter = btnfilters.getText().toString();
        if (txt_btn_filter == "hide") {
            ln_f.setVisibility(View.GONE);
            btnfilters.setText("show");
        } else {
            ln_f.setVisibility(View.VISIBLE);
            btnfilters.setText("hide");
        }
    }

    @OnClick(R.id.btn_detail)
    public void btn_detail() {
        txt_btn_detail = btndetail.getText().toString();
        if (txt_btn_detail == "Detail") {
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

        View view = inflater.inflate(R.layout.pembayaran_layout, container, false);
        findViewById1(view);
        ubin = ButterKnife.bind(this, view);
        // Inflate the layout for this fragment
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        btndetail.setText("Detail");
        getActionBar().setTitle("Remunerasi");
        date_Awal.setFocusable(false);
        date_Akhir.setFocusable(false);
        dateFormater = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        newtonCradleLoading.setLoadingColor(R.color.colorAccent);

        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mlayoutManager = new LinearLayoutManager(getActivity());
        mrecRecyclerView.setLayoutManager(layoutManager);

        sharedPreferences = getActivity().getSharedPreferences(pref, Context.MODE_PRIVATE);
        Log.d("tokene", sharedPreferences.getString(my_token, null) + " ");
        kd_user = sharedPreferences.getString(login_session, null);
        emaile.setText(sharedPreferences.getString(nm_dokter, null));
//        load more
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

        cb = Calendar.getInstance();
        wulan = cb.get(Calendar.MONTH) + 1;
        taun = cb.get(Calendar.YEAR);
        dino = cb.get(Calendar.DATE);

//        jumlah hari dalam sebulan
        dino_d_b = cb.getActualMaximum(Calendar.DAY_OF_MONTH);
//        tanggal awal minggu
        kr = cb.get(Calendar.DAY_OF_WEEK) ;
//        satu hari sebelumnya
        dr = dino - 1;
        if(dino <= 0 ){
            dino =1;
       }
//       hari minggu pertama
        rn = (dino - kr)+1;
        if (rn <= 0) {
            rn = 1;
        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null) {
            hariIni();
            getInacbgs();
        }
    }

    private void getInacbgs() {
        if (isOnline() == true) {
            try {
                Call<Inacbgs> call = apiInterface.getTarifInacgs();
                call.enqueue(new Callback<Inacbgs>() {
                    @Override
                    public void onResponse(Call<Inacbgs> call, Response<Inacbgs> response) {
                        String tarifo = response.body().getTr().get(0).getTarif_rp();
                        String status = response.body().getTr().get(0).getStatus();

                        ttpd_inacbgs.setText(tarifo);
                        Log.d(TAG, status + " jj");
                    }

                    @Override
                    public void onFailure(Call<Inacbgs> call, Throwable t) {
                        Log.d(TAG, t.getMessage());
                    }
                });
            } catch (Exception e) {
                Log.d(TAG, e.getMessage());
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void findViewById1(View view) {

        date_Awal = (EditText) view.findViewById(R.id.date_awal);
        date_Akhir = (EditText) view.findViewById(R.id.date_akhir);

//        progressBar1 = (ProgressBar) view.findViewById(R.id.progress_bar1);
//        progressBar2 = (ProgressBar) view.findViewById(R.id.progress_bar2);
//        progressBar3 = (ProgressBar) view.findViewById(R.id.progress_bar3);

        loadMoreProgreses = (ProgressBar) view.findViewById(R.id.loadMore);

        txtloadmore = (TextView) view.findViewById(R.id.txtloadmore);

        btn_date_awal = (Button) view.findViewById(R.id.start_d);
        btn_date_akhir = (Button) view.findViewById(R.id.end_d);

//        toolbar = (Toolbar) view.findViewById(R.id.toolbar);

        newtonCradleLoading = (NewtonCradleLoading) view.findViewById(R.id.newton_cradle_loading);

        emaile = (TextView) view.findViewById(R.id.email_d);
        judule = (TextView) view.findViewById(R.id.judul);
        total_pendapatan = (TextView) view.findViewById(R.id.pendapatan_total);
        pendapatan_umum = (TextView) view.findViewById(R.id.pendapatan_umum);
        pendapatan_bpjs = (TextView) view.findViewById(R.id.pendapatan_bpjs);
        txtbpjs = (TextView) view.findViewById(R.id.txtbpjs);
        txtumum = (TextView) view.findViewById(R.id.txtumum);
        ttpd_inacbgs = (TextView) view.findViewById(R.id.pendapatan_total_incbgs);
        ln_f = (LinearLayout) view.findViewById(R.id.ln_filter);

        mrecRecyclerView = (RecyclerView) view.findViewById(R.id.list_detail);
        btndetail = (Button) view.findViewById(R.id.btn_detail);

        drawer = (DrawerLayout) view.findViewById(R.id.drawer_layout);

        navigationView = (NavigationView) view.findViewById(R.id.nav_view);

        liner = (RelativeLayout) view.findViewById(R.id.liner);
        loaded = (LinearLayout) view.findViewById(R.id.loaded);

        scrolloadm = (NestedScrollView) view.findViewById(R.id.nestedScroll);
    }

    private void showDialogDateAkhir() {
        Calendar akhircalendar = Calendar.getInstance();
        Calendar ak = Calendar.getInstance();
        akhirdatepicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                Calendar newDateAwal = Calendar.getInstance();
                newDateAwal.set(i, i1, i2);
                date_Akhir.setText(dateFormater.format(newDateAwal.getTime()));
                showLoad();
                getdatadetail();
            }
        },
                akhircalendar.get(Calendar.YEAR),
                akhircalendar.get(Calendar.MONTH),
                akhircalendar.get(Calendar.DAY_OF_MONTH));

        int al = ak.getActualMaximum(Calendar.DAY_OF_MONTH);
        ak.set(Calendar.DATE, al);

        akhircalendar.add(Calendar.YEAR, 0);
        akhircalendar.add(Calendar.MONTH, 0);
        akhircalendar.set(Calendar.DATE, 1);

        akhirdatepicker.getDatePicker().setMinDate(akhircalendar.getTimeInMillis());
        akhirdatepicker.getDatePicker().setMaxDate(ak.getTimeInMillis());
        akhirdatepicker.show();
    }

    private void showDialogDateAwal() {
        final Calendar awalcalendar = Calendar.getInstance();
        final Calendar ak = Calendar.getInstance();
        awaldatepicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                Calendar newDateAwal = Calendar.getInstance();
                newDateAwal.set(i, i1, i2);
                date_Awal.setText(dateFormater.format(newDateAwal.getTime()));
//                showLoad();
//                getdatadetail();
            }
        },
                awalcalendar.get(Calendar.YEAR),
                awalcalendar.get(Calendar.MONTH),
                awalcalendar.get(Calendar.DAY_OF_MONTH));

        awalcalendar.add(Calendar.YEAR, 0);
        awalcalendar.add(Calendar.MONTH, 0);
        awalcalendar.set(Calendar.DATE, 1);

        int al = ak.getActualMaximum(Calendar.DAY_OF_MONTH);
        ak.set(Calendar.DATE, al);

        awaldatepicker.getDatePicker().setMinDate(awalcalendar.getTimeInMillis());
        awaldatepicker.getDatePicker().setMaxDate(ak.getTimeInMillis());
        awaldatepicker.show();
    }

    private void getdatadetail() {
        ngarep = date_Awal.getText().toString().trim();
        mburi = date_Akhir.getText().toString().trim();
        getgaji(ngarep, mburi);
    }

    private void getListDataDetail() {
        String date_aw = date_Awal.getText().toString();
        String date_ak = date_Akhir.getText().toString();
        if (kd_user != null) {
            if (isOnline() == true) {
                try {
                    Call<TindakanGetData> call = apiInterface.getDataTindakan(kd_user, date_aw, date_ak);
                    call.enqueue(new Callback<TindakanGetData>() {
                        @Override
                        public void onResponse(Call<TindakanGetData> call, Response<TindakanGetData> response) {

                            if (response.isSuccessful()) {
                                pendapatan_bpjsr = response.body().getPendapatan_bpjs();
                                pendapatan_umumr = response.body().getPendapatan_umum();
                                total = response.body().getTotal().toString();
                                judul = response.body().getJudul().toString();
                                status = response.body().getStatus().toString();
                                detailTindakanList = response.body().getDetailTindakanList();
                                if (status.equals("ok")) {
                                    judule.setText(judul);
                                    pendapatan_bpjs.setText(pendapatan_bpjsr);
                                    pendapatan_umum.setText(pendapatan_umumr);
                                    if (total == "0") {
                                        total_pendapatan.setText("Rp. 0");
                                    } else {
                                        total_pendapatan.setText(total);
                                    }
                                    if (detailTindakanList.size() > 0) {
                                        mrecRecyclerView.setVisibility(View.VISIBLE);
//                                    mrecRecyclerView.setMinimumHeight(1000);
                                        Log.d("total :", response.body().getTotal().toString());
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
                        public void onFailure(Call<TindakanGetData> call, Throwable t) {
                            newtonCradleLoading.stop();
                            newtonCradleLoading.setVisibility(View.GONE);
                            dialog_failure();
//                            Toast.makeText(getActivity(), "Tidak dapat menjangkau server", Toast.LENGTH_SHORT).show();
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
                dialog_failure();
//                Toast.makeText(getActivity(), "Periksa kembali koneksi internet !", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void hideLoad() {

        total_pendapatan.setVisibility(View.VISIBLE);
        pendapatan_bpjs.setVisibility(View.VISIBLE);
        pendapatan_umum.setVisibility(View.VISIBLE);
    }

    private void showLoad() {

        total_pendapatan.setVisibility(View.GONE);
        pendapatan_bpjs.setVisibility(View.GONE);
        pendapatan_umum.setVisibility(View.GONE);
    }

    private void getgaji(String ngarep, String mburi) {
        showLoad();
        if (kd_user != null) {
            if (isOnline() == true) {
                try {
                    Call<TindakanGetData> call = apiInterface.getDataTindakan(kd_user, ngarep, mburi);
                    call.enqueue(new Callback<TindakanGetData>() {
                        @Override
                        public void onResponse(Call<TindakanGetData> call, Response<TindakanGetData> response) {
                            if (response.isSuccessful()) {
                                pendapatan_bpjsr = response.body().getPendapatan_bpjs();
                                pendapatan_umumr = response.body().getPendapatan_umum();
                                status = response.body().getStatus().toString();
                                nama_dokter = response.body().getNama_dokter().toString();
                                judul = response.body().getJudul().toString();
                                total = response.body().getTotal().toString();
                                if (status.equals("ok")) {
                                    judule.setText(judul);
                                    pendapatan_bpjs.setText(pendapatan_bpjsr);
                                    pendapatan_umum.setText(pendapatan_umumr);
                                    if (total == "0") {
                                        total_pendapatan.setText("Rp. 0");
                                    } else {
                                        total_pendapatan.setText(total);
                                    }

                                } else {
                                    Toast.makeText(getActivity(), "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                                }
                                hideLoad();
                            }
                        }

                        @Override
                        public void onFailure(Call<TindakanGetData> call, Throwable t) {
                            hideLoad();
                            dialog_failure();
//                            Toast.makeText(getActivity(), "Tidak dapat menjangkau server", Toast.LENGTH_SHORT).show();
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
                dialog_failure();
//                Toast.makeText(getActivity(), "Periksa kembali koneksi internet !", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getiListBulanan() {
        ngarep = taun + "-" + wulan + "-" + 1;
        mburi = taun + "-" + wulan + "-" + dino_d_b;

        date_Awal.setText(ngarep);
        date_Akhir.setText(mburi);

        getgaji(ngarep, mburi);

    }

    private void getiListMingguan() {

        ngarep = taun + "-" + wulan + "-" + rn;
        mburi = taun + "-" + wulan + "-" + dino;

        date_Awal.setText(ngarep);
        date_Akhir.setText(mburi);

        getgaji(ngarep, mburi);

    }

    private void getiListHariIni() {
        ngarep = taun + "-" + wulan + "-" + dr;
        mburi = taun + "-" + wulan + "-" + dino;

        date_Awal.setText(ngarep);
        date_Akhir.setText(mburi);

        getgaji(ngarep, mburi);
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

    //dialog failure
    private void dialog_failure() {
        CustomDialogDetail cdd = new CustomDialogDetail(getActivity());
        cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        cdd.show();
    }
}
