package id.go.patikab.rsud.remun.remunerasi.view.DetailProfil

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso

import id.go.patikab.rsud.remun.remunerasi.R
import id.go.patikab.rsud.remun.remunerasi.config.util.*
import id.go.patikab.rsud.remun.remunerasi.data.lokal.sharepreference.SharePref.*
import id.go.patikab.rsud.remun.remunerasi.data.api.ApiClient.*
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import id.go.patikab.rsud.remun.remunerasi.view.page_dialog.*
import org.jetbrains.anko.support.v4.toast
import kotlinx.android.synthetic.main.layout_detail_profil.*
import org.jetbrains.anko.support.v4.onRefresh
import id.go.patikab.rsud.remun.remunerasi.config.adapter.*

class DetailProfil : Fragment(), DetailView {

    val mPresenter by lazy { DetailPresenter(this) }
    internal lateinit var sharedPreferences: SharedPreferences
    internal var kd_user: String = ""
    internal var nama_dokter: String = ""
    var foton = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        sharedPreferences = activity!!.getSharedPreferences(pref, Context.MODE_PRIVATE)
//        Log.d("tokene", sharedPreferences.getString(my_token, null)!! + " ")
        kd_user = sharedPreferences?.getString(login_session, null)
        nama_dokter = sharedPreferences?.getString(nm_dokter, null)
        return inflater.inflate(R.layout.layout_detail_profil, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        txt_overview.text = mOverview
        if (savedInstanceState == null) {
            if (txt_nama.text == "") {
                txt_nama.text = nama_dokter
            }
            if (foton == "") {
                if (isOnline() == true) {
                    refresh()
                } else {
                    dialog_failure()
                }
            } else {
                Picasso.get()
                        .load(base_Url_upload + foton)
                        .error(R.drawable.dokterdefault)
                        .into(profile_image2)
            }

        }
        swiperefresh.onRefresh {
            refresh()
            swiperefresh.isRefreshing = false
        }

        profile_image2.setOnClickListener {
            activity?.openUbahFoto(kd_user, nama_dokter);
        }
    }

    fun refresh() {
        launch(UI) {
            mPresenter.fetchProfilRetro(kd_user)
            mPresenter.fetchGaji(kd_user)
            mPresenter.getPengumuman(kd_user)
        }
    }

    override fun showDetail(detail: ProfilDetail) {
        if (foton == "") {
            Picasso.get()
                    .load(base_Url_upload + detail.foto)
                    .error(R.drawable.dokterdefault)
                    .into(profile_image2)
        }
    }

    override fun showGaji(getgaji: TindakanGetData) {
        val gaji = if (getgaji.total == "0") "Rp 0.0" else getgaji.total
        total_gaji.text = gaji
        gajiBpjs.text = getgaji.pendapatan_bpjs
        gajiumum.text = getgaji.pendapatan_umum
    }

    fun isOnline(): Boolean {
        val cm = activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        //jika ada koneksi return true
        return if (netInfo != null && netInfo.isConnectedOrConnecting) {
            true
        } else false
        //jika tidak ada koneksi return false
    }

    fun dialog_failure() {
        val cdd = CustomDialogDetail(context)
        cdd.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        cdd.show()
    }
    override fun showInformasi(informasi: List<NotifikasiResponse.Notif>) {
        recycle_data?.let {
            with(recycle_data) {
                layoutManager = LinearLayoutManager(context)
                var counte = 0
                if(informasi.size < 5) counte=informasi.size else counte = 5
                Log.d("count","test"+counte)
                adapter =
                        InformasiAdapter(informasi, counte ) { infor ->
                            activity?.openNotif(infor)
//                            toast(infor.id + " test")
                        }
            }
        }
    }


}