package id.go.patikab.rsud.remun.remunerasi.view.profil

import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.ProfilDetail
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.TindakanGetData
import id.go.patikab.rsud.remun.remunerasi.data.lokal.`object`.Informasi

interface  DetailView {
    fun showDetail(detail: ProfilDetail)
    fun showInformasi(informasi:List<Informasi>)
    fun showGaji(getgaji: TindakanGetData)

}