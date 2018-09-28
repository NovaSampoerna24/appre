package id.go.patikab.rsud.remun.remunerasi.view.profil

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
import com.squareup.picasso.Picasso

import id.go.patikab.rsud.remun.remunerasi.R
import id.go.patikab.rsud.remun.remunerasi.data.lokal.sharepreference.SharePref.*
import kotlinx.android.synthetic.main.layout_detail_profil.*
import id.go.patikab.rsud.remun.remunerasi.config.util.*
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.ProfilDetail
import id.go.patikab.rsud.remun.remunerasi.data.lokal.`object`.Informasi
import id.go.patikab.rsud.remun.remunerasi.data.api.ApiClient.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.support.v4.onRefresh
import id.go.patikab.rsud.remun.remunerasi.view.page_dialog.*

class DetailProfil : Fragment() , ProfilView {

    val mPresenter by lazy { ProfilPresenter(this) }
    internal lateinit var sharedPreferences: SharedPreferences
    internal var kd_user: String = ""
    internal var nama_dokter: String = ""
    var foton = ""
    lateinit var editor:SharedPreferences.Editor

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        sharedPreferences = activity!!.getSharedPreferences(pref, Context.MODE_PRIVATE)
//        Log.d("tokene", sharedPreferences.getString(my_token, null)!! + " ")
        kd_user = sharedPreferences?.getString(login_session, null)
        nama_dokter = sharedPreferences?.getString(nm_dokter, null)
        return inflater.inflate(R.layout.layout_detail_profil, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        txt_overview.text = mOverview
        if(savedInstanceState == null){
            if (txt_nama.text == "") {
                txt_nama.text = nama_dokter
            }
            if(foton == ""){
                if(isOnline() == true){
                    refresh()
                }else{
                   dialog_failure()
                }
            }else{
                Picasso.get()
                        .load(base_Url_upload + foton)
                        .error(R.drawable.dokterdefault)
                        .into(profile_image2)
            }

        }
        swiperefresh.onRefresh {
            refresh()
            swiperefresh.isRefreshing = false
        }

        profile_image2.setOnClickListener {
            activity?.openUbahFoto(kd_user, nama_dokter);
        }
    }
    fun refresh(){
        launch(UI) {
            mPresenter.fetchProfilRetro(kd_user)
        }
    }
    override fun showDetail(detail: ProfilDetail) {
        if (foton == "") {
            Picasso.get()
                    .load(base_Url_upload + detail.foto)
                    .error(R.drawable.dokterdefault)
                    .into(profile_image2)
        }


     }

    fun isOnline():Boolean {
        val cm = activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        //jika ada koneksi return true
        return if (netInfo != null && netInfo.isConnectedOrConnecting) {
            true
        } else false
        //jika tidak ada koneksi return false
    }
    fun dialog_failure() {
        val cdd = CustomDialogDetail(context)
        cdd.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        cdd.show()
    }

    override fun showInformasi(informasi: List<Informasi>) {
        }

    override fun showLoading() {
        }

    override fun hideLoading() {
        }

    override fun showplaceholder() {
         }

    override fun show() {
          }

}