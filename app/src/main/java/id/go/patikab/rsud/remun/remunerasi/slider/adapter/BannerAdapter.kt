package id.go.patikab.rsud.remun.remunerasi.slider.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import id.go.patikab.rsud.remun.remunerasi.slider.fragment.BannerFragment
import id.go.patikab.rsud.remun.remunerasi.slider.model.BannerPromo

class BannerAdapter(fragmentManager: FragmentManager,
                    private val banners: List<BannerPromo>) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(pos: Int): Fragment {
        return BannerFragment.newInstance(banners[pos].image)
    }

    override fun getCount(): Int = banners.size

}