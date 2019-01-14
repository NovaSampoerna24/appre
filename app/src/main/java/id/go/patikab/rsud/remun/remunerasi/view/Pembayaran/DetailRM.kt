package id.go.patikab.rsud.remun.remunerasi.view.Pembayaran

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.ListPasienDokter
import id.go.patikab.rsud.remun.remunerasi.R
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.os.PersistableBundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate
import com.appeaser.sublimepickerlibrary.helpers.SublimeOptions
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker
import id.go.patikab.rsud.remun.remunerasi.config.adapter.DetailAdapter
import org.jetbrains.anko.support.v4.ctx
import kotlinx.android.synthetic.main.layout_remid.*
import java.util.Calendar
import id.go.patikab.rsud.remun.remunerasi.view.page_dialog.DialogListTindakan
import id.go.patikab.rsud.remun.remunerasi.config.adapter.PembayaranRemidAdapter
import id.go.patikab.rsud.remun.remunerasi.data.lokal.sharepreference.SharePref.*
import id.go.patikab.rsud.remun.remunerasi.view.page_dialog.SublimePickerFragment
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat
import id.go.patikab.rsud.remun.remunerasi.view.page_dialog.SublimePickerFragment.Callback
import org.jetbrains.anko.support.v4.onRefresh
import id.go.patikab.rsud.remun.remunerasi.config.util.*
import kotlinx.android.synthetic.main.list_detail.*
import org.jetbrains.anko.scrollView

class DetailRM : AppCompatActivity(), DetailRMView, AdapterView.OnItemSelectedListener {
    var cb = Calendar.getInstance()
    var wulan = cb.get(Calendar.MONTH) + 1
    var taun = cb.get(Calendar.YEAR)
    var dino = cb.get(Calendar.DATE)
    var dr = dino
    var tujuharisebelum = dino - 7
    var dino_d_b = cb.getActualMaximum(Calendar.DAY_OF_MONTH)
    var tgl_awal = ""
    var tgl_akhir = ""
    var title = ""
    var kd_user: String = ""
    var nama_dokter: String = ""
    var device_token: String = ""
    var signatured: String = ""
    val listfilter = arrayOf("Filter", "Hari ini", "Kemarin", "7 hari terkahir", "Bulan ini", "khusus");
    private val mPresenter by lazy { DetailRMPresenter(this) }
    var prefs: SharedPreferences? = null
    internal lateinit var mActionBarToolbar: Toolbar
    lateinit var listpasien: List<ListPasienDokter.Remid>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_remid)
        mActionBarToolbar = findViewById<View>(R.id.toolbar) as Toolbar

        setSupportActionBar(mActionBarToolbar)
        supportActionBar?.setTitle("Detail Ringkasan")

        prefs = getSharedPreferences(pref, Context.MODE_PRIVATE)
//        Log.d("tokene", sharedPreferences.getString(my_token, null)!! + " ")
        kd_user = prefs?.getString(login_session, null).toString()
        nama_dokter = prefs?.getString(nm_dokter, null).toString()
        device_token = prefs?.getString(my_token, null).toString()
        signatured = prefs?.getString(signature, null).toString()

        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, listfilter)
//        nm_doktere.text = nama_dokter
        // Set layout to use when the list of choices appear
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        spinner_filter?.setAdapter(aa)

        spinner_filter?.setOnItemSelectedListener(this)
        if (intent != null) {
            val start: String = intent.getStringExtra("start")
            val end: String = intent.getStringExtra("end")
            tgl_awal = start
            tgl_akhir = end
//            toast(tgl_awal + " - "+ tgl_akhir)
            refresh()
        }
        swiperefreshe.onRefresh {
            refresh()
            swiperefreshe.isRefreshing = false
        }
    }




//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
////        Log.d("tokene", sharedPreferences.getString(my_token, null)!! + " ")
//        prefs = ctx.getSharedPreferences(pref,0)
////        Log.d("tokene", sharedPreferences.getString(my_token, null)!! + " ")
//        kd_user = prefs?.getString(login_session, null).toString()
//        nama_dokter = prefs?.getString(nm_dokter, null).toString()
//        return inflater.inflate(R.layout.layout_remid, container, false)
//    }
//
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        activity?.actionBar?.setTitle("Pembayaran")
//        val aa = ArrayAdapter(ctx, android.R.layout.simple_spinner_item, listfilter)
////        nm_doktere.text = nama_dokter
//        // Set layout to use when the list of choices appear
//        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        // Set Adapter to Spinner
//        spinner_filter?.setAdapter(aa)
//
//        spinner_filter?.setOnItemSelectedListener(this)
//
//        swiperefreshe.onRefresh {
//            refresh()
//            swiperefreshe.isRefreshing = false
//        }
//    }

    fun refresh() {
        if (kd_user != "") {
            var mape = HashMap<String, String>()
            mape.put("token", token_server)
            mape.put("device_token", device_token)
            mape.put("package_name", packageName)
//            Log.d("logs id_dokter", kd_user)
//            Log.d("logs device_token", device_token)
//            Log.d("logs package_name", packageName.toString())
//            Log.d("signature end", signatured)
            launch(UI) { mPresenter.getRM(kd_user, tgl_awal, tgl_akhir, mape, signatured) }
//            Log.d("test", "test12" + kd_user + tgl_akhir + tgl_awal)
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        if (intent == null) {
            hariini()
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//        toast("test" + listfilter[p2])

        if (listfilter[p2] == "Hari ini") {
            hariini()
        }
        if (listfilter[p2] == "Kemarin") {
            kemarin()
        }
        if (listfilter[p2] == "7 hari terkahir") {
            tujuhari()
        }
        if (listfilter[p2] == "Bulan ini") {
            bulanini()
        }
        if (listfilter[p2] == "khusus") {
            opendaterange()
        }
//        toast("1" + p0 + "2" + p1 + "3" + p2 + "4" + p3)
//        Log.d("text", "1" + p0.toString() + "2" + p1.toString()  + "3" + p2.toString()  + "4" + p3.toString())
    }

    fun tujuhari() {
        titlee.text = "Data pasien dalam 7 hari terakhir"
        tgl_awal = taun.toString() + "-" + wulan + "-" + if (tujuharisebelum < 1) 1 else tujuharisebelum
        tgl_akhir = taun.toString() + "-" + wulan + "-" + if (dr > 1) dr else 1
        refresh()
    }

    fun hariini() {
        titlee.text = "Data pasien hari ini"
        tgl_awal = taun.toString() + "-" + wulan + "-" + if (dr > 1) dr else 1
        tgl_akhir = taun.toString() + "-" + wulan + "-" + if (dr > 1) dr else 1
        refresh()
    }

    fun bulanini() {
        titlee.text = "Data pasien bulan ini"
        tgl_awal = taun.toString() + "-" + wulan + "-" + 1
        tgl_akhir = taun.toString() + "-" + wulan + "-" + dino_d_b
        refresh()
    }

    fun kemarin() {
        titlee.text = "Data pasien kemarin"
        if (dino == 1) dino = 1 else dino = dino - 1
        tgl_awal = taun.toString() + "-" + wulan + "-" + dino
        tgl_akhir = taun.toString() + "-" + wulan + "-" + dino
        refresh()
    }


    fun openlistdialog(list: List<ListPasienDokter.Remid.Tindakan>) {
        val cdd = DialogListTindakan(this, list)
        cdd.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        cdd.show()


    }

    fun opendaterange() {
//        val pickerFrag :SublimePickerFragment

        val pickerFrag = SublimePickerFragment()
        pickerFrag.setCallback(object : Callback {
            override fun onCancelled() {
                toast("user cancel")
            }

            override fun onDateTimeRecurrenceSet(selectedDate: SelectedDate) {

                @SuppressLint("SimpleDateFormat")
                val formatDate = SimpleDateFormat("yyyy-MM-dd")
                val mDateStart = formatDate.format(selectedDate.startDate.time)
                val mDateEnd = formatDate.format(selectedDate.endDate.time)
                val formatDate2 = SimpleDateFormat("dd MMMM yyyy")
                val mDateStart2 = formatDate2.format(selectedDate.startDate.time)
                val mDateEnd2 = formatDate2.format(selectedDate.endDate.time)
//                Log.d("test1","test1"+mDateStart+"test2"+mDateEnd)
                titlee.text = "Data pasien dari $mDateStart2 sampai $mDateEnd2"
                tgl_awal = mDateStart
                tgl_akhir = mDateEnd
                refresh()
                // set date start ke textview
//                tvStartDate.setText(mDateStart)
//                // set date end ke textview
//                tvEndDate.setText(mDateEnd)
            }
        })

        // ini configurasi agar library menggunakan method Date Range Picker
        val options = SublimeOptions()
        options.setCanPickDateRange(true)
        options.pickerToShow = SublimeOptions.Picker.DATE_PICKER

        val bundle = Bundle()
        bundle.putParcelable("SUBLIME_OPTIONS", options)
        pickerFrag.arguments = bundle

        pickerFrag.setStyle(DialogFragment.STYLE_NO_TITLE, 0)
        pickerFrag.show(this.supportFragmentManager, "SUBLIME_PICKER")
    }

    override fun showLoading() {
        recycle_data?.visibility = View.GONE
        progress_circular?.visibility = View.VISIBLE
        no_data?.visibility = View.GONE
    }

    override fun hideLoading() {
        recycle_data?.visibility = View.VISIBLE
        progress_circular?.visibility = View.GONE
        no_data?.visibility = View.GONE
    }

    override fun showPlaceholder() {
        recycle_data?.visibility = View.GONE
        progress_circular?.visibility = View.GONE
        no_data?.visibility = View.VISIBLE
//        pendapatan_total.text = "Rp. 0"
    }

    override fun showRM(RMList: List<ListPasienDokter.Remid>, pendapatan: String,pasien:String,tindakan:String) {
        pendapatan_total.text = pendapatan
        jumlah_pasien.text = pasien
        jumlah_tindakan.text = tindakan
        listpasien = RMList
        var counte = RMList.size
        recycle_data?.let {
            with(recycle_data) {
                layoutManager = LinearLayoutManager(context)
                adapter = PembayaranRemidAdapter(RMList, counte) { rmlist ->
                    val rem = rmlist.tindakanList
                    openlistdialog(rem)
                }
            }
        }
        no_data?.visibility = View.GONE
    }

}
