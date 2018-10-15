package id.go.patikab.rsud.remun.remunerasi.view.DetailProfil

import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.*

interface  DetailView {
    fun showDetail(detail: ProfilDetail)
    fun showInformasi(informasi:List<NotifikasiResponse.Notif>)
    fun showGaji(getgaji: TindakanGetData)

}