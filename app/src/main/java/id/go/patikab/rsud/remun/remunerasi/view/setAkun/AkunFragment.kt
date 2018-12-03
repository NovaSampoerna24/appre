package id.go.patikab.rsud.remun.remunerasi.view.setAkun


import android.content.Context
import android.content.SharedPreferences

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import id.go.patikab.rsud.remun.remunerasi.R
import id.go.patikab.rsud.remun.remunerasi.data.lokal.sharepreference.SharePref.pref
import id.go.patikab.rsud.remun.remunerasi.data.lokal.sharepreference.SharePref.login_session
import id.go.patikab.rsud.remun.remunerasi.data.lokal.sharepreference.SharePref.nm_dokter
import id.go.patikab.rsud.remun.remunerasi.config.util.*
import kotlinx.android.synthetic.main.activity_akun.*
import org.jetbrains.anko.support.v4.ctx

class AkunFragment : Fragment() {


//    private val mPresenter by lazy { ProfilPresenter(this) }

    var kd_user: String = ""
    var nama_dokter: String = ""
    var message: String = ""
    var prefs: SharedPreferences? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        prefs = ctx.getSharedPreferences(pref, 0)
//        Log.d("tokene", sharedPreferences.getString(my_token, null)!! + " ")
        kd_user = prefs?.getString(login_session, null).toString()
        nama_dokter = prefs?.getString(nm_dokter, null).toString()

        return inflater.inflate(R.layout.activity_akun, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        t_ubah_password.setOnClickListener {
            activity?.opengantiPassword()
            Log.d("test user", kd_user)
        }
        t_ganti_foto.setOnClickListener {
            activity?.openUbahFoto(kd_user, nama_dokter)
        }
        t_logout.setOnClickListener {
            activity?.logout()
        }


    }


}

