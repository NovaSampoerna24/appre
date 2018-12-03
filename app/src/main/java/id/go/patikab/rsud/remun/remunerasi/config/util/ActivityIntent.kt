package id.go.patikab.rsud.remun.remunerasi.config.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import id.go.patikab.rsud.remun.remunerasi.data.lokal.sharepreference.SharePref.*
import id.go.patikab.rsud.remun.remunerasi.data.api.ApiClient
import id.go.patikab.rsud.remun.remunerasi.data.api.ApiInterface
import id.go.patikab.rsud.remun.remunerasi.view.Auth.AuthActivity
import id.go.patikab.rsud.remun.remunerasi.view.gantiPassword.*
import id.go.patikab.rsud.remun.remunerasi.view.ubahfoto.*
import id.go.patikab.rsud.remun.remunerasi.view.Notifikasi.*
import id.go.patikab.rsud.remun.remunerasi.view.notifikasidetail.*
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.*
import id.go.patikab.rsud.remun.remunerasi.view.MainApps
import org.jetbrains.anko.act
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun Activity.openUbahFoto(iddokter: String, namaDokter: String) {
    startActivity(Intent(this, UbahFoto::class.java).apply {
        putExtra("id_dokter", iddokter)
        putExtra("nama_dokter", namaDokter)
    })
}

fun Activity.logout() {
    var sharedPreferences: SharedPreferences
    sharedPreferences = getSharedPreferences(pref, Context.MODE_PRIVATE)

    var psw = sharedPreferences.getString("psw", null)
    var id = sharedPreferences.getString(login_session, null)
    var getResponse: ApiInterface
    getResponse  = ApiClient.getClient().create(ApiInterface::class.java)
    val call: Call<AuthResponse>
    call = getResponse.getloginresponse(id,psw,"")
    call.enqueue(object:Callback<AuthResponse>{
        override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
        }
        override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
        }
    })

    var editor = sharedPreferences.edit()
    editor.putString(login_session, "")
    editor.commit()
    finish()
    startActivity(Intent(this, AuthActivity::class.java))
}

fun Activity.opengantiPassword() {
    startActivity(Intent(this, GantiPassword::class.java).apply {

    })
}
fun Activity.openhome(){
    startActivity(Intent(this, MainApps::class.java).apply {
        val bundle = Bundle()
        bundle.putString("fp","yes")
        putExtras(bundle)
    })
}

fun Activity.openNotif(notif:NotifikasiResponse.Notif){
    startActivity(Intent(this, NotifikasiDetail::class.java).apply {
        val bundle = Bundle()
        bundle.putString("id_pesan",notif.id)
        bundle.putString("judul",notif.judul)
        bundle.putString("pesan",notif.pesan)
        bundle.putString("waktu",notif.readableDate)
        bundle.putString("label",notif.jenis_p)
        putExtras(bundle)

    })

}



