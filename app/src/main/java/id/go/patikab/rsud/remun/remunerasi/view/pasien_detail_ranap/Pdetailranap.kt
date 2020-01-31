package id.go.patikab.rsud.remun.remunerasi.view.pasien_detail_ranap
import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.squareup.picasso.Picasso
import id.go.patikab.rsud.remun.remunerasi.R
import id.go.patikab.rsud.remun.remunerasi.data.lokal.sharepreference.SharePref.*
import org.jetbrains.anko.support.v4.ctx
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.android.synthetic.main.detail_pasien_ranap.*
import id.go.patikab.rsud.remun.remunerasi.config.adapter.PasienAdapter
import id.go.patikab.rsud.remun.remunerasi.data.api.ApiClient
import id.go.patikab.rsud.remun.remunerasi.data.api.ApiInterface
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.*
import id.go.patikab.rsud.remun.remunerasi.view.pasien_rajal.PranapView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.toast
import id.go.patikab.rsud.remun.remunerasi.view.pasien_detail.PdetailrajalPresenter
import kotlinx.android.synthetic.main.form_laporkan.view.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.backgroundDrawable
import org.jetbrains.anko.sdk25.coroutines.onClick
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Pdetailranap : AppCompatActivity(), PdetailranapView {


    val mPresenter by lazy { PdetailranapPresenter(this) }
    var kd_user: String = ""
    var nama_dokter: String = ""
    var prefs: SharedPreferences? = null
    var email: String = ""
    var idxdaftar = ""
    var nomr = ""

    internal lateinit var mActionBarToolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_pasien_ranap)
        prefs = getSharedPreferences(pref, 0)
//        Log.d("tokene", sharedPreferences.getString(my_token, null)!! + " ")
        kd_user = prefs?.getString(login_session, null).toString()
        nama_dokter = prefs?.getString(nm_dokter, null).toString()
        email = prefs?.getString(email_device, "").toString()
        idxdaftar = intent?.getStringExtra("idxdaftar") ?: ""
        nomr = intent?.getStringExtra("nomr") ?: ""

        launch(UI) {
            mPresenter.getPdetailRanap(idxdaftar,nomr)
        }
//        btn_visit.onClick {
//            postvisit(kd_user,idxdaftar)
//        }
//        btnlaporkan.onClick {
//            dialoglaporkan(kd_user,idxdaftar)
//        }
    }
    fun dialoglaporkan(kduser:String,idxdaftar: String){
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.form_laporkan, null)
        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
                .setTitle("Laporkan Pasien")
        //show dialog
        val  mAlertDialog = mBuilder.show()
        //login button click of custom layout
        mDialogView.dialogLoginBtn.setOnClickListener {
            //dismiss dialog
            mAlertDialog.dismiss()
            //get text from EditTexts of custom layout
            val name = mDialogView.dialogNameEt.text.toString()
            //set the input text in TextView
//            btnlaporkan.setText("Name:"+ name)
            postlaporkan(kduser,idxdaftar,name)
        }
        //cancel button click of custom layout
        mDialogView.dialogCancelBtn.setOnClickListener {
            //dismiss dialog
            mAlertDialog.dismiss()
        }

    }
    fun postlaporkan(kduser:String,idxdaftar:String,keterangan:String){
        var getResponse: ApiInterface
        getResponse = ApiClient.getClient().create(ApiInterface::class.java)
        val call: Call<MessageRes>
        call = getResponse.Postlaporkan(kduser,idxdaftar,keterangan)
//        Log.d("test id",id)
        call.enqueue(object : Callback<MessageRes> {
            override fun onFailure(call: Call<MessageRes>, t: Throwable) {
                Log.d("Failure", t.message.toString() + " --")
            }
            override fun onResponse(call: Call<MessageRes>, response: Response<MessageRes>) {
                Log.d("response code nt",response.code().toString()+" -- ")
//                Log.d("response",response.body().toString())
                if (response.isSuccessful) {
                    val notif = response.body()?.message.toString()
                    Log.d("test 3",notif.toString())

                    if (notif == "sukses") {
//                        mView.showDataPendaftaran(notif)
                        Log.d("message", notif + " --")

//                        var img = resources.getDrawable( R.drawable.ic_done);
//                        img.setBounds( 0, 0, 60, 60 );
//                        btnlaporkan.setCompoundDrawables(null,null,img,null);
//                        mView.hideloading()
                    }else{
//                        mView.placeholder()
                        Log.d("message", "gagal" + " --")

                    }
                }else{
//                    mView.placeholder()
                    Log.d("message", "gagal2" + " --")
                }
            }
        });
    }
    fun postvisit(kd_user:String,idxdaftar: String){
        var getResponse: ApiInterface
        getResponse = ApiClient.getClient().create(ApiInterface::class.java)
        val call: Call<MessageRes>
        call = getResponse.Postvisit(kd_user,idxdaftar)
//        Log.d("test id",id)
        call.enqueue(object : Callback<MessageRes> {
            override fun onFailure(call: Call<MessageRes>, t: Throwable) {
                Log.d("Failure", t.message.toString() + " --")

            }
            override fun onResponse(call: Call<MessageRes>, response: Response<MessageRes>) {
                Log.d("response code nt",response.code().toString()+" -- ")
//                Log.d("response",response.body().toString())
                if (response.isSuccessful) {
                    val notif = response.body()?.message.toString()
                    Log.d("test 3",notif.toString())

                    if (notif == "sukses") {
//                        mView.showDataPendaftaran(notif)
                        Log.d("message", notif + " --")
//                        mView.hideloading()
//                        var img = resources.getDrawable( R.drawable.ic_done);
//                        img.setBounds( 0, 0, 60, 60 );
//                        btn_visit.setCompoundDrawables(null,null,img,null);
                    }else{
//                        mView.placeholder()
                        Log.d("message", "gagal" + " --")
                    }
                }else{
//                    mView.placeholder()
                    Log.d("message", "gagal2" + " --")
                }
            }
        });
    }

    override fun showDataPendaftaran(data: DetailPasienRanap.dataPendaftaran) {
//        Picasso.get().load(player.bannerUrl).into(iv_banner)
//        tv_position.text = data.position

        tv_weight.text = data.masukrs
        tv_height.text = data.keluarrs
//        txt_kunjungan.text = data.tanggalmasuk
        txt_nama_pasien.text = data.NAMA
        txt_nomr.text = data.NOMR
        app_bar.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            internal var isShow = true
            internal var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    toolbar_layout.title = data.NAMA
                    getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
                    getSupportActionBar()?.setDisplayShowHomeEnabled(true);
                    isShow = true
                } else if (isShow) {
                    toolbar_layout.title = " "//careful there should a space between double quote otherwise it wont work
                    isShow = false
                }
            }
        })
//        tv_overview.text = data.overview

//        supportActionBar?.title = player.name
    }

    override fun hideloading() {
        progress.visibility = View.GONE
        fr2.visibility = View.VISIBLE
        tidak_ada.visibility = View.GONE
//        recycle_datae.visibility = View.VISIBLE
//        progress_circular.visibility = View.GONE
//        no_data.visibility = View.GONE
    }
    override fun showloading() {
        progress.visibility = View.VISIBLE
        fr2.visibility = View.GONE
//        recycle_datae.visibility = View.GONE
//        progress_circular.visibility = View.VISIBLE
//        no_data.visibility = View.GONE
    }
    override fun placeholder() {
        progress.visibility = View.GONE
        fr2.visibility = View.GONE
        tidak_ada.visibility = View.VISIBLE
//        recycle_datae.visibility = View.GONE
//        progress_circular.visibility = View.GONE
//        no_data.visibility = View.VISIBLE
    }

}
