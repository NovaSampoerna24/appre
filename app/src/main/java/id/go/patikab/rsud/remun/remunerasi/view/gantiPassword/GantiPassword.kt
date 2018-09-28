package id.go.patikab.rsud.remun.remunerasi.view.gantiPassword

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import id.go.patikab.rsud.remun.remunerasi.R
import id.go.patikab.rsud.remun.remunerasi.data.api.ApiClient
import id.go.patikab.rsud.remun.remunerasi.data.api.ApiInterface
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.ServerResponse
import id.go.patikab.rsud.remun.remunerasi.view.page_dialog.CustomDialogDetail
import kotlinx.android.synthetic.main.ubah_password_layout.*
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GantiPassword:AppCompatActivity(){
    var id_d: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ubah_password_layout)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        id_d = intent?.getStringExtra("id_dokter")

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
                simpangantipassword(id_d.toString(),password1.text.toString())
            }else{
                dialog_failure()
            }

        }


    }
    fun isOnline(): Boolean {
        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        //jika ada koneksi return true
        return if (netInfo != null && netInfo.isConnectedOrConnecting) {
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