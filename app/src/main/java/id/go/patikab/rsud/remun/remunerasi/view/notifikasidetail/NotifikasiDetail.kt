package id.go.patikab.rsud.remun.remunerasi.view.notifikasidetail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View

import id.go.patikab.rsud.remun.remunerasi.R
import kotlinx.android.synthetic.main.detailnotifikasi.*
import kotlinx.android.synthetic.main.list_item.*

class NotifikasiDetail:AppCompatActivity(){

    internal lateinit var mActionBarToolbar: Toolbar
    var id_de=""
    var titlee=""
    var pesane=""
    var labels=""
    var tanggale =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detailnotifikasi)

        mActionBarToolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(mActionBarToolbar)

        var id_de = intent?.getStringExtra("id")
        var titlee = intent?.getStringExtra("title")
        var pesane = intent?.getStringExtra("pesan")
        var labels = intent?.getStringExtra("label")
        var tanggale = intent?.getStringExtra("tanggal")


    }

fun inte(){
    supportActionBar?.setTitle(titlee)

    titles.text = titlee
    pesan.text = pesane
    labele.text = labels
    waktu.text = tanggale
}

}