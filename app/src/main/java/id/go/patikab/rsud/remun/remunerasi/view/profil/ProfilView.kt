package id.go.patikab.rsud.remun.remunerasi.view.profil

import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.ProfilDetail
import id.go.patikab.rsud.remun.remunerasi.data.lokal.`object`.Informasi

interface ProfilView {
    fun showDetail(detail: ProfilDetail)
    fun showInformasi(informasi:List<Informasi>)
    fun showLoading()
    fun hideLoading()
    fun showplaceholder()
    fun show()
}