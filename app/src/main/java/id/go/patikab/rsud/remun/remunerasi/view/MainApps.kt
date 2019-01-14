package id.go.patikab.rsud.remun.remunerasi.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.FragmentManager
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import id.go.patikab.rsud.remun.remunerasi.R
import id.go.patikab.rsud.remun.remunerasi.view.Auth.AuthActivity
import id.go.patikab.rsud.remun.remunerasi.view.PembayaranDokter.PembayaranFragment
import id.go.patikab.rsud.remun.remunerasi.view.profil.ProfilFragment
import id.go.patikab.rsud.remun.remunerasi.view.setAkun.AkunFragment
import id.go.patikab.rsud.remun.remunerasi.view.Pembayaran.DetailRM
import id.go.patikab.rsud.remun.remunerasi.view.Ringkasan.*
import id.go.patikab.rsud.remun.remunerasi.view.home.HomeProfil
import id.go.patikab.rsud.remun.remunerasi.view.JasaPelayanan.*

import id.go.patikab.rsud.remun.remunerasi.data.lokal.sharepreference.SharePref.*
import id.go.patikab.rsud.remun.remunerasi.config.util.logout
import id.go.patikab.rsud.remun.remunerasi.config.util.openAktivasi
import id.go.patikab.rsud.remun.remunerasi.config.util.openLogin
import kotlinx.android.synthetic.main.nav_header_main.*
import org.jetbrains.anko.toast

class MainApps : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    internal lateinit var drawer: DrawerLayout
    internal lateinit var navigationView: NavigationView
    internal lateinit var sharedPreferences: SharedPreferences
    internal lateinit var toolbar: Toolbar
    internal lateinit var fragmentprofil: AkunFragment
    internal lateinit var fragmentpembayaran: PembayaranFragment
//    internal lateinit var profilFragment: ProfilFragment
//    internal lateinit var taksiranPenghasilan: Ringkasan
//    internal lateinit var jaspelView: Jaspel
    internal lateinit var detailRM: DetailRM
    internal lateinit var beranda: HomeProfil
    internal lateinit var fragmentManager: FragmentManager
    internal lateinit var mActionBarToolbar: Toolbar
    var check_login=""
    var login_user=""
    var nm_doktere = ""
    var prefs: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prefs = getSharedPreferences(pref, Context.MODE_PRIVATE)
        check_login = prefs?.getString(status_akun,"").toString()
        login_user = prefs?.getString(username_device,"").toString()
//        nm_doktere = prefs?.getString(nm_dokter,"").toString()
        Log.d("user device",login_user)
//        lb_dokter?.text = "test"
        if(login_user ==""){
               openLogin()
            this.finish()
        }else if(check_login != "1"){
            openAktivasi()
            this.finish()
        }else {

            toolbar = findViewById<Toolbar>(R.id.toolbar)
            mActionBarToolbar = findViewById<View>(R.id.toolbar) as Toolbar

            setSupportActionBar(mActionBarToolbar)
            supportActionBar?.setTitle("Remunerasi")

            drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
            navigationView = findViewById<NavigationView>(R.id.nav_view)


            val toggle = ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
            drawer.addDrawerListener(toggle)
            toggle.syncState()

            navigationView.setNavigationItemSelectedListener(this)

//        Log.d("tokene", sharedPreferences.getString(my_token, null) + " ");
//        kd_user = sharedPreferences.getString(login_session, null)
//        loadProfile();
            fragmentprofil = AkunFragment()
            fragmentpembayaran = PembayaranFragment()
//            profilFragment = ProfilFragment()
            detailRM = DetailRM()
//            taksiranPenghasilan = Ringkasan()
//            jaspelView = Jaspel()
            beranda = HomeProfil()

            fragmentManager = supportFragmentManager

            if (savedInstanceState == null) {
                fragmentManager.beginTransaction()
                        .replace(R.id.flContent, beranda)
                        .commit()
            }
        }

    }

    override fun onBackPressed() {
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if (!item.isChecked) {
            item.isCheckable = true
        }
        // Handle navigation view item clicks here.
        val id = item.itemId
        when (item.itemId) {
//            R.id.nav_jaspel -> fragmentManager.beginTransaction()
//                    .replace(R.id.flContent, jaspelView)
//                    .commit()
//
//            R.id.nav_profile -> fragmentManager.beginTransaction()
//                    .replace(R.id.flContent, profilFragment)
//                    .commit()
//            R.id.nav_remune -> fragmentManager.beginTransaction()
//                    .replace(R.id.flContent, fragmentpembayaran)
//                    .commit()
//            R.id.nav_pembayaran -> fragmentManager.beginTransaction()
//                    .replace(R.id.flContent, taksiranPenghasilan)
//                    .commit()
            R.id.nav_beranda -> fragmentManager.beginTransaction()
                    .replace(R.id.flContent, beranda)
                    .commit()
            else -> fragmentManager.beginTransaction()
                    .replace(R.id.flContent, beranda)
                    .commit()
        }//              ActivityIntentKt.openPembayaranRemid(this,"15","2018-09-01","2018-10-31");

        if (id == R.id.nav_logout) {
            Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show()
            logout()
        }
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)

        return true
    }

}
