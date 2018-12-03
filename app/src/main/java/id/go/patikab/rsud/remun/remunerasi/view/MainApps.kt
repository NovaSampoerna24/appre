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
import android.support.v7.app.ActionBar
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

import id.go.patikab.rsud.remun.remunerasi.data.lokal.sharepreference.SharePref.pref
import id.go.patikab.rsud.remun.remunerasi.data.lokal.sharepreference.SharePref.my_token
import id.go.patikab.rsud.remun.remunerasi.data.lokal.sharepreference.SharePref.login_session
import id.go.patikab.rsud.remun.remunerasi.config.util.logout
import org.jetbrains.anko.act

class MainApps : AppCompatActivity() ,  NavigationView.OnNavigationItemSelectedListener{

    internal lateinit var drawer: DrawerLayout
    internal lateinit var navigationView: NavigationView
    internal lateinit var sharedPreferences: SharedPreferences
    internal lateinit var toolbar: Toolbar
    internal lateinit var fragmentprofil: AkunFragment
    internal lateinit var fragmentpembayaran: PembayaranFragment
    internal lateinit var profilFragment: ProfilFragment
    internal lateinit var detailRM: DetailRM
    internal lateinit var fragmentManager: FragmentManager
    internal lateinit var mActionBarToolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById<Toolbar>(R.id.toolbar)
        mActionBarToolbar = findViewById<View>(R.id.toolbar) as Toolbar

        setSupportActionBar(mActionBarToolbar)
        supportActionBar?.setTitle("Remunerasi")

        drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        navigationView = findViewById<NavigationView>(R.id.nav_view)

        sharedPreferences = getSharedPreferences(pref, Context.MODE_PRIVATE)
        var kd_user = sharedPreferences.getString(login_session,"")

        if ( kd_user == "") {
            finish()
            startActivity(Intent(this, AuthActivity::class.java))
//            Log.d("tokene", sharedPreferences.getString(my_token, null))
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener(this)
        sharedPreferences = getSharedPreferences(pref, Context.MODE_PRIVATE)
//        Log.d("tokene", sharedPreferences.getString(my_token, null) + " ");
//        kd_user = sharedPreferences.getString(login_session, null)
//        loadProfile();
        fragmentprofil = AkunFragment()
        fragmentpembayaran = PembayaranFragment()
        profilFragment = ProfilFragment()
        detailRM = DetailRM()

        fragmentManager = supportFragmentManager

        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.flContent, profilFragment)
                    .commit()
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
            R.id.nav_profile -> fragmentManager.beginTransaction()
                    .replace(R.id.flContent, profilFragment)
                    .commit()
//            R.id.nav_remune -> fragmentManager.beginTransaction()
//                    .replace(R.id.flContent, fragmentpembayaran)
//                    .commit()
            R.id.nav_pembayaran -> fragmentManager.beginTransaction()
                    .replace(R.id.flContent, detailRM)
                    .commit()
            else -> fragmentManager.beginTransaction()
                    .replace(R.id.flContent, profilFragment)
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