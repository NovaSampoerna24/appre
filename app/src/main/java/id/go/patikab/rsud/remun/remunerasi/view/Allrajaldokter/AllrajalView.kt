package id.go.patikab.rsud.remun.remunerasi.view.Allrajaldokter

import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.Allrajal

interface AllrajalView{
    fun showData(informasi:List<Allrajal.Datarajal>,jumlah: String)
    fun hideloading()
    fun showloading()
    fun placeholder()
}