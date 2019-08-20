package id.go.patikab.rsud.remun.remunerasi.view.JasaPelayanan

import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.View
import id.go.patikab.rsud.remun.remunerasi.data.lokal.sharepreference.SharePref.*
import id.go.patikab.rsud.remun.remunerasi.R
import id.go.patikab.rsud.remun.remunerasi.config.util.openWebViewlistJaspel
import kotlinx.android.synthetic.main.layout_jaspel.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.ListJaspel
import id.go.patikab.rsud.remun.remunerasi.config.adapter.*
import org.jetbrains.anko.support.v4.onRefresh

class Jaspel : AppCompatActivity(),JaspelView{
    private val mPresenter by lazy { JaspelPresenter(this) }
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
        launch(UI) { mPresenter.getlistjaspel()  }
    }
    override fun showInformasi(listjp: List<ListJaspel.listJaspel>) {
        recycle_datae?.let {
            with(recycle_datae) {
                layoutManager = LinearLayoutManager(context)
                var counte :Int
                if(listjp.size > 20)counte = 20 else counte = listjp.size
                adapter =
                        ListJaspelAdapter(listjp, counte) { infor ->
                            var d = infor.dari
                            var s = infor.sampai
                            var t = infor.token
                            openWebViewlistJaspel(d,s,t)
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