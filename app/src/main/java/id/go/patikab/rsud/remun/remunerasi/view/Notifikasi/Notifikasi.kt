package id.go.patikab.rsud.remun.remunerasi.view.Notifikasi

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.go.patikab.rsud.remun.remunerasi.R
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.NotifikasiResponse
import id.go.patikab.rsud.remun.remunerasi.data.lokal.sharepreference.SharePref
import kotlinx.android.synthetic.main.notifikasilayout.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import id.go.patikab.rsud.remun.remunerasi.config.adapter.*
import id.go.patikab.rsud.remun.remunerasi.config.util.openNotif

class Notifikasi : Fragment(),NotifikasiView {
    private val mPresenter by lazy { NotifikasiPresenter(this) }
    internal lateinit var sharedPreferences: SharedPreferences
    internal var kd_user: String=""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        sharedPreferences = activity!!.getSharedPreferences(SharePref.pref, Context.MODE_PRIVATE)
//        Log.d("tokene", sharedPreferences.getString(my_token, null)!! + " ")
        kd_user = sharedPreferences?.getString(SharePref.login_session, null)

        launch(UI) { mPresenter.getPengumuman(kd_user) }


        return inflater.inflate(R.layout.notifikasilayout, container, false)
    }

    override fun showInformasi(informasi: List<NotifikasiResponse.Notif>) {
        recycle_data?.let {
            with(recycle_data) {
                layoutManager = LinearLayoutManager(context)
                adapter =
                        InformasiAdapter(informasi,informasi.size) { infor ->
                            activity?.openNotif(infor)
//                            toast(infor.id+" test")
                        }
            }
        }
    }


}