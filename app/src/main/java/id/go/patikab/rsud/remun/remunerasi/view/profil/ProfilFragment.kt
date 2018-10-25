package id.go.patikab.rsud.remun.remunerasi.view.profil

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.go.patikab.rsud.remun.remunerasi.*
import id.go.patikab.rsud.remun.remunerasi.view.DetailProfil.DetailProfil
import kotlinx.android.synthetic.main.layout_profil_container.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import id.go.patikab.rsud.remun.remunerasi.view.setAkun.AkunFragment
import id.go.patikab.rsud.remun.remunerasi.view.Notifikasi.*




class ProfilFragment : Fragment(), ProfilView {

    val mPresenter by lazy { ProfilPresenter(this) }
    private val tabIcons = intArrayOf(
            R.drawable.ic_account,
            R.drawable.ic_notifications_width,
            R.drawable.ic_person_width)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        refresh()
        return inflater.inflate(R.layout.layout_profil_container, container, false)
    }

    fun refresh() {
        launch(UI) {
            mPresenter.showadapter()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState == null) {
            refresh()
        }


    }

    override fun show() {
        viewpage_team_detail.adapter = TeamPagerAdapter("No overview", getChildFragmentManager())
        tab_team_detail.setupWithViewPager(viewpage_team_detail)
        setupTabIcons();
    }

    private fun setupTabIcons() {
        tab_team_detail.getTabAt(0)?.setIcon(tabIcons[0])
        tab_team_detail.getTabAt(1)?.setIcon(tabIcons[1])
        tab_team_detail.getTabAt(2)?.setIcon(tabIcons[2])
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

internal class TeamPagerAdapter(private val mOverview: String, fragmentManager: FragmentManager)
    : FragmentPagerAdapter(fragmentManager) {

    private val mPages by lazy {
        listOf(
                DetailProfil().apply {},
                Notifikasi().apply {},
                AkunFragment().apply {}
        )
    }

    private val mTitles by lazy { listOf("Profil", "Notifikasi", "Akun") }

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
