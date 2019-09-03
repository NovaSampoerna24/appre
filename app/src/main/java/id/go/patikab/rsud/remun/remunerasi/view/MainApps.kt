package id.go.patikab.rsud.remun.remunerasi.view

import android.content.Context
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
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import id.go.patikab.rsud.remun.remunerasi.R
import id.go.patikab.rsud.remun.remunerasi.view.PembayaranDokter.PembayaranFragment
import id.go.patikab.rsud.remun.remunerasi.view.profil.ProfilFragment
import id.go.patikab.rsud.remun.remunerasi.view.setAkun.AkunFragment
import id.go.patikab.rsud.remun.remunerasi.view.Pembayaran.DetailRM
import id.go.patikab.rsud.remun.remunerasi.view.Ringkasan.*

import id.go.patikab.rsud.remun.remunerasi.data.lokal.sharepreference.SharePref.*
import id.go.patikab.rsud.remun.remunerasi.view.PasienKu.PasienKu
import id.go.patikab.rsud.remun.remunerasi.view.akun.AkuneFramgent
import id.go.patikab.rsud.remun.remunerasi.view.home.HomeProfil
import id.go.patikab.rsud.remun.remunerasi.view.pasien_rajal.Prajal
import id.go.patikab.rsud.remun.remunerasi.view.pasien_ranap.Pranap
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.SearchView
import id.go.patikab.rsud.remun.remunerasi.config.util.*
import kotlinx.android.synthetic.main.nav_header_main.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.alert
import org.jetbrains.anko.bundleOf
import org.jetbrains.anko.toast
import android.widget.TextView
import android.content.DialogInterface
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import id.go.patikab.rsud.remun.remunerasi.slider.groupie.BannerCarouselItem
import id.go.patikab.rsud.remun.remunerasi.slider.groupie.BannerListener
import id.go.patikab.rsud.remun.remunerasi.slider.model.BannerPromo


class MainApps : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener, BannerListener {

    internal lateinit var drawer: DrawerLayout
    internal lateinit var navigationView: NavigationView
    internal lateinit var sharedPreferences: SharedPreferences
    internal lateinit var toolbar: Toolbar
    internal lateinit var fragmentprofil: AkunFragment
    internal lateinit var fragmentpembayaran: PembayaranFragment
    internal lateinit var prajal: Prajal
    internal lateinit var pranap: Pranap
    internal lateinit var akun: AkuneFramgent
    internal lateinit var detailRM: DetailRM
    internal lateinit var beranda: HomeProfil
    internal lateinit var fragmentManager: FragmentManager
    internal lateinit var mActionBarToolbar: Toolbar
    var check_login = ""
    var login_user = ""
    var nm_doktere = ""
    var prefs: SharedPreferences? = null
    var difragment = ""
    private var doubleBackToExitPressedOnce = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prefs = getSharedPreferences(pref, Context.MODE_PRIVATE)
        check_login = prefs?.getString(status_akun, "").toString()
        login_user = prefs?.getString(username_device, "").toString()
        nm_doktere = prefs?.getString(nm_dokter, "").toString()
//        nm_doktere = prefs?.getString(nm_dokter,"").toString()
        Log.d("user device", login_user)
//        lb_dokter?.text = "test"
        if (login_user == "") {
            openLogin()
            this.finish()
        } else if (check_login != "1") {
            openAktivasi()
            this.finish()
        } else {

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


            val headerView = navigationView.getHeaderView(0)
            val navUsername = headerView.findViewById(R.id.txt_user) as TextView
            navUsername.text = nm_doktere

            navigationView.setNavigationItemSelectedListener(this)

//        loadProfile();
            fragmentprofil = AkunFragment()
            fragmentpembayaran = PembayaranFragment()
//            profilFragment = ProfilFragment()
            detailRM = DetailRM()
            prajal = Prajal()
            pranap = Pranap()
            akun = AkuneFramgent()
//            taksiranPenghasilan = Ringkasan()
//            jaspelView = PasienKu()
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
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed()
                return
            }

            this.doubleBackToExitPressedOnce = true
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

            Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)

//            val builder = AlertDialog.Builder(this)
//            builder.setMessage("Are you sure you want to exit?")
//                    .setCancelable(false)
//                    .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, id -> this.finish() })
//                    .setNegativeButton("No", DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })
//            val alert = builder.create()
//            alert.show()
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.main, menu)
//        val menuSearch = menu?.findItem(R.id.action_search)
//        val searchView = menuSearch?.actionView as SearchView?
//
//        searchView?.let {
//            with(it) {
//                setOnCloseListener {
//                    searchView.setQuery("", false)
//                    true
//                }
//                setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//                    override fun onQueryTextSubmit(query: String?): Boolean = false
//                    override fun onQueryTextChange(newText: String?): Boolean {
//                        return newText?.let {
//                            prajal.getSearchPrajal(it)
//                            true
//                        } ?: false
//                    }
//                })
//            }
//        }
//        return true
//        prefs = getSharedPreferences(pref, Context.MODE_PRIVATE)
////        Log.d("tokene", sharedPreferences.getString(my_token, null)!! + " ")
//        difragment = prefs?.getString(fragmene, "").toString()
//        if (difragment != "") {
//            menuInflater.inflate(R.menu.main, menu)
//            val item = menu?.findItem(R.id.action_search)
//            val searchView = MenuItemCompat.getActionView(item) as SearchView
//            searchView.queryHint = getString(R.string.type_name)
//            searchView.isIconified = true
//            searchView.setOnQueryTextListener(this)
//        }
        return true
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if (!item.isChecked) {
            item.isCheckable = true
        }
        // Handle navigation view item clicks here.
        val id = item.itemId
        if (item.itemId == R.id.nav_jaspel) {
            fragmentManager.beginTransaction()
                    .replace(R.id.flContent, prajal)
                    .commit()
//            difragment = "rajal"
            val editor = prefs!!.edit()
            editor.putString(fragmene, "rajal")
            editor.apply()
        } else if (item.itemId == R.id.nav_pembayaran) {
            fragmentManager.beginTransaction()
                    .replace(R.id.flContent, pranap)
                    .commit()
//            difragment = "ranap"
            val editor = prefs!!.edit()
            editor.putString(fragmene, "ranap")
            editor.apply()
        } else if (item.itemId == R.id.nav_profile) {
            val editor = prefs!!.edit()
            editor.putString(fragmene, "")
            editor.apply()
            OpenProfile()
        } else if (item.itemId == R.id.nav_beranda) {
            val editor = prefs!!.edit()
            editor.putString(fragmene, "")
            editor.apply()
            fragmentManager.beginTransaction()
                    .replace(R.id.flContent, beranda)
                    .commit()
        }else if (item.itemId == R.id.nav_logout){
            logout()
        }
        else {
            val editor = prefs!!.edit()
            editor.putString(fragmene, "")
            editor.apply()
            fragmentManager.beginTransaction()
                    .replace(R.id.flContent, beranda)
                    .commit()
        }


//        when (item.itemId) {
//            R.id.nav_jaspel ->
//
//            R.id.nav_pembayaran ->
////            R.id.nav_profile ->
//            R.id.nav_profile -> OpenProfile()
////            R.id.nav_pembayaran -> fragmentManager.beginTransaction()
////                    .replace(R.id.flContent, taksiranPenghasilan)
////                    .commit()
//            R.id.nav_beranda -> fragmentManager.beginTransaction()
//                    .replace(R.id.flContent, beranda)
//                    .commit()
//            else ->
//
//        }
        //              ActivityIntentKt.openPembayaranRemid(this,"15","2018-09-01","2018-10-31");

//        if (id == R.id.nav_logout) {
//            Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show()
//            logout()
//        }
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
//        prefs = getSharedPreferences(pref, Context.MODE_PRIVATE)
////        Log.d("tokene", sharedPreferences.getString(my_token, null)!! + " ")
//        difragment = prefs?.getString(fragmene,"").toString()
//        Log.d("test fragment",difragment+" aw")
//        if(difragment == "rajal"){
//            prajal?.getSearchPrajal(newText.toString(),prajal.tanggal)
//
//        }
//        if(difragment == "ranap"){
//            pranap?.getSearchPranap(newText.toString())
//        }
//
        return false
    }

    override fun onQueryTextSubmit(query: String?): Boolean {

        prefs = getSharedPreferences(pref, Context.MODE_PRIVATE)
//        Log.d("tokene", sharedPreferences.getString(my_token, null)!! + " ")
        difragment = prefs?.getString(fragmene, "").toString()
        Log.d("test fragment", difragment + " aw")
        if (difragment == "rajal") {
            prajal?.getSearchPrajal(query.toString(), prajal.tanggal)

        }
        if (difragment == "ranap") {
            pranap?.getSearchPranap(query.toString())

        }

        return false
    }

    override fun onSeeAllPromoClick() {

    }

    override fun onBannerClick(promo: BannerPromo) {

    }
}


