package id.go.patikab.rsud.remun.remunerasi.view.WebView

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.webkit.WebSettings
import id.go.patikab.rsud.remun.remunerasi.R
import android.webkit.WebViewClient
import android.webkit.WebView
import id.go.patikab.rsud.remun.remunerasi.data.lokal.sharepreference.SharePref
import kotlinx.android.synthetic.main.layout_webview_jaspel.*
import org.jetbrains.anko.support.v4.ctx
import id.go.patikab.rsud.remun.remunerasi.data.api.ApiClient.base_Url

class ViewJaspel :AppCompatActivity(){
    lateinit var url:String
    lateinit var dari:String
    lateinit var sampai:String
    lateinit var sign:String
    lateinit var id_dokter:String
    var prefs : SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_webview_jaspel)

        dari = intent.getStringExtra("dari")
        sampai = intent.getStringExtra("sampai")
        sign = intent?.getStringExtra("sign").toString()
        prefs = getSharedPreferences(SharePref.pref, Context.MODE_PRIVATE)
        id_dokter = prefs?.getString(SharePref.login_session, null).toString()

        url = "${base_Url}android/service/view_tindakan/index.php?id_dokter=${id_dokter}&dari=${dari}&sampai=${sampai}&signature=${sign}"

//        Log.d("url webview ",url+"")
        web_view.loadUrl(url)

        web_view.getSettings().setLoadsImagesAutomatically(true);
        web_view.getSettings().setJavaScriptEnabled(true);
        web_view.getSettings().setBuiltInZoomControls(true);
        web_view.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        web_view.setFocusable(true);
        web_view.setFocusableInTouchMode(true);
        web_view.getSettings().setJavaScriptEnabled(true);
        web_view.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        web_view.getSettings().setDomStorageEnabled(true);
        web_view.getSettings().setDatabaseEnabled(true);
        web_view.getSettings().setAppCacheEnabled(true);
        web_view.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        web_view.webViewClient = WebViewClient()
    }
}