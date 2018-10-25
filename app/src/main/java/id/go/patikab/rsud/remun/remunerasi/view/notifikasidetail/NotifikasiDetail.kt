package id.go.patikab.rsud.remun.remunerasi.view.notifikasidetail


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View

import id.go.patikab.rsud.remun.remunerasi.R
import id.go.patikab.rsud.remun.remunerasi.data.lokal.sharepreference.SharePref
import kotlinx.android.synthetic.main.activity_detail_notifikasi.*
import org.jetbrains.anko.toast

class NotifikasiDetail : AppCompatActivity() {

    internal lateinit var mActionBarToolbar: Toolbar
    lateinit var id_de: String
    var d = ""
    var titlee = ""
    var pesane = ""
    var labels = ""
    var tanggale = ""
    internal lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_notifikasi)

        mActionBarToolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(mActionBarToolbar)
        supportActionBar?.setTitle("Notifikasi Remunerasi")

        sharedPreferences = getSharedPreferences(SharePref.pref, Context.MODE_PRIVATE)
//        Log.d("tokene", sharedPreferences.getString(my_token, null)!! + " ")
        id_de = sharedPreferences.getString("id_pes", null)
        if (id_de != "0") {
            enti()
//            toast("ambil dari api"+ id_de)
        } else {
            inte()
        }
    }

    fun inte() {
        d = intent?.getStringExtra("id_ne").toString()
        titlee = intent.getStringExtra("title")
        pesane = intent.getStringExtra("pesan")
        labels = intent.getStringExtra("label")
        tanggale = intent.getStringExtra("tanggal")

        titles.text = titlee
        pesan.text = pesane
        labele.text = labels
        waktu.text = tanggale
    }
    fun enti() {
//        d = intent.getStringExtra("id_ne")
//        id_de = sharedPreferences.getString("id_pes", null)
        titlee = sharedPreferences.getString("jd", null)
        pesane = sharedPreferences.getString("ps", null)
        labels = sharedPreferences.getString("lb", null)
        tanggale = sharedPreferences.getString("wk", null)

        titles.text = titlee
        pesan.text = pesane
        labele.text = labels
        waktu.text = tanggale
    }

}