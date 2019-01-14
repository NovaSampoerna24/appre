package id.go.patikab.rsud.remun.remunerasi.view.RingkasanPelaksana.TaksiranPenghasilan

import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.jetbrains.anko.support.v4.ctx
import id.go.patikab.rsud.remun.remunerasi.data.lokal.sharepreference.SharePref.*
import id.go.patikab.rsud.remun.remunerasi.R
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.RingkasanModel
import kotlinx.android.synthetic.main.layout_taksiran_penghasilan.*
import id.go.patikab.rsud.remun.remunerasi.config.adapter.RingkasanAdapter
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.noButton
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.yesButton
import id.go.patikab.rsud.remun.remunerasi.config.util.openRemid

class TkPenghasilanFragment : Fragment(),TkPenghasilanView {

    val mPresenter by lazy { TkPenghasilanPresenter(this) }
    var kd_user = ""
    var prefs: SharedPreferences? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        prefs = ctx.getSharedPreferences(pref,0)
        kd_user = prefs?.getString(login_session,null).toString()

        return inflater.inflate(R.layout.layout_taksiran_penghasilan, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.actionBar?.setTitle("Ringkasan")
        refresh()
        swiperefreshe.onRefresh {
            refresh()
            swiperefreshe.isRefreshing = false
        }
    }
    fun refresh() {
        if(kd_user !="") {
            launch(UI) { mPresenter.getTaksiranPenghasilan(kd_user) }
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
//        if(list_tk.isShown){
        list_tk?.visibility = View.GONE
        progress_circular?.visibility = View.GONE
        no_data?.visibility = View.VISIBLE
//       }
    }

    override fun show(ringkasan:List<RingkasanModel.Ringkasan>) {
        list_tk?.let{
            with(list_tk){
                layoutManager = LinearLayoutManager(context)
                adapter = RingkasanAdapter(ringkasan,ringkasan.size){
                    ringkasan ->
//                    toast(ringkasan.tanggal_awal +" - " + ringkasan.tanggal_akhir)
                    activity?.openRemid(ringkasan.tanggal_awal,ringkasan.tanggal_akhir)
                }

            }
        }

    }

}