package id.go.patikab.rsud.remun.remunerasi.view.aktivasi_akun

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import id.go.patikab.rsud.remun.remunerasi.R
import id.go.patikab.rsud.remun.remunerasi.data.api.ApiClient
import id.go.patikab.rsud.remun.remunerasi.data.api.ApiInterface
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.AktivasiResponse
import kotlinx.android.synthetic.main.layout_aktivasi.*
import org.jetbrains.anko.longToast
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast
import id.go.patikab.rsud.remun.remunerasi.data.lokal.sharepreference.SharePref.*
import id.go.patikab.rsud.remun.remunerasi.config.util.openhome
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Aktifasi : AppCompatActivity(), aktivasiView {
    var prefs: SharedPreferences? = null
    val mPresenter by lazy { AktifasiPresenter(this) }
    lateinit var token:String
    lateinit var username:String
    lateinit var email:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_aktivasi)
        prefs = getSharedPreferences(pref, Context.MODE_PRIVATE)
        username = prefs?.getString(username_device, "").toString()
        email = prefs?.getString(email_device, "").toString()
        token = prefs?.getString(my_token, "").toString()
        txt_username.text = username +""
//        txt_email.text = email + ""
        btn_cek.onClick {
            longToast("Memeriksa username...")
            Log.d("chek", token_server + "," + token + "," + packageName)
            val mape = java.util.HashMap<String, String>()
            mape["token"] = token_server
            mape["device_token"] = token
            mape["package_name"] = packageName
            mPresenter.getaktivasi(username, mape)
        }
        if (prefs?.getString(status_akun, "") != "0") {
            openhome()
            this.finish()
        }

    }

    override fun hasil_chek(data: List<AktivasiResponse.Status>) {
        if (data.size > 0) {
            var kd_dokter = data[0].kd_dokter
            var emaile = data.get(0).email
            var status = data[0].status_akun
            var signatured = data[0].signaturee
            var nama_doktere = data[0].nama_dokter
            val editor = prefs!!.edit()
            editor.putString(status_akun, status)
            editor.putString(login_session, kd_dokter)
            editor.putString(email_device, emaile)
            editor.putString(signature,signatured)
            editor.putString(nm_dokter,nama_doktere)
            editor.apply()
            openhome()
            this.finish()
        }
    }

    override fun message(msg: String) {
        toast(msg)
    }
}

interface aktivasiView {
    fun hasil_chek(data: List<AktivasiResponse.Status>)
    fun message(msg: String)
}

data class AktifasiPresenter(private val mView: aktivasiView) {
    suspend fun getaktivasi(username: String,map:HashMap<String,String>){
        var getResponse:ApiInterface
        getResponse = ApiClient.getClient().create(ApiInterface::class.java)
        val call:Call<AktivasiResponse>
        call = getResponse.cek_aktivasi(username,map)
        call.enqueue(object :Callback<AktivasiResponse>{
            override fun onFailure(call: Call<AktivasiResponse>, t: Throwable) {
                Log.d("Failure 1", t.message.toString() + " --")
            }

            override fun onResponse(call: Call<AktivasiResponse>, response: Response<AktivasiResponse>) {
                Log.d("response code", response.code().toString() + " -- ")
                if (response.code() == 200) {
                    if (response.isSuccessful) {
                        val status = response.body()?.status.toString()
                        val message = response.body()?.message.toString()
                        Log.d("status chek",status+"")
                        if (status == "sukses") {
//                        mView.hideload()v
                            val datae =response.body()?.dataUser
                            if (datae != null) {
                                if (datae?.size > 0) {
                                    mView.message(message)
                                    mView.hasil_chek(datae)
                                }
                            } else {
                                mView.message(message)
                            }

                        } else {
                            mView.message(message+"")
                        }

                    } else {
                        mView.message("Terjadi gangguan saat menjangkau server 2 !")
//                    mView.hideload()
                    }
                } else {
                    mView.message("Terjadi gangguan saat menjangkau server 3 !")
                }
            }

        })
    }
//    suspend fun getaktivasi2(username: String, map: HashMap<String, String>) {
//        Log.d("chek username",username+"")
//        var getResponse: ApiInterface
//        getResponse = ApiClient.getClient().create(ApiInterface::class.java)
//        val call: Call<AktivasiResponse>
//        call = getResponse.cek_aktivasi(username, map)
//        call.enqueue(object : Callback<AktivasiResponse> {
//
//            override fun onFailure(call: Call<AktivasiResponse>, t: Throwable) {
//
//            }
//
//            override fun onResponse(call: Call<AktivasiResponse>, response: Response<AktivasiResponse>) {
//
//            }
//        })
//    }
}
