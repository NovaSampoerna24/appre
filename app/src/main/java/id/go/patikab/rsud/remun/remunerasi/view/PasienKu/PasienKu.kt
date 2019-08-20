package id.go.patikab.rsud.remun.remunerasi.view.PasienKu

import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.View
import id.go.patikab.rsud.remun.remunerasi.data.lokal.sharepreference.SharePref.*
import id.go.patikab.rsud.remun.remunerasi.R
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.PasienKuResponse
import kotlinx.android.synthetic.main.layout_notifikasi.*
import org.jetbrains.anko.support.v4.onRefresh
import id.go.patikab.rsud.remun.remunerasi.config.adapter.PasienKuAdapter
import id.go.patikab.rsud.remun.remunerasi.config.util.openringkasanjp
import org.jetbrains.anko.alert

class PasienKu : AppCompatActivity(),PasienKuView{
    private val mPresenter by lazy { PasienKuPresenter(this) }
    var kd_user:String =""
    var nama_dokter:String = ""
    var prefs : SharedPreferences? = null
    internal lateinit var mActionBarToolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefs = getSharedPreferences(pref,0)
//        Log.d("tokene", sharedPreferences.getString(my_token, null)!! + " ")
        kd_user = prefs?.getString(login_session, null).toString()
        nama_dokter = prefs?.getString(nm_dokter, null).toString()

        setContentView(R.layout.layout_jp2)

        refresh()
        swiperefreshe.onRefresh {
            refresh()
            swiperefreshe.isRefreshing = false
        }
        mActionBarToolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(mActionBarToolbar)
        supportActionBar?.setTitle("Rekapan Jasa Pelaksana")
    }

    fun refresh(){
        launch(UI) { mPresenter.getPasienku(kd_user)  }
    }
    override fun showInformasi(listjp: List<PasienKuResponse.Pasienku>) {
        recycle_datae?.let {
            with(recycle_datae) {
                layoutManager = LinearLayoutManager(context)
                var counte :Int
                if(listjp.size > 20)counte = 20 else counte = listjp.size
                adapter =
                        PasienKuAdapter(listjp) { infor ->
                            var judul = infor.judul
                            if(judul == "Pasien Rawat Jalan"){

//                                alert { "Rawat Jalan" }

                            }else if(judul == "Pasien Rawat Inap"){
//                                alert { "Rawat Inap" }

                            }

//                            toast(infor.id+" test")
                        }
            }
        }
    }

    override fun hideloading() {
        recycle_datae?.visibility = View.VISIBLE
        progress_circular?.visibility = View.GONE
        no_data?.visibility = View.GONE
    }

    override fun showloading() {
        recycle_datae?.visibility = View.GONE
        progress_circular?.visibility = View.VISIBLE
        no_data?.visibility = View.GONE
    }

    override fun placeholder() {
        recycle_datae?.visibility = View.GONE
        progress_circular?.visibility = View.GONE
        no_data?.visibility = View.VISIBLE
    }

}