package id.go.patikab.rsud.remun.remunerasi.view.Ringkasan

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.go.patikab.rsud.remun.remunerasi.view.RingkasanPelaksana.Pasien.TkPasienFragment
import id.go.patikab.rsud.remun.remunerasi.view.RingkasanPelaksana.TaksiranPenghasilan.*
import id.go.patikab.rsud.remun.remunerasi.view.RingkasanPelaksana.Tindakan.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import id.go.patikab.rsud.remun.remunerasi.R
import kotlinx.android.synthetic.main.activity_ringkasan.*

class Ringkasan : AppCompatActivity(), RingkasanView {

    val mPresenter by lazy { RingkasanPresenter(this) }
    internal lateinit var mActionBarToolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      refresh()
        setContentView(R.layout.activity_ringkasan)

        mActionBarToolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(mActionBarToolbar)
        supportActionBar?.setTitle("Jasa Pelaksana")
    }


    override fun show() {
        viewpage.adapter = TeamPagerAdapter("No overview", supportFragmentManager)
        tabpage.setupWithViewPager(viewpage)
    }
    fun refresh() {
        launch(UI) {
            mPresenter.showadapter()
        }
    }
}

internal class TeamPagerAdapter(private val mOverview: String, fragmentManager: FragmentManager)
    : FragmentPagerAdapter(fragmentManager) {

    private val mPages by lazy {
        listOf(
                TkPenghasilanFragment().apply {},
                TkPasienFragment().apply {},
                TkTindakanFragment().apply {}
        )
    }
    private val mTitles by lazy { listOf("Jasa Pelayanan", "Jumlah Pasien","Jumlah Tindakan") }

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
