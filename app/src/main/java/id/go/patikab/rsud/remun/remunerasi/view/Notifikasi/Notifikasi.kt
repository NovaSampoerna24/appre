package id.go.patikab.rsud.remun.remunerasi.view.Notifikasi

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.go.patikab.rsud.remun.remunerasi.R
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.NotifikasiResponse
import id.go.patikab.rsud.remun.remunerasi.data.lokal.sharepreference.SharePref.pref
import id.go.patikab.rsud.remun.remunerasi.data.lokal.sharepreference.SharePref.login_session
import id.go.patikab.rsud.remun.remunerasi.data.lokal.sharepreference.SharePref.nm_dokter
import kotlinx.android.synthetic.main.layout_notifikasi.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import id.go.patikab.rsud.remun.remunerasi.config.adapter.InformasiAdapter
import id.go.patikab.rsud.remun.remunerasi.config.util.openNotif
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh

class Notifikasi : Fragment(),NotifikasiView {
    private val mPresenter by lazy { NotifikasiPresenter(this) }
    var kd_user:String =""
    var nama_dokter:String = ""
    var prefs :SharedPreferences? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        prefs = ctx.getSharedPreferences(pref,0)
//        Log.d("tokene", sharedPreferences.getString(my_token, null)!! + " ")
        kd_user = prefs?.getString(login_session, null).toString()
        nama_dokter = prefs?.getString(nm_dokter, null).toString()

        return inflater.inflate(R.layout.layout_notifikasi, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        refresh()

        swiperefreshe.onRefresh {
            refresh()
            swiperefreshe.isRefreshing = false
        }

    }
    fun refresh(){
        launch(UI) { mPresenter.getPengumuman(kd_user) }
    }
    override fun showInformasi(informasi: List<NotifikasiResponse.Notif>) {
        recycle_datae?.let {
            with(recycle_datae) {
                layoutManager = LinearLayoutManager(context)
                var counte :Int
                if(informasi.size > 20)counte = 20 else counte = informasi.size
                adapter =
                        InformasiAdapter(informasi,counte) { infor ->
                            activity?.openNotif(infor)
//                            toast(infor.id+" test")
                        }
            }
        }
    }
    override fun hideloading() {
        recycle_datae.visibility = View.VISIBLE
        progress_circular.visibility = View.GONE
        no_data.visibility = View.GONE
   }

    override fun showloading() {
        recycle_datae.visibility = View.GONE
        progress_circular.visibility = View.VISIBLE
        no_data.visibility = View.GONE
   }

    override fun placeholder() {
        recycle_datae.visibility = View.GONE
        progress_circular.visibility = View.GONE
        no_data.visibility = View.VISIBLE
    }



}