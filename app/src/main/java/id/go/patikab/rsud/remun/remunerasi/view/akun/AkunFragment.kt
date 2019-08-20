package id.go.patikab.rsud.remun.remunerasi.view.akun


import android.content.Context
import android.content.SharedPreferences

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import id.go.patikab.rsud.remun.remunerasi.R
import id.go.patikab.rsud.remun.remunerasi.data.lokal.sharepreference.SharePref.*
import id.go.patikab.rsud.remun.remunerasi.config.util.*
import kotlinx.android.synthetic.main.activity_profile.*
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.toast
import android.support.design.widget.AppBarLayout
import kotlinx.android.synthetic.main.content_profile.*


class AkuneFramgent : AppCompatActivity() {


//    private val mPresenter by lazy { ProfilPresenter(this) }

    var kd_user: String = ""
    var nama_dokter: String = ""
    var message: String = ""
    var prefs: SharedPreferences? = null
    var email:String = ""
    internal lateinit var mActionBarToolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefs = getSharedPreferences(pref, 0)
//        Log.d("tokene", sharedPreferences.getString(my_token, null)!! + " ")
        kd_user = prefs?.getString(login_session, "").toString()
        nama_dokter = prefs?.getString(nm_dokter, "").toString()
        email = prefs?.getString(email_device,"").toString()
        setContentView(R.layout.activity_profile)

        mActionBarToolbar = findViewById<View>(R.id.toolbar) as Toolbar
        mActionBarToolbar.title = "Akun"
        setSupportActionBar(mActionBarToolbar)


        txt_username.text = nama_dokter
        txt_email.text = email
        btn_back.setOnClickListener {
            onBackPressed()
        }
        txt_nama.text = nama_dokter
        txtemail.text = email
        txt_password.setOnClickListener {
            opengantiPassword()
        }
//        val collapsingToolbarLayout = findViewById(R.id.collapsingToolbarLayout) as CollapsingToolbarLayout
//        val appBarLayout = findViewById(R.id.appBarLayout) as AppBarLayout
        app_bar.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            internal var isShow = true
            internal var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    toolbar_layout.title = "Informasi Akun"
                    getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
                    getSupportActionBar()?.setDisplayShowHomeEnabled(true);
                    isShow = true
                } else if (isShow) {
                    toolbar_layout.title = " "//careful there should a space between double quote otherwise it wont work
                    isShow = false
                }
            }
        })

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
//                              savedInstanceState: Bundle?): View? {
//        prefs = ctx.getSharedPreferences(pref, 0)
////        Log.d("tokene", sharedPreferences.getString(my_token, null)!! + " ")
//        kd_user = prefs?.getString(login_session, null).toString()
//        nama_dokter = prefs?.getString(nm_dokter, null).toString()
//
//        return inflater.inflate(R.layout.activity_profile, container, false)
//    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)

//        t_ubah_password.setOnClickListener {
//            activity?.opengantiPassword()
//            Log.d("test user", kd_user)
//        }
//        t_ganti_foto.setOnClickListener {
//            activity?.openUbahFoto(kd_user, nama_dokter)
//        }
//        t_logout.setOnClickListener {
//            activity?.logout()
//        }


//    }


}


