package id.go.patikab.rsud.remun.remunerasi.view.setAkun


import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences

import android.graphics.Color
import android.graphics.drawable.ColorDrawable

import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v4.app.Fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import id.go.patikab.rsud.remun.remunerasi.R
import id.go.patikab.rsud.remun.remunerasi.data.lokal.sharepreference.SharePref.*
import id.go.patikab.rsud.remun.remunerasi.config.util.*
import kotlinx.android.synthetic.main.akun_layout.*

class AkunFragment : Fragment() {


//    private val mPresenter by lazy { ProfilPresenter(this) }

    internal lateinit var sharedPreferences: SharedPreferences
    internal var kd_user: String=""
    internal var nama_dokter: String=""
    lateinit var message: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        sharedPreferences = activity!!.getSharedPreferences(pref, Context.MODE_PRIVATE)
//        Log.d("tokene", sharedPreferences.getString(my_token, null)!! + " ")
        kd_user = sharedPreferences?.getString(login_session, null)
        nama_dokter = sharedPreferences?.getString(nm_dokter, null)

        return inflater.inflate(R.layout.akun_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        t_ubah_password.setOnClickListener {
            activity?.opengantiPassword(kd_user)
        }
        t_ganti_foto.setOnClickListener {
            activity?.openUbahFoto(kd_user,nama_dokter)
        }
        t_logout.setOnClickListener {
            activity?.logout()
            activity?.finish()

        }
        t_pengumuman.setOnClickListener {
            activity?.openpengumuman(kd_user)
        }

    }




}

