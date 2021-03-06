package id.go.patikab.rsud.remun.remunerasi.view.pasien_rajal

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.go.patikab.rsud.remun.remunerasi.R
import id.go.patikab.rsud.remun.remunerasi.data.lokal.sharepreference.SharePref.*
import org.jetbrains.anko.support.v4.ctx
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import id.go.patikab.rsud.remun.remunerasi.config.adapter.PasienRajalAdapter
import id.go.patikab.rsud.remun.remunerasi.config.util.openNotif
import id.go.patikab.rsud.remun.remunerasi.config.util.openPdetail
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.*
import kotlinx.android.synthetic.main.layout_pasien_rajal.*
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.toast
import android.widget.DatePicker
import android.app.DatePickerDialog
import android.widget.AdapterView
import android.widget.ArrayAdapter
import org.jetbrains.anko.toast
import java.util.*
import java.text.SimpleDateFormat
import kotlin.collections.ArrayList


class Prajal : AppCompatActivity(), PrajalView {

    val mPresenter by lazy { PrajalPresenter(this) }
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
        setContentView(R.layout.layout_pasien_rajal)
        prefs = getSharedPreferences(pref, 0)
//        Log.d("tokene", sharedPreferences.getString(my_token, null)!! + " ")
        kd_user = prefs?.getString(login_session, null).toString()
        nama_dokter = prefs?.getString(nm_dokter, null).toString()
        email = prefs?.getString(email_device, "").toString()

        val currentDate = SimpleDateFormat("yyyy-MM-dd")
        val currentDate2 = SimpleDateFormat("dd-MMM-yyyy")
        val todayDate = Date()
        tanggal = currentDate.format(todayDate)
        tanggal2 = currentDate2.format(todayDate)

        getActionBar()?.setTitle("Pasien Rawat Jalan")
        refresh(kd_user)
        swiperefreshe.onRefresh {
            refresh(kd_user)
            swiperefreshe.isRefreshing = false
        }
        nmane_dokter.text = nama_dokter
        dt_tanggal.setText(tanggal2)
        dt_tanggal.setOnClickListener {
            DatePickerDialog(this, date, myCalendar
            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        }
        }

        val myCalendar = Calendar.getInstance()

        val date = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "MM/dd/yy" //In which you need put here
            val sdf = SimpleDateFormat(myFormat, Locale.US)

            dt_tanggal.setText(sdf.format(myCalendar.time))
            updateLabel()
            refresh(kd_user)
//
    }


//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//
//        prefs = ctx.getSharedPreferences(pref, 0)
////        Log.d("tokene", sharedPreferences.getString(my_token, null)!! + " ")
//        kd_user = prefs?.getString(login_session, null).toString()
//        nama_dokter = prefs?.getString(nm_dokter, null).toString()
//        email = prefs?.getString(email_device, "").toString()
//
//        val currentDate = SimpleDateFormat("yyyy-MM-dd")
//        val currentDate2 = SimpleDateFormat("dd-MMM-yyyy")
//        val todayDate = Date()
//        tanggal = currentDate.format(todayDate)
//        tanggal2 = currentDate2.format(todayDate)
//
//        return inflater.inflate(R.layout.layout_pasien_rajal, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        getActionBar()?.setTitle("Pasien Rawat Jalan")
//        refresh(kd_user)
//        swiperefreshe.onRefresh {
//            refresh(kd_user)
//            swiperefreshe.isRefreshing = false
//        }
//        nmane_dokter.text = nama_dokter
//        dt_tanggal.setText(tanggal2)
//        dt_tanggal.setOnClickListener {
//            DatePickerDialog(ctx, date, myCalendar
//            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//        }
//        }
//
//        val myCalendar = Calendar.getInstance()
//
//        val date = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
//            myCalendar.set(Calendar.YEAR, year)
//            myCalendar.set(Calendar.MONTH, monthOfYear)
//            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
//
//            val myFormat = "MM/dd/yy" //In which you need put here
//            val sdf = SimpleDateFormat(myFormat, Locale.US)
//
//            dt_tanggal.setText(sdf.format(myCalendar.time))
//            updateLabel()
//            refresh(kd_user)
//
//    }


    private fun updateLabel() {
        val myFormat = "E, dd MMM yyyy" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
        val format2 = "yyyy-MM-dd"
        val sdf2 = SimpleDateFormat(format2,Locale.getDefault())
        tanggal = sdf2.format(myCalendar.time)
        dt_tanggal.setText(sdf.format(myCalendar.time))

    }
    override fun showPrajal(data: List<ListPasien.Pasiene>) {
        //        Log.d("test nt",informasi.get(0).judul)
        txt_jumlah?.text = "Jumlah Pasien : "+ data.size.toString()
        recycle_datae?.let {
            with(recycle_datae) {
                layoutManager = LinearLayoutManager(context)
                adapter =
                        PasienRajalAdapter(data,data.size) { infor ->
                            toast( infor.NAMA +" ")
//                            openPdetail(infor.copy())
                        }
            }
        }

    }
    fun refresh(kd_user2:String){
        launch(UI) {
            mPresenter.getPrajal(kd_user2,tanggal)
            mPresenter.getDokter()
        }
    }
    fun refresh2(kd_user2: String){
        launch(UI) {
            mPresenter.getPrajal(kd_user2,tanggal)
        }
    }

    fun getSearchPrajal(key:String,tanggal:String){
        launch(UI){
            mPresenter.getSearchPrajal(kd_user,tanggal,key)

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

    override fun showDokter(data: List<ListDokter.Dokter>) {

    }


}
