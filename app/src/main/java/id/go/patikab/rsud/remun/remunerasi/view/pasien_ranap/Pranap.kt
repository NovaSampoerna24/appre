package id.go.patikab.rsud.remun.remunerasi.view.pasien_ranap

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import id.go.patikab.rsud.remun.remunerasi.R
import id.go.patikab.rsud.remun.remunerasi.data.lokal.sharepreference.SharePref.*
import org.jetbrains.anko.support.v4.ctx
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.android.synthetic.main.layout_pasien.*
import id.go.patikab.rsud.remun.remunerasi.config.adapter.PasienAdapter
import id.go.patikab.rsud.remun.remunerasi.config.util.openPdetailRanap
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.*
import id.go.patikab.rsud.remun.remunerasi.view.pasien_rajal.PranapView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.toast
import java.text.SimpleDateFormat
import java.util.*

class Pranap : Fragment(), PranapView {

    val mPresenter by lazy { PranapPresenter(this) }
    var kd_user: String = ""
    var nama_dokter: String = ""
    var prefs: SharedPreferences? = null
    var email: String = ""
    fun getActionBar(): ActionBar? {
        return (activity as AppCompatActivity).supportActionBar
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        prefs = ctx.getSharedPreferences(pref, 0)
//        Log.d("tokene", sharedPreferences.getString(my_token, null)!! + " ")
        kd_user = prefs?.getString(login_session, null).toString()
        nama_dokter = prefs?.getString(nm_dokter, null).toString()
        email = prefs?.getString(email_device, "").toString()

        return inflater.inflate(R.layout.layout_pasien, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getActionBar()?.setTitle("Pasien Rawat Inap")
        refresh()
        swiperefreshe.onRefresh {
            refresh()
            swiperefreshe.isRefreshing = false
        }
    }
    override fun showPranap(data: List<ListPasien.Pasiene>) {
        //        Log.d("test nt",informasi.get(0).judul)
        val currentDate2 = SimpleDateFormat("dd-MMM-yyyy")
        val todayDate = Date()
        var tanggal2 = currentDate2.format(todayDate)
        txt_tanggal.text = tanggal2
        nmane_dokter.text = nama_dokter
        txt_jumlah.text = "Jumlah : "+data.size.toString()
        recycle_datae?.let {
            with(recycle_datae) {
                layoutManager = LinearLayoutManager(context)
                var counte :Int

                adapter =
                        PasienAdapter(data,data.size) { infor ->
//                            activity?.openNotif(data)
//                            toast(infor.NOMR+" test")
                            activity?.openPdetailRanap(infor.IDXDAFTAR,infor.NOMR)
                        }
            }
        }
    }
    fun refresh(){
        launch(UI) {
            mPresenter.getPranap(kd_user)
            mPresenter.getDokter()
        }
    }
    fun refresh2(user:String){
        launch(UI) {
            mPresenter.getPranap(user.trim())
        }
    }
    fun getSearchPranap(q:String){

        launch(UI) {
            mPresenter.getsearchPranap(kd_user,q)
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

        var spindokter = ArrayList<String>();
        var isi = ArrayList<String>();
        for (i in data){
            spindokter.add(i.NAMADOKTER)
            isi.add(i.KDDOKTER)
        }

        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, spindokter )
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        positionSpinner.adapter = adapter

        positionSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                // either one will work as well
                // val item = parent.getItemAtPosition(position) as String
                val item = adapter.getItem(position)
                kd_user  = isi.get(positionSpinner.selectedItemPosition)
//                toast(alerte)
                refresh2(kd_user)

            }

        }

    }


}
