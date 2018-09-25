package id.go.patikab.rsud.remun.remunerasi.view.profile


import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.Spinner
import android.widget.Toast
import com.squareup.picasso.Picasso

import id.go.patikab.rsud.remun.remunerasi.R
import id.go.patikab.rsud.remun.remunerasi.config.util.openUbahFoto
import id.go.patikab.rsud.remun.remunerasi.data.api.ApiClient.base_Url_upload
import id.go.patikab.rsud.remun.remunerasi.data.api.ApiInterface
import id.go.patikab.rsud.remun.remunerasi.data.lokal.sharepreference.SharePref.*
import kotlinx.android.synthetic.main.fragment_profile.*
import id.go.patikab.rsud.remun.remunerasi.data.api.`object`.ProfileGetData
import org.jetbrains.anko.toast
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import  id.go.patikab.rsud.remun.remunerasi.data.api.`interface`.*
import id.go.patikab.rsud.remun.remunerasi.view.page_dialog.*
import id.go.patikab.rsud.remun.remunerasi.data.lokal.*
import id.go.patikab.rsud.remun.remunerasi.data.lokal.`object`.Informasi
import id.go.patikab.rsud.remun.remunerasi.config.adapter.*
import id.go.patikab.rsud.remun.remunerasi.data.lokal.*

class ProfileFragment : Fragment(), ProfileView {


    private val mApi by lazy { ApiInterfac() }
    private val mDb by lazy { AnkoDb(context!!) }
    private val mPresenter by lazy { ProfilePresenter(this, mApi, mDb) }


    internal lateinit var sharedPreferences: SharedPreferences
    internal var kd_user: String? = null
    internal var nama_dokter: String? = null
    lateinit var message: String
    val actionBar: ActionBar?
        get() = (activity as AppCompatActivity).supportActionBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        actionBar!!.setTitle("Profile")

        sharedPreferences = activity!!.getSharedPreferences(pref, Context.MODE_PRIVATE)
        Log.d("tokene", sharedPreferences.getString(my_token, null)!! + " ")
        kd_user = sharedPreferences.getString(login_session, null)
        nama_dokter = sharedPreferences.getString(nm_dokter, null)
        //        nm_dk.setText(nama_dokter);
        k_foto.setOnClickListener({
            activity?.openUbahFoto(kd_user.toString(), nama_dokter.toString())
        })
        txt_nama.setText(nama_dokter)
//        mPresenter?.getProfile(kd_user?:"")
        launch(UI) {
            if (isOnline() == true) {
                mPresenter.fetchProfil(kd_user.toString())
                mPresenter.getInformasi(kd_user.toString())
            } else {
                dialog_failure()
            }

        }

    }

    //method untuk cek koneksi
    fun isOnline(): Boolean {
        val cm = activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        //jika ada koneksi return true
        return if (netInfo != null && netInfo.isConnectedOrConnecting) {
            true
        } else false
        //jika tidak ada koneksi return false
    }

    private fun dialog_failure() {
        val cdd = CustomDialogDetail(activity)
        cdd.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        cdd.show()
    }

    override fun onResume() {
        super.onResume()
        launch(UI) {
            if (isOnline() == true) {
                mPresenter.fetchProfil(kd_user.toString())
            }
        }
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showDetail(detail: ProfileGetData.DetailP) {
        Picasso.get().load(base_Url_upload + detail.foto).error(R.drawable.ic_profil).into(profile_image2)
    }

    override fun showInformasi(informasi: List<Informasi>) {
        recycle?.let {
            with(recycle) {
                layoutManager = LinearLayoutManager(context)
                adapter = InformasiAdapter(informasi) { informasine ->
                    activity?.toast(" test " + informasine.judul)
                }
            }
        }
    }

    override fun showplaceholder() {
      }


}// Required empty public constructor

