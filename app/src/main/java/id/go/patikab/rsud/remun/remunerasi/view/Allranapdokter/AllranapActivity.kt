package id.go.patikab.rsud.remun.remunerasi.view.Allranapdokter

import android.app.DatePickerDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import id.go.patikab.rsud.remun.remunerasi.R
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.Allranap
import id.go.patikab.rsud.remun.remunerasi.data.lokal.sharepreference.SharePref
import kotlinx.android.synthetic.main.layout_allranap.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.support.v4.onRefresh
import java.text.SimpleDateFormat
import java.util.*
import id.go.patikab.rsud.remun.remunerasi.config.adapter.*
import id.go.patikab.rsud.remun.remunerasi.config.util.openpasiendokterranap

class AllranapActivity :AppCompatActivity(),AllranapView{
    val mPresenter by lazy { AllranapPresenter(this) }
    var kd_user: String = ""
    var nama_dokter: String = ""
    var prefs: SharedPreferences? = null
    var email: String = ""
    internal lateinit var mActionBarToolbar: Toolbar
    //    fun getActionBar(): ActionBar? {
//        return (activity as AppCompatActivity).supportActionBar
//    }
    var tanggal = ""
    var tanggal2 = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefs = getSharedPreferences(SharePref.pref, 0)
//        Log.d("tokene", sharedPreferences.getString(my_token, null)!! + " ")
        kd_user = prefs?.getString(SharePref.login_session, null).toString()
        nama_dokter = prefs?.getString(SharePref.nm_dokter, null).toString()
        email = prefs?.getString(SharePref.email_device, "").toString()

        val currentDate2 = SimpleDateFormat("dd-MMM-yyyy")
        val todayDate = Date()
        tanggal2 = currentDate2.format(todayDate)
        setContentView(R.layout.layout_allranap)

        getActionBar()?.setTitle("Pasien Rawat Inap")
        swiperefreshe.onRefresh {
            refresh()
            swiperefreshe.isRefreshing = false
        }
        nmane_dokter.text = nama_dokter
        txt_tanggal.setText(tanggal2)
        refresh()

    }

    fun refresh(){
//        Log.d("tangale",tgl)
        launch(UI) {
            mPresenter.getdata()
        }
    }
    override fun showData(data: List<Allranap.Dataranap>, jumlah: String) {
        txt_jumlah.text = jumlah
        recycle_datae?.let {
            with(recycle_datae) {
                layoutManager = LinearLayoutManager(context)
                adapter =
                        AllpasienranapAdapater(data, data.size) { infor ->
                            //                            openPdetail(infor.copy())
                            openpasiendokterranap(infor.kddokter.toString(),infor.dokter.toString())
//                            toast(data[0].NAMA+" test")
                        }
            }

        }
    }

    override fun hideloading() {
        adah_jumlah.visibility = View.VISIBLE
        txt_jumlah.visibility = View.VISIBLE
        recycle_datae.visibility = View.VISIBLE
        progress_circular.visibility = View.GONE
        no_data.visibility = View.GONE
    }
    override fun showloading() {
        adah_jumlah.visibility = View.GONE
        txt_jumlah.visibility = View.GONE
        recycle_datae.visibility = View.GONE
        progress_circular.visibility = View.VISIBLE
        no_data.visibility = View.GONE
    }
    override fun placeholder() {
        adah_jumlah.visibility = View.GONE
        txt_jumlah.visibility = View.GONE
        recycle_datae.visibility = View.GONE
        progress_circular.visibility = View.GONE
        no_data.visibility = View.VISIBLE
    }

}