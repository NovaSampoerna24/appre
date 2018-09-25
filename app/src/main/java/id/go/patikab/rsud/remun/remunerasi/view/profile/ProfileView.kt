package id.go.patikab.rsud.remun.remunerasi.view.profile

import id.go.patikab.rsud.remun.remunerasi.data.api.`object`.ProfilDetail
import id.go.patikab.rsud.remun.remunerasi.data.api.`object`.ProfileGetData
import id.go.patikab.rsud.remun.remunerasi.data.lokal.`object`.Informasi


interface ProfileView{
    fun showDetail(detail:ProfileGetData.DetailP)
    fun showInformasi(informasi:List<Informasi>)
    fun showLoading()

    fun hideLoading()
    fun showplaceholder()
}