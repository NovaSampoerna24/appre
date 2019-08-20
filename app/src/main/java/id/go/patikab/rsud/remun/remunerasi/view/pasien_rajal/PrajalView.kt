package id.go.patikab.rsud.remun.remunerasi.view.pasien_rajal

import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.ListPasien

interface  PrajalView {

//    fun showInformasi(informasi:List<NotifikasiResponse.Notif>)
    fun showPrajal(data:List<ListPasien.Pasiene>)
    fun hideloading()
    fun showloading()
    fun placeholder()
}