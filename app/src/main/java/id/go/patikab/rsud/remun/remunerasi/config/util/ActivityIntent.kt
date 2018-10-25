package id.go.patikab.rsud.remun.remunerasi.config.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import id.go.patikab.rsud.remun.remunerasi.data.lokal.sharepreference.SharePref.*
import id.go.patikab.rsud.remun.remunerasi.data.api.*
import id.go.patikab.rsud.remun.remunerasi.view.Auth.*
import id.go.patikab.rsud.remun.remunerasi.view.gantiPassword.*
import id.go.patikab.rsud.remun.remunerasi.view.ubahfoto.*
import id.go.patikab.rsud.remun.remunerasi.view.Notifikasi.*
import id.go.patikab.rsud.remun.remunerasi.view.notifikasidetail.*
import id.go.patikab.rsud.remun.remunerasi.view.DetailTindakan.*
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.*
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
    val editor = sharedPreferences.edit()
    editor.putString(login_session, null)
    editor.commit()

    startActivity(Intent(this, AuthActivity::class.java))
}

fun Activity.opengantiPassword(iddokter: String) {
    startActivity(Intent(this, GantiPassword::class.java).apply {
        putExtra("id_dokter", iddokter)
    })
}
fun Activity.openPembayaranRemid(id_dokter: String,tgl_awal:String,tgl_akhir:String){
    startActivity(Intent(this,DetailRM::class.java).apply {
        putExtra("id_dokter",id_dokter)
        putExtra("tgl_awal",tgl_awal)
        putExtra("tgl_akhir",tgl_akhir)
    })

}
fun Activity.openNotif(notif:NotifikasiResponse.Notif){
    startActivity(Intent(this, NotifikasiDetail::class.java).apply {

        var preferences: SharedPreferences
        preferences = getSharedPreferences(pref, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString("id_pes","0")
        editor.apply()

        putExtra("id_ne",notif.id)
        putExtra("title",notif.judul)
        putExtra("pesan",notif.pesan)
        putExtra("label",notif.jenis_p)
        putExtra("tanggal",notif.readableDate)

    })

}
fun Activity.openpengumuman(iddokter: String) {
    startActivity(Intent(this, Notifikasi::class.java).apply {
        putExtra("id_dokter", iddokter)
    })
}

