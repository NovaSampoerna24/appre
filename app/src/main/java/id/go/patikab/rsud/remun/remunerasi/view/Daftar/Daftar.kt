package id.go.patikab.rsud.remun.remunerasi.view.Daftar

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import id.go.patikab.rsud.remun.remunerasi.R
import kotlinx.android.synthetic.main.layout_daftar.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import id.go.patikab.rsud.remun.remunerasi.config.util.openLogin
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.DaftarResponse
import id.go.patikab.rsud.remun.remunerasi.data.lokal.sharepreference.SharePref.*
import org.jetbrains.anko.toast
import java.util.HashMap
import id.go.patikab.rsud.remun.remunerasi.config.util.openhome
class Daftar:AppCompatActivity(),DaftarView{



    lateinit var username: String
    lateinit var password: String
    lateinit var email: String
    lateinit var token: String
    lateinit var repassword: String
    var prefs: SharedPreferences? = null

    val mPresenter by lazy { DaftarPresenter(this) }
lateinit var check_login:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_daftar)

        prefs = getSharedPreferences(pref, Context.MODE_PRIVATE)
//        Log.d("tokene", sharedPreferences.getString(my_token, null)!! + " ")
        token = prefs?.getString(my_token, null).toString()
        check_login = prefs?.getString(username_device,"").toString()
        if(check_login !=""){
            openhome()
            finish()
        }
        btn_register.onClick {
            username = edt_username.text.trim().toString()
            password = edt_password.text.trim().toString()
            email = edt_email.text.trim().toString()
            repassword = edt_password_ulang.text.trim().toString()

            var valid = true

            if (username.length < 1) {
                edt_username.setError("Username tidak boleh kosong !")
                valid = false
            }
            if (email.length < 1) {
                edt_email.setError("Email tidak boleh kosong !")
                valid = false
            }
            if (password.length < 1) {
                edt_password.setError("Password tidak boleh kosong !")
                valid = false
            }
            if (repassword.length < 1) {
                edt_password_ulang.setError("Konfirmasi password tidak boleh kosong !")
                valid = false
            }
            if (password.length < 6) {
                edt_password_ulang.setError("Password minimal 6 karakter")
                valid = false
            }
            if (repassword != password) {
                edt_password_ulang.setError("Konfirmasi password tidak sama !")
                valid = false
            }
            if (valid == true) {
                toast("proses daftar...")
//                toast("proses login... ")
                val mape = HashMap<String, String>()
                mape["token"] = token_server
                mape["device_token"] = token
                mape["package_name"] = packageName
                mPresenter.goesDaftar(username,email,password,repassword,mape)

            }
        }
        btn_login.onClick {
            openLogin()
            finish()
        }

    }
    override fun daftar(data: List<DaftarResponse.Akun>) {
        if(data.size > 0){
            val editor = prefs!!.edit()
            editor.putString(username_device,data[0].username)
            editor.putString(signature, data[0].signatured)
            editor.putString(status_akun, data[0].status)
            editor.putString(nm_dokter,data[0].username)
            editor.apply()
            Log.d("status_akun",data[0].status)
            openhome()
          finish()
        }
    }

    override fun placeholder() {
      }

    override fun hideload() {
     }

    override fun loading() {
     }

    override fun message(msg: String) {
        toast(msg)
     }
}