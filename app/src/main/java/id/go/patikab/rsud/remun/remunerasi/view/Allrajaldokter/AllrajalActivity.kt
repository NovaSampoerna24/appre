package id.go.patikab.rsud.remun.remunerasi.view.Allrajaldokter


import android.app.DatePickerDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.Allrajal
import id.go.patikab.rsud.remun.remunerasi.data.lokal.sharepreference.SharePref
import kotlinx.android.synthetic.main.layout_allrajal.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.support.v4.onRefresh
import java.text.SimpleDateFormat
import java.util.*
import id.go.patikab.rsud.remun.remunerasi.R
import id.go.patikab.rsud.remun.remunerasi.config.adapter.*
import id.go.patikab.rsud.remun.remunerasi.config.util.openpasiendokterrajal
import id.go.patikab.rsud.remun.remunerasi.config.util.openpasiendokterranap


class AllrajalActivity :AppCompatActivity(), AllrajalView {

    val mPresenter by lazy { AllrajalPresenter(this) }
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
        setContentView(R.layout.layout_allrajal)

        prefs = getSharedPreferences(SharePref.pref, 0)
//        Log.d("tokene", sharedPreferences.getString(my_token, null)!! + " ")
        kd_user = prefs?.getString(SharePref.login_session, null).toString()
        nama_dokter = prefs?.getString(SharePref.nm_dokter, null).toString()
        email = prefs?.getString(SharePref.email_device, "").toString()

        val currentDate = SimpleDateFormat("yyyy-MM-dd")
        val currentDate2 = SimpleDateFormat("dd-MMM-yyyy")
        val todayDate = Date()
        tanggal = currentDate.format(todayDate)
        tanggal2 = currentDate2.format(todayDate)

        getActionBar()?.setTitle("Pasien Rawat Jalan")
        swiperefreshe.onRefresh {
            refresh(tanggal)
            swiperefreshe.isRefreshing = false
        }
        nmane_dokter.text = nama_dokter
        dt_tanggal.setText(tanggal2)
        dt_tanggal.setOnClickListener {
            DatePickerDialog(this, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        }
        refresh(tanggal)

    }

    val myCalendar = Calendar.getInstance()

    val date = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
        myCalendar.set(Calendar.YEAR, year)
        myCalendar.set(Calendar.MONTH, monthOfYear)
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        val myFormat = "dd-MMM-yyyy" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
        val format2 = "yyyy-MM-dd"
        val sdf2 = SimpleDateFormat(format2,Locale.getDefault())
        tanggal = sdf2.format(myCalendar.time)
        tanggal2 = sdf.format(myCalendar.time)
        dt_tanggal.setText(tanggal2)
        refresh(tanggal)
//
    }

    fun refresh(tgl: String){
    Log.d("tangale",tgl)
        launch(UI) {
            mPresenter.getdata(tgl)
        }
    }
    fun refresh2(kd_user2: String){
        launch(UI) {
//            mPresenter.getPrajal(kd_user2,tanggal)
        }
    }

    fun getSearchPrajal(key:String,tanggal:String){
        launch(UI){
//            mPresenter.getSearchPrajal(kd_user,tanggal,key)

        }
    }

    override fun showData(data: List<Allrajal.Datarajal>,jumlah:String) {
//        txt_jumlah?.text = "Jumlah Pasien : "+ data.size.toString()
        txt_jumlah.text = jumlah
        recycle_datae?.let {
            with(recycle_datae) {
                layoutManager = LinearLayoutManager(context)
                adapter =
                            AllpasienrajalAdapater(data, data.size) { infor ->
//                            openPdetail(infor.copy())
                                openpasiendokterrajal(infor.kddokter.toString(),infor.dokter.toString(),tanggal)
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