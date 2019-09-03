package id.go.patikab.rsud.remun.remunerasi.slider.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso
import id.go.patikab.rsud.remun.remunerasi.R

class BannerFragment : Fragment() {

    companion object {
        /**
         * new instance pattern for fragment
         */
        @JvmStatic
        fun newInstance(url: String): BannerFragment {
            val newsFragment = BannerFragment()
            val args = Bundle()
            args.putString("img", url)
            newsFragment.arguments = args
            return newsFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_banner, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val url = arguments?.getString("img")
        val imgBanner2:ImageView
        imgBanner2 = view.findViewById(R.id.imgBanner)
        url.let {
            Picasso.get().load(url).into(imgBanner2)
        }
    }

}
