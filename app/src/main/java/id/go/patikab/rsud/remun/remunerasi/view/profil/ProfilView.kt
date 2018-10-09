package id.go.patikab.rsud.remun.remunerasi.view.profil

import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.ProfilDetail
import id.go.patikab.rsud.remun.remunerasi.data.lokal.`object`.Informasi
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.TindakanGetData
interface ProfilView {
    fun showLoading()
    fun hideLoading()
    fun showplaceholder()
    fun show()
}