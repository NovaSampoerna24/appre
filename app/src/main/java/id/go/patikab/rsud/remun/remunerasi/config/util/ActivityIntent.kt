package id.go.patikab.rsud.remun.remunerasi.config.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle

import id.go.patikab.rsud.remun.remunerasi.data.lokal.sharepreference.SharePref.*

import id.go.patikab.rsud.remun.remunerasi.view.gantiPassword.*
import id.go.patikab.rsud.remun.remunerasi.view.ubahfoto.*
import id.go.patikab.rsud.remun.remunerasi.view.Pembayaran.DetailRM
import id.go.patikab.rsud.remun.remunerasi.view.notifikasidetail.*
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.*
import id.go.patikab.rsud.remun.remunerasi.view.Allrajaldokter.AllrajalActivity
import id.go.patikab.rsud.remun.remunerasi.view.Allrajaldokter.ListPasienDokterjalActivity
import id.go.patikab.rsud.remun.remunerasi.view.Allranapdokter.AllranapActivity
import id.go.patikab.rsud.remun.remunerasi.view.Allranapdokter.ListPasienDokternapActivity
import id.go.patikab.rsud.remun.remunerasi.view.MainApps
import id.go.patikab.rsud.remun.remunerasi.view.WebView.ViewJaspel
import id.go.patikab.rsud.remun.remunerasi.view.WebView.ViewMenu

import id.go.patikab.rsud.remun.remunerasi.view.Daftar.Daftar
import id.go.patikab.rsud.remun.remunerasi.view.Login.Login
import id.go.patikab.rsud.remun.remunerasi.view.aktivasi_akun.Aktifasi
import id.go.patikab.rsud.remun.remunerasi.view.PasienKu.PasienKu
import id.go.patikab.rsud.remun.remunerasi.view.profil.ProfilFragment
import id.go.patikab.rsud.remun.remunerasi.view.Ringkasan.Ringkasan
import id.go.patikab.rsud.remun.remunerasi.view.about_aplikasi.AboutActivity
import id.go.patikab.rsud.remun.remunerasi.view.akun.AkuneFramgent
import id.go.patikab.rsud.remun.remunerasi.view.pasien_detail.Pdetailrajal
import id.go.patikab.rsud.remun.remunerasi.view.pasien_detail_ranap.Pdetailranap
import id.go.patikab.rsud.remun.remunerasi.view.pasien_rajal.Prajal
import id.go.patikab.rsud.remun.remunerasi.view.pasien_ranap.Pranap

fun Activity.openUbahFoto(iddokter: String, namaDokter: String) {
    startActivity(Intent(this, UbahFoto::class.java).apply {
        putExtra("id_dokter", iddokter)
        putExtra("nama_dokter", namaDokter)
    })
}
fun Activity.openRemid(start:String,end:String){
    startActivity(Intent(this,DetailRM::class.java).apply {
        putExtra("start",start)
        putExtra("end",end)
    })
}
fun Activity.openWebViewlistJaspel(start:String,end:String,sign:String){
    startActivity(Intent(this,ViewJaspel::class.java).apply {
        putExtra("dari",start)
        putExtra("sampai",end)
        putExtra("sign",sign)
    })
}

fun Activity.logout() {
    var sharedPreferences: SharedPreferences
    sharedPreferences = getSharedPreferences(pref, Context.MODE_PRIVATE)

    var editor = sharedPreferences.edit()
    editor.putString(login_session, "")
    editor.putString(signature, "")
    editor.putString(status_akun, "")
    editor.putString(username_device, "")
    editor.putString(nm_dokter, "")
    editor.putString(email_device, "")
    editor.commit()
    startActivity(Intent(this, Login::class.java))
    this.finish()
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
fun Activity.openPdetail(data:ListPasien.Pasiene){
//    startActivity(Intent(this, NotifikasiDetail::class.java).apply {
//
    val i = Intent(this, Pdetailrajal::class.java)
    i.putExtra("idxdaftar", data.IDXDAFTAR)
    i.putExtra("nomr",data.NOMR)
    startActivity(i)


}
fun Activity.openPdetailRanap(idx:String,nomr:String){
//    startActivity(Intent(this, NotifikasiDetail::class.java).apply {
//
    val i = Intent(this, Pdetailranap::class.java)
    i.putExtra("idxdaftar", idx)
    i.putExtra("nomr",nomr)
    startActivity(i)


}


fun Activity.openDaftar(){
    startActivity(Intent(this,Daftar::class.java).apply {

    })
}
fun Activity.openLogin(){
    startActivity(Intent(this,Login::class.java).apply {

    })
}
fun Activity.openAktivasi(){
    startActivity(Intent(this,Aktifasi::class.java).apply {

    })
}
fun Activity.OpenProfile(){
    startActivity(Intent(this,AkuneFramgent::class.java).apply {

    })
}
fun Activity.OpenSearch(){
    startActivity(Intent(this,AkuneFramgent::class.java).apply {

    })
}
fun Activity.openwebMenu(url:String){
    startActivity(Intent(this,ViewMenu::class.java).apply {
        putExtra("url_menu",url)
    })
}
//fun Activity.openjasadokter(){
//    startActivity(Intent(this,PasienKu::class.java).apply {
//
//    })
//}
fun Activity.openPasienku(){
    startActivity(Intent(this,PasienKu::class.java).apply {

    })
}
fun Activity.openakun(){
    startActivity(Intent(this,ProfilFragment::class.java).apply {

    })
}
fun Activity.openringkasanjp(){
    startActivity(Intent(this,Ringkasan::class.java).apply {

    })
}

fun Activity.openabout(){
    startActivity(Intent(this,AboutActivity::class.java).apply {
    })
}
fun Activity.openpasjal(){
    startActivity(Intent(this,Prajal::class.java).apply {
    })
}
fun Activity.openpasnap(){
    startActivity(Intent(this,Pranap::class.java).apply {
    })
}
fun Activity.openallrajal(){
    startActivity(Intent(this,AllrajalActivity::class.java).apply {
    })
}

fun Activity.openallranap(){
    startActivity(Intent(this,AllranapActivity::class.java).apply {
    })
}

fun Activity.openpasiendokterrajal(kddokter:String,nmdokter:String,tanggal:String){
//    startActivity(Intent(this, NotifikasiDetail::class.java).apply {
    val i = Intent(this, ListPasienDokterjalActivity::class.java)
    i.putExtra("kddokter", kddokter)
    i.putExtra("nmdokter",nmdokter)
    i.putExtra("tanggal",tanggal)
    startActivity(i)
}

fun Activity.openpasiendokterranap(kddokter:String,nmdokter:String){
//    startActivity(Intent(this, NotifikasiDetail::class.java).apply {
    val i = Intent(this, ListPasienDokternapActivity::class.java)
    i.putExtra("kddokter", kddokter)
    i.putExtra("nmdokter",nmdokter)
    startActivity(i)
}

