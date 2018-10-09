package id.go.patikab.rsud.remun.remunerasi.view.profil

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import id.go.patikab.rsud.remun.remunerasi.data.api.ApiClient.*
import id.go.patikab.rsud.remun.remunerasi.*
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.ProfilDetail
import id.go.patikab.rsud.remun.remunerasi.data.lokal.`object`.Informasi
import kotlinx.android.synthetic.main.profil_layout.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import id.go.patikab.rsud.remun.remunerasi.view.page_dialog.*
import  id.go.patikab.rsud.remun.remunerasi.data.lokal.sharepreference.SharePref.*
import id.go.patikab.rsud.remun.remunerasi.config.util.*
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.TindakanGetData

class ProfilFragment : Fragment(), ProfilView {

    val mPresenter by lazy { ProfilPresenter(this) }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        refresh()
        return inflater.inflate(R.layout.profil_layout, container, false)
    }

    fun refresh() {
        launch(UI) {
            mPresenter.showadapter()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if(savedInstanceState == null){
           refresh()
        }


    }

    override fun show() {
        viewpage_team_detail.adapter = TeamPagerAdapter("No overview", getChildFragmentManager())
        tab_team_detail.setupWithViewPager(viewpage_team_detail)
    }
    //method untuk cek koneksi
//    fun isOnline(): Boolean {
//        val cm = activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        val netInfo = cm.activeNetworkInfo
//        //jika ada koneksi return true
//        return if (netInfo != null && netInfo.isConnectedOrConnecting) {
//            true
//        } else false
//        //jika tidak ada koneksi return false
//    }
    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showplaceholder() {
    }

}

internal class TeamPagerAdapter(private val mOverview: String,
                                fragmentManager: FragmentManager)
    : FragmentPagerAdapter(fragmentManager) {

    private val mPages by lazy {
        listOf(
                DetailProfil().apply { },
                AkunFragment().apply {}
        )
    }

    private val mTitles by lazy { listOf("Profil", "Akun") }

    override fun getItem(position: Int): Fragment {
        return mPages[position]
    }

    override fun getCount(): Int {
        return mPages.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mTitles[position]
    }


}
