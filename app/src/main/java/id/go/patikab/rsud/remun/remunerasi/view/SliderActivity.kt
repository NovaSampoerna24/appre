package id.go.patikab.rsud.remun.remunerasi.view


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import id.go.patikab.rsud.remun.remunerasi.slider.groupie.BannerListener
import id.go.patikab.rsud.remun.remunerasi.slider.model.BannerPromo
import  id.go.patikab.rsud.remun.remunerasi.R
import id.go.patikab.rsud.remun.remunerasi.slider.groupie.BannerCarouselItem
import kotlinx.android.synthetic.main.main_slider.*

class SliderActivity:AppCompatActivity() ,BannerListener{

    private var groupAdapter = GroupAdapter<ViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_slider)

        val promos = listOf(
                BannerPromo(name = "Puncak badai uang",
                        image = "https://s2.bukalapak.com/uploads/promo_partnerinfo_bloggy/2842/Bloggy_1_puncak.jpg"),
                BannerPromo(
                        name = "hati hati ada guncangan badai uang",
                        image = "https://s4.bukalapak.com/uploads/promo_partnerinfo_bloggy/5042/Bloggy_1.jpg"
                ),
                BannerPromo(name = "Puncak badai uang",
                        image = "https://s2.bukalapak.com/uploads/promo_partnerinfo_bloggy/2842/Bloggy_1_puncak.jpg"),
                BannerPromo(
                        name = "hati hati ada guncangan badai uang",
                        image = "https://s4.bukalapak.com/uploads/promo_partnerinfo_bloggy/5042/Bloggy_1.jpg"
                ),
                BannerPromo(name = "Puncak badai uang",
                        image = "https://s2.bukalapak.com/uploads/promo_partnerinfo_bloggy/2842/Bloggy_1_puncak.jpg"),
                BannerPromo(
                        name = "hati hati ada guncangan badai uang",
                        image = "https://s4.bukalapak.com/uploads/promo_partnerinfo_bloggy/5042/Bloggy_1.jpg"
                )
        )
        rvMain.apply {
            layoutManager = LinearLayoutManager(this@SliderActivity)
            adapter = groupAdapter
        }
        val bannerCarouselItem = BannerCarouselItem(promos, supportFragmentManager, this)
        groupAdapter.add(bannerCarouselItem)
    }

    override fun onSeeAllPromoClick() {
         }

    override fun onBannerClick(promo: BannerPromo) {
         }

}