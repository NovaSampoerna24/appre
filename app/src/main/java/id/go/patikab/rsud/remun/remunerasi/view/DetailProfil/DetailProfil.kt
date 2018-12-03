package id.go.patikab.rsud.remun.remunerasi.view.DetailProfil

import android.content.Context
import android.content.SharedPreferences
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso

import id.go.patikab.rsud.remun.remunerasi.R
import id.go.patikab.rsud.remun.remunerasi.config.util.openUbahFoto
import id.go.patikab.rsud.remun.remunerasi.data.lokal.sharepreference.SharePref.pref
import id.go.patikab.rsud.remun.remunerasi.data.lokal.sharepreference.SharePref.nm_dokter
import id.go.patikab.rsud.remun.remunerasi.data.lokal.sharepreference.SharePref.login_session
import id.go.patikab.rsud.remun.remunerasi.data.lokal.sharepreference.SharePref.foto_profil
import id.go.patikab.rsud.remun.remunerasi.data.api.ApiClient.base_Url_upload
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.MenuModel
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.ProfilDetail
import id.go.patikab.rsud.remun.remunerasi.view.page_dialog.CustomDialogDetail
import kotlinx.android.synthetic.main.acitivity_detail_profil.*
import id.go.patikab.rsud.remun.remunerasi.config.adapter.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.share
import org.jetbrains.anko.support.v4.toast

class DetailProfil : Fragment(), DetailView {


    val mPresenter by lazy { DetailPresenter(this) }
    var kd_user: String = ""
    var nama_dokter: String = ""
    var prefs: SharedPreferences? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        prefs = ctx.getSharedPreferences(pref, 0)
//        Log.d("tokene", sharedPreferences.getString(my_token, null)!! + " ")
        kd_user = prefs?.getString(login_session, null).toString()
        nama_dokter = prefs?.getString(nm_dokter, null).toString()

        launch(UI) {
            mPresenter.getmenu()
        }
        if (kd_user != "") {
            if (isOnline() == true) {
                refresh()
            }
        }

        return inflater.inflate(R.layout.acitivity_detail_profil, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        txt_overview.text = mOverview
        txt_nama.text = nama_dokter
        profile_image2.setOnClickListener {
            activity?.openUbahFoto(kd_user, nama_dokter);
        }
        if (activity?.intent?.extras != null) {
            val bundle = activity?.intent?.extras
            if (bundle?.getString("fp") == "yes") {
                refresh()
            }
        }
    }

    fun refresh() {
        launch(UI) {
            mPresenter.fetchProfilRetro(kd_user)
        }
    }

    override fun showDetail(detail: ProfilDetail) {
        if (isOnline()) {
            if (profile_image2 != null) {
                Picasso.get()
                        .load(base_Url_upload + detail.foto)
                        .error(R.drawable.dokterdefault)
                        .into(profile_image2)
            }
        }
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

    fun dialog_failure() {
        val cdd = CustomDialogDetail(context)
        cdd.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        cdd.show()
    }

    override fun showMenu(menu: List<MenuModel>, icon: ArrayList<Int>) {
        recycle_data?.let {
            with(recycle_data) {
                layoutManager = GridLayoutManager(context, 3)

                adapter = MenuAdapter(menu, icon) { m ->
                    toast("test " + m.nama)
                }
            }
        }
    }


}