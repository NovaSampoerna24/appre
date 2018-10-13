package id.go.patikab.rsud.remun.remunerasi.config.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import id.go.patikab.rsud.remun.remunerasi.data.lokal.sharepreference.SharePref.*
import id.go.patikab.rsud.remun.remunerasi.view.Auth.AuthActivity
import id.go.patikab.rsud.remun.remunerasi.view.gantiPassword.GantiPassword
import id.go.patikab.rsud.remun.remunerasi.view.ubahfoto.UbahFoto
import id.go.patikab.rsud.remun.remunerasi.view.Notifikasi.*

fun Activity.openUbahFoto(iddokter: String, namaDokter: String) {
    startActivity(Intent(this, UbahFoto::class.java).apply {
        putExtra("id_dokter", iddokter)
        putExtra("nama_dokter", namaDokter)
    })
}

fun Activity.logout() {
    var sharedPreferences: SharedPreferences
    sharedPreferences = getSharedPreferences(pref, Context.MODE_PRIVATE)
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
fun openEventsDetail(id:String){

}
fun Activity.openpengumuman(iddokter: String) {
    startActivity(Intent(this, Notifikasi::class.java).apply {
        putExtra("id_dokter", iddokter)
    })
}

