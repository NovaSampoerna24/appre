package id.go.patikab.rsud.remun.remunerasi.view.JasaPelayanan

import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.ListJaspel

interface JaspelView{
    fun showInformasi(listjp:List<ListJaspel.listJaspel>)
    fun hideloading()
    fun showloading()
    fun placeholder()
}