package id.go.patikab.rsud.remun.remunerasi.view.notifikasidetail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View

import id.go.patikab.rsud.remun.remunerasi.R
import kotlinx.android.synthetic.main.activity_detail_notifikasi.*

class NotifikasiDetail : AppCompatActivity() {

    internal lateinit var mActionBarToolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_notifikasi)

        mActionBarToolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(mActionBarToolbar)

        supportActionBar?.setTitle("PasienRanap Remunerasi")

            if(intent.extras != null){
                val bundle = intent.extras
                titles.text = bundle?.getString("judul")
                pesan.text = bundle?.getString("pesan")
                labele.text = bundle?.getString("label")
                waktu.text = bundle?.getString("waktu")
            }


    }


}