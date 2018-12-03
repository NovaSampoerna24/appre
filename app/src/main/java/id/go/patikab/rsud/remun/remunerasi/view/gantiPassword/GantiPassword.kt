package id.go.patikab.rsud.remun.remunerasi.view.gantiPassword

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.util.Log
import android.view.View
import id.go.patikab.rsud.remun.remunerasi.R
import id.go.patikab.rsud.remun.remunerasi.data.api.ApiClient
import id.go.patikab.rsud.remun.remunerasi.data.api.ApiInterface
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.ServerResponse
import id.go.patikab.rsud.remun.remunerasi.data.lokal.sharepreference.SharePref
import id.go.patikab.rsud.remun.remunerasi.view.page_dialog.CustomDialogDetail
import kotlinx.android.synthetic.main.activity_ubah_password.*
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GantiPassword:AppCompatActivity(){
    internal lateinit var mActionBarToolbar: Toolbar
    var kd_user:String = ""
    var nama_dokter:String = ""
    var prefs :SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ubah_password)

        mActionBarToolbar = findViewById<View>(R.id.toolbar) as Toolbar

        setSupportActionBar(mActionBarToolbar)
        supportActionBar?.setTitle("Ganti Password")
        //        Log.d("tokene", sharedPreferences.getString(my_token, null)!! + " ")
        prefs = this.getSharedPreferences(SharePref.pref,0)
//        Log.d("tokene", sharedPreferences.getString(my_token, null)!! + " ")
        kd_user = prefs?.getString(SharePref.login_session, null).toString()
        nama_dokter = prefs?.getString(SharePref.nm_dokter, null).toString()
//        Log.d("test user",kd_user)
        btn_simpan_profile.setOnClickListener({
            validsitext()

        })
    }

    private fun validsitext() {
        if(password1.text == null || password1.text.length < 6 || password2.text == null || !TextUtils.equals(password1.text,password2.text) ){
            if(password1.text == null){
                password1.setError("Password tidak boleh kosong !")
            }
            if(password1.text.length < 6){
                password1.setError("Minimal 6 karakter !")
            }
            if(password2.text == null){
                password2.setError("Password tidak boleh kosong !")
            }
            if(!TextUtils.equals(password1.text,password2.text)){
                password2.setError("Konfirmasi Password tidak sesuai !")
            }
        }else{
//            toast("ubah sandi")
            if (isOnline()==true){
                simpangantipassword(kd_user,password1.text.toString())
            }else{
                dialog_failure()
            }

        }


    }
    fun isOnline(): Boolean {
        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        //jika ada koneksi return true
        return if (netInfo != null && netInfo.isConnected) {
            true
        } else false
        //jika tidak ada koneksi return false
    }

    private fun dialog_failure() {
        val cdd = CustomDialogDetail(this)
        cdd.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        cdd.show()
    }
    private fun simpangantipassword(text1:String, text2: String) {

        var getResponse: ApiInterface
        getResponse = ApiClient.getClient().create(ApiInterface::class.java)
        val call: Call<ServerResponse>
        call = getResponse.toUbahPassword(text1, text2)
        call.enqueue(object : Callback<ServerResponse> {
            override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                var message = response.body()?.msg.toString()
                if (response.isSuccessful) {
                    toast(message)
                    finish()
                } else {
                    toast(message)
                }
            }
            override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                toast(t.message + " ")
            }
        })
    }
}