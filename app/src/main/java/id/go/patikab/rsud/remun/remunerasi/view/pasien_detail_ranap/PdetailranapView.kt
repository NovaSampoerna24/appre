package id.go.patikab.rsud.remun.remunerasi.view.pasien_detail_ranap

import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.DetailPasienRanap

interface  PdetailranapView {

//    fun showInformasi(informasi:List<NotifikasiResponse.Notif>)
    fun showDataPendaftaran(data:DetailPasienRanap.dataPendaftaran)
    fun hideloading()
    fun showloading()
    fun placeholder()
}