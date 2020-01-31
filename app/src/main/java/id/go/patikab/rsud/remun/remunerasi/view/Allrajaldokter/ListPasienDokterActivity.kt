package id.go.patikab.rsud.remun.remunerasi.view.Allrajaldokter

import android.app.DatePickerDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import id.go.patikab.rsud.remun.remunerasi.R
import id.go.patikab.rsud.remun.remunerasi.config.util.openPdetail
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.ListDokter
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.ListPasien
import id.go.patikab.rsud.remun.remunerasi.data.lokal.sharepreference.SharePref
import id.go.patikab.rsud.remun.remunerasi.view.pasien_rajal.PrajalPresenter
import id.go.patikab.rsud.remun.remunerasi.view.pasien_rajal.PrajalView
import kotlinx.android.synthetic.main.layout_pasien_rajal_all.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.support.v4.onRefresh
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import id.go.patikab.rsud.remun.remunerasi.config.adapter.*
import org.jetbrains.anko.toast

class ListPasienDokterjalActivity : AppCompatActivity(), PrajalView {


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
        setContentView(R.layout.layout_pasien_rajal_all)
//        Log.d("tokene", sharedPreferences.getString(my_token, null)!! + " ")
        kd_user = intent?.getStringExtra("kddokter").toString()
        nama_dokter = intent?.getStringExtra("nmdokter").toString()
        tanggal = intent?.getStringExtra("tanggal").toString()


        getActionBar()?.setTitle("Pasien Rawat Jalan")
        refresh(kd_user)
        swiperefreshe.onRefresh {
            refresh(kd_user)
            swiperefreshe.isRefreshing = false
        }
        nmane_dokter.text = nama_dokter
        dt_tanggal.setText(tanggal)
//        dt_tanggal.setOnClickListener {
//            DatePickerDialog(this, date, myCalendar
//                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//        }
    }

//    val myCalendar = Calendar.getInstance()
//
//    val date = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
//        myCalendar.set(Calendar.YEAR, year)
//        myCalendar.set(Calendar.MONTH, monthOfYear)
//        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
//
//        val myFormat = "MM/dd/yy" //In which you need put here
//        val sdf = SimpleDateFormat(myFormat, Locale.US)
//
//        dt_tanggal.setText(sdf.format(myCalendar.time))
//        refresh(kd_user)
////
//    }




    override fun showPrajal(data: List<ListPasien.Pasiene>) {
        //        Log.d("test nt",informasi.get(0).judul)
        txt_jumlah?.text = "Jumlah Pasien : "+ data.size.toString()
        recycle_datae?.let {
            with(recycle_datae) {
                layoutManager = LinearLayoutManager(context)
                adapter =
                        PasienAdapter(data, data.size) { infor ->
//                            openPdetail(infor.copy())
                            toast(data[0].NAMA)
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

//        var spindokter = ArrayList<String>();
//        var isi = ArrayList<String>();
//        for (i in data){
//            spindokter.add(i.NAMADOKTER)
//            isi.add(i.KDDOKTER)
//        }
//
//        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, spindokter )
////        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        positionSpinner.adapter = adapter
//
//        positionSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//
//            }
//
//            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
//                // either one will work as well
//                // val item = parent.getItemAtPosition(position) as String
//                val item = adapter.getItem(position)
//                kd_user  = isi.get(positionSpinner.selectedItemPosition)
////                toast(alerte)
//                refresh2(kd_user)
//
//            }
//
//        }

    }


}
