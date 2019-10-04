package id.go.patikab.rsud.remun.remunerasi.view.Allranapdokter

import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.Allrajal
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.Allranap

interface AllranapView {
    fun showData(data: List<Allranap.Dataranap>, jumlah: String)
    fun hideloading()
    fun showloading()
    fun placeholder()
}