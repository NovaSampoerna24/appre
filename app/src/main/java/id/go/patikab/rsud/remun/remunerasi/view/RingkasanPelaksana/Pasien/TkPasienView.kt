package id.go.patikab.rsud.remun.remunerasi.view.RingkasanPelaksana.Pasien

import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.RingkasanModel

interface TkPasienView{
    fun showLoading()
    fun hideLoading()
    fun showplaceholder()
    fun show(ringkasan:List<RingkasanModel.Ringkasan>)
}