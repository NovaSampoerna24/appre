package id.go.patikab.rsud.remun.remunerasi.view.RingkasanPelaksana.Tindakan

import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.go.patikab.rsud.remun.remunerasi.R
import id.go.patikab.rsud.remun.remunerasi.config.util.openRemid
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.RingkasanModel
import id.go.patikab.rsud.remun.remunerasi.data.lokal.sharepreference.SharePref
import id.go.patikab.rsud.remun.remunerasi.view.RingkasanPelaksana.TaksiranPenghasilan.TkPenghasilanPresenter
import id.go.patikab.rsud.remun.remunerasi.view.RingkasanPelaksana.TaksiranPenghasilan.TkPenghasilanView
import kotlinx.android.synthetic.main.layout_taksiran_penghasilan.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import id.go.patikab.rsud.remun.remunerasi.config.adapter.*

class TkTindakanFragment:Fragment(), TkTindakanView {

    val mPresenter by lazy { TkTindakanPresenter(this) }
    var kd_user = ""
    var prefs: SharedPreferences? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        prefs = ctx.getSharedPreferences(SharePref.pref, 0)
        kd_user = prefs?.getString(SharePref.login_session, null).toString()

        return inflater.inflate(R.layout.layout_taksiran_penghasilan, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        refresh()
        swiperefreshe.onRefresh {
            refresh()
            swiperefreshe.isRefreshing = false
        }
    }

    fun refresh() {
        if (kd_user != "") {
            launch(UI) { mPresenter.getTaksiranTindakan(kd_user) }
        }
    }

    override fun showLoading() {
        list_tk?.visibility = View.GONE
        progress_circular?.visibility = View.VISIBLE
        no_data?.visibility = View.GONE
    }

    override fun hideLoading() {
        list_tk?.visibility = View.VISIBLE
        progress_circular?.visibility = View.GONE
        no_data?.visibility = View.GONE
    }

    override fun showplaceholder() {
        list_tk?.visibility = View.GONE
        progress_circular?.visibility = View.GONE
        no_data?.visibility = View.VISIBLE
    }

    override fun show(ringkasan: List<RingkasanModel.Ringkasan>) {
        list_tk?.let {
            with(list_tk) {
                layoutManager = android.support.v7.widget.LinearLayoutManager(context)
                adapter = RingkasanAdapter(ringkasan, ringkasan.size) { ringkasan ->
                    //                    toast(ringkasan.tanggal_awal +" - " + ringkasan.tanggal_akhir)
                    activity?.openRemid(ringkasan.tanggal_awal, ringkasan.tanggal_akhir)
                }

            }
        }

    }

}