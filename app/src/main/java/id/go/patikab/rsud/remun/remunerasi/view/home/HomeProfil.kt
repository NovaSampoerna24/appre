package id.go.patikab.rsud.remun.remunerasi.view.home

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import id.go.patikab.rsud.remun.remunerasi.R
import id.go.patikab.rsud.remun.remunerasi.data.lokal.sharepreference.SharePref.*
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.MenuModel
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.ProfilDetail
import org.jetbrains.anko.support.v4.ctx
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.ResponseMenu
import kotlinx.android.synthetic.main.layout_recycle.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import id.go.patikab.rsud.remun.remunerasi.config.adapter.Menu1Adapter
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.NotifikasiResponse
import kotlinx.android.synthetic.main.layout_home.*
import id.go.patikab.rsud.remun.remunerasi.config.adapter.InformasiAdapter
import id.go.patikab.rsud.remun.remunerasi.config.util.*
import id.go.patikab.rsud.remun.remunerasi.slider.groupie.BannerListener
import id.go.patikab.rsud.remun.remunerasi.slider.model.BannerPromo
import id.go.patikab.rsud.remun.remunerasi.view.Notifikasi.NotifikasiView
import id.go.patikab.rsud.remun.remunerasi.view.pasien_rajal.Prajal
import id.go.patikab.rsud.remun.remunerasi.view.pasien_ranap.Pranap
import id.go.patikab.rsud.remun.remunerasi.slider.groupie.BannerCarouselItem

class HomeProfil : Fragment(), HomeView, NotifikasiView,BannerListener{



    val mPresenter by lazy { HomePresenter(this) }
    var kd_user: String = ""
    var nama_dokter: String = ""
    var prefs: SharedPreferences? = null
    var email: String = ""
    var level :String=""
    internal lateinit var prajal: Prajal
    internal lateinit var pranap: Pranap
    private var groupAdapter = GroupAdapter<ViewHolder>()
    internal lateinit var context: Context
    fun getActionBar(): ActionBar? {
        return (activity as AppCompatActivity).supportActionBar
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        prefs = ctx.getSharedPreferences(pref, 0)
//        Log.d("tokene", sharedPreferences.getString(my_token, null)!! + " ")
        kd_user = prefs?.getString(login_session, null).toString()
        nama_dokter = prefs?.getString(nm_dokter, null).toString()
        email = prefs?.getString(email_device, "").toString()
        level = prefs?.getString(level_user,"").toString()

        return inflater.inflate(R.layout.layout_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getActionBar()?.setTitle("Beranda")
        launch(UI) {
            mPresenter.getmenu1(level)
            mPresenter.getPengumuman(kd_user)
        }
        prajal = Prajal()
        pranap = Pranap()




    }


    override fun showDetail(detail: ProfilDetail) {

    }

    fun isOnline(): Boolean {
        val cm = activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        //jika ada koneksi return true
        return if (netInfo != null && netInfo.isConnected) {
            true
        } else false
        //jika tidak ada koneksi return false
    }


    override fun showMenu(menu: List<ResponseMenu.Menune>) {
//        recycle_data?.let {
//            with(recycle_data) {
//                layoutManager = GridLayoutManager(context, 3)
//
//                adapter = MenuAdapter(menu) { m ->
//                    if (m.messagee != "0" ) {
//                        toast(m.messagee)
//                    } else {
//                        activity?.openwebMenu(m.url)
//                    }
//                }
//
//            }
//        }
    }

    override fun showMenu1(menu: List<MenuModel>, icon: ArrayList<Int>) {
        val column = 3
        recycle_data?.let {
            with(recycle_data) {
                layoutManager = GridLayoutManager(context, column)
                adapter = Menu1Adapter(menu, icon) { m ->
                    if (m.id == "0") {
                        activity?.openpasjal()
//                        val editor = prefs!!.edit()
//                        editor.putString(fragmene, "")
//                        editor.apply()
//
//                        fragmentManager?.beginTransaction()
//                                ?.replace(R.id.flContent, prajal)
//                                ?.commit()
                    } else if (m.id == "1") {
                        activity?.openpasnap()
//                        val editor = prefs!!.edit()
//                        editor.putString(fragmene, "")
//                        editor.apply()
//                        fragmentManager?.beginTransaction()
//                                ?.replace(R.id.flContent, pranap)
//                                ?.commit()

                    } else if (m.id == "2") {
                        activity?.OpenProfile()
//                        val editor = prefs!!.edit()
//                        editor.putString(fragmene, "")
//                        editor.apply()
                    } else if (m.id == "3") {
//                        activity?.openabout()
                        activity?.openallrajal()
                    }
                    else if (m.id == "4") {
//                        activity?.openabout()
                        activity?.openallranap()
                    }
                    else if (m.id == "5") {
//                        activity?.openabout()
                        activity?.openpengembangan()
                    }
                    else if (m.id == "6") {
//                        activity?.openabout()
                        activity?.openpengembangan()
                    }
                    else if (m.id == "7") {
//                        activity?.openabout()
                        activity?.openpengembangan()
                    }

                }
            }
        }


    }

    override fun showInformasi(informasi: List<NotifikasiResponse.Notif>) {
//        Log.d("test nt",informasi.get(0).judul)
        recycle_datae?.let {
            with(recycle_datae) {
                layoutManager = LinearLayoutManager(context)
                var counte: Int
                if (informasi.size > 5) counte = 5 else counte = informasi.size
                adapter =
                        InformasiAdapter(informasi, counte) { infor ->
                            activity?.openNotif(infor)
//                            toast(infor.id+" test")
                        }
            }
        }


    }

    override fun hideloading() {
        recycle_datae.visibility = View.VISIBLE
        progress_circular.visibility = View.GONE
        no_data.visibility = View.GONE
    }

    override fun showloading() {
        recycle_datae.visibility = View.GONE
        progress_circular.visibility = View.VISIBLE
        no_data.visibility = View.GONE
    }

    override fun placeholder() {
        recycle_datae.visibility = View.GONE
        progress_circular.visibility = View.GONE
        no_data.visibility = View.VISIBLE
    }
    override fun onSeeAllPromoClick() {
          }

    override fun onBannerClick(promo: BannerPromo) {
         }
}
