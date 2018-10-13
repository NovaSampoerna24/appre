package id.go.patikab.rsud.remun.remunerasi.view.Notifikasi

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import id.go.patikab.rsud.remun.remunerasi.R
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.NotifikasiResponse
import id.go.patikab.rsud.remun.remunerasi.data.lokal.sharepreference.SharePref
import kotlinx.android.synthetic.main.notifikasilayout.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import id.go.patikab.rsud.remun.remunerasi.config.adapter.*
import id.go.patikab.rsud.remun.remunerasi.config.util.*
import org.jetbrains.anko.toast

class Notifikasi : AppCompatActivity(),NotifikasiView {
    private val mPresenter by lazy { NotifikasiPresenter(this) }

    internal lateinit var sharedPreferences: SharedPreferences
    internal var kd_user: String=""

    override fun showInformasi(informasi: List<NotifikasiResponse.Notif>) {
        recycle_data?.let {
            with(recycle_data) {
                layoutManager = LinearLayoutManager(context)
                adapter =
                        InformasiAdapter(informasi) { infor ->
                          openEventsDetail(infor.id)
                            toast(infor.id+" test")
                        }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notifikasilayout)
        sharedPreferences = getSharedPreferences(SharePref.pref, Context.MODE_PRIVATE)
//        Log.d("tokene", sharedPreferences.getString(my_token, null)!! + " ")
        kd_user = sharedPreferences?.getString(SharePref.login_session, null)
        launch(UI) { mPresenter.getPengumuman(kd_user) }
    }
}