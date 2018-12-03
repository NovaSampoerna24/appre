package id.go.patikab.rsud.remun.remunerasi.view.Notifikasi
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.*

interface NotifikasiView{
    fun showInformasi(informasi:List<NotifikasiResponse.Notif>)
    fun hideloading()
    fun showloading()
    fun placeholder()
}