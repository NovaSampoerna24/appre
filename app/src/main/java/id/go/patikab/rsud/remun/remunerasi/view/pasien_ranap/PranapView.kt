package id.go.patikab.rsud.remun.remunerasi.view.pasien_rajal

import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.ListPasien

interface  PranapView {

//    fun showInformasi(informasi:List<NotifikasiResponse.Notif>)
    fun showPranap(data:List<ListPasien.Pasiene>)
    fun hideloading()
    fun showloading()
    fun placeholder()
}