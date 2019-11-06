package id.go.patikab.rsud.remun.remunerasi.view.Login

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import id.go.patikab.rsud.remun.remunerasi.R
import id.go.patikab.rsud.remun.remunerasi.data.api.*
import kotlinx.android.synthetic.main.layout_login.*
import org.jetbrains.anko.sdk25.coroutines.onClick

import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.ServerResponse
import id.go.patikab.rsud.remun.remunerasi.data.lokal.sharepreference.SharePref.*
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.HashMap
import id.go.patikab.rsud.remun.remunerasi.config.util.*
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.LoginResponse

class Login :AppCompatActivity(),LoginView {


    val mPresenter by lazy { LoginPresenter(this) }
    lateinit var username: String
    lateinit var password: String
    var token: String = ""
    var prefs: SharedPreferences? = null
    lateinit var check_login:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_login)
        prefs = getSharedPreferences(pref, Context.MODE_PRIVATE)
//        Log.d("tokene", sharedPreferences.getString(my_token, null)!! + " ")
        token = prefs?.getString(my_token, null).toString()
        check_login = prefs?.getString(username_device,"").toString()


        btn_login.onClick {
            username = edt_username.text.trim().toString()
            password = edt_password.text.trim().toString()
            var valid = true

            if (username.length < 1) {
                edt_username.setError("Username tidak boleh kosong !")
                valid = false
            }
            if (password.length < 1) {
                edt_password.setError("Password tidak boleh kosong")
                valid = false
            }
            if (valid == true) {
                toast("proses login... ")
                val mape = HashMap<String, String>()
                mape["token"] = token_server
                mape["device_token"] = token
                mape["package_name"] = packageName
                mPresenter.getLogin(username,password,token,mape)
            }
        }
//        btn_daftar.onClick {
//            openDaftar()
//            finish()
//        }
        if(check_login !=""){
            openhome()
            finish()
        }
    }

    override fun login(data: List<LoginResponse.Akun>) {
        if (data.size > 0) {
            val editor = prefs!!.edit()
            editor.putString(username_device, data[0].username)
            editor.putString(signature, data[0].signatured)
            editor.putString(status_akun, data[0].status)
            editor.putString(login_session,data[0].kd_dokter)
            editor.putString(nm_dokter,data[0].nama_dokter)
            editor.putString(email_device,data[0].email_user)
            editor.putString(level_user,data[0].level_user)
            editor.apply()
            openhome()
            finish()
        }
 }
    override fun message(msg: String) {
        if(msg != ""){
            toast(msg)
        }
    }
    override fun placeholder() {
    }

    override fun hideload() {
    }

    override fun loading() {
    }


    private fun insert_profile(id: String, nm: String) {
        var getResponse: ApiInterface
        getResponse = ApiClient.getClient().create(ApiInterface::class.java)
        val call = getResponse.insert_profile(id, nm)
        call.enqueue(object : Callback<ServerResponse> {
            override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                if (response.isSuccessful) {
                    Log.d("tg", "Sukses insert profil")
                }

            }

            override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                Log.d("fail", t.message.toString() + " --")
            }
        })
    }
}
