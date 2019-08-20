package id.go.patikab.rsud.remun.remunerasi.view.PasienKu

import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.PasienKuResponse

interface PasienKuView{
    fun showInformasi(listjp:List<PasienKuResponse.Pasienku>)
    fun hideloading()
    fun showloading()
    fun placeholder()
}