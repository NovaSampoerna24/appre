package id.go.patikab.rsud.remun.remunerasi.view.pasien_detail
import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import id.go.patikab.rsud.remun.remunerasi.R
import id.go.patikab.rsud.remun.remunerasi.data.lokal.sharepreference.SharePref.*
import org.jetbrains.anko.support.v4.ctx
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.android.synthetic.main.detail_pasien.*
import id.go.patikab.rsud.remun.remunerasi.config.adapter.PasienAdapter
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.*
import id.go.patikab.rsud.remun.remunerasi.view.pasien_rajal.PranapView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.toast
import id.go.patikab.rsud.remun.remunerasi.view.pasien_detail.PdetailrajalPresenter

class Pdetailrajal : AppCompatActivity(), PdetailrajalView {


    val mPresenter by lazy { PdetailrajalPresenter(this) }
    var kd_user: String = ""
    var nama_dokter: String = ""
    var prefs: SharedPreferences? = null
    var email: String = ""
    var idxdaftar = ""
    var nomr = ""
    internal lateinit var mActionBarToolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_pasien)
        prefs = getSharedPreferences(pref, 0)
//        Log.d("tokene", sharedPreferences.getString(my_token, null)!! + " ")
        kd_user = prefs?.getString(login_session, null).toString()
        nama_dokter = prefs?.getString(nm_dokter, null).toString()
        email = prefs?.getString(email_device, "").toString()
        idxdaftar = intent?.getStringExtra("idxdaftar") ?: ""
        nomr = intent?.getStringExtra("nomr") ?: ""

        launch(UI) {
            mPresenter.getPdetail(idxdaftar,nomr)

        }

    }


    override fun showDataPendaftaran(data: DetailPasienRajal.dataPendaftaran) {
//        Picasso.get().load(player.bannerUrl).into(iv_banner)
//        tv_position.text = data.position

        tv_weight.text = data.masukpoly
        tv_height.text = data.keluarpoly
        txt_kunjungan.text = data.tanggalmasuk
        txt_nama_pasien.text = data.NAMA
        txt_nomr.text = data.NOMR
        app_bar.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            internal var isShow = true
            internal var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    toolbar_layout.title = data.NAMA
                    getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
                    getSupportActionBar()?.setDisplayShowHomeEnabled(true);
                    isShow = true
                } else if (isShow) {
                    toolbar_layout.title = " "//careful there should a space between double quote otherwise it wont work
                    isShow = false
                }
            }
        })
//        tv_overview.text = data.overview

//        supportActionBar?.title = player.name
    }

    override fun hideloading() {
        progress.visibility = View.GONE
        fr2.visibility = View.VISIBLE
//        recycle_datae.visibility = View.VISIBLE
//        progress_circular.visibility = View.GONE
//        no_data.visibility = View.GONE
    }
    override fun showloading() {
        progress.visibility = View.VISIBLE
        fr2.visibility = View.GONE
//        recycle_datae.visibility = View.GONE
//        progress_circular.visibility = View.VISIBLE
//        no_data.visibility = View.GONE
    }
    override fun placeholder() {
        progress.visibility = View.GONE
        fr2.visibility = View.VISIBLE
//        recycle_datae.visibility = View.GONE
//        progress_circular.visibility = View.GONE
//        no_data.visibility = View.VISIBLE
    }
}
