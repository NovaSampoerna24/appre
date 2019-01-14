package id.go.patikab.rsud.remun.remunerasi.view.RingkasanPelaksana.Tindakan

import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.RingkasanModel

interface TkTindakanView{
    fun showLoading()
    fun hideLoading()
    fun showplaceholder()
    fun show(ringkasan:List<RingkasanModel.Ringkasan>)
}