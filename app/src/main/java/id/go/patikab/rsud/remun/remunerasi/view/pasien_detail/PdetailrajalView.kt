package id.go.patikab.rsud.remun.remunerasi.view.pasien_detail

import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.DetailPasienRajal

interface  PdetailrajalView {

//    fun showInformasi(informasi:List<NotifikasiResponse.Notif>)
    fun showDataPendaftaran(data:DetailPasienRajal.dataPendaftaran)
    fun hideloading()
    fun showloading()
    fun placeholder()
}