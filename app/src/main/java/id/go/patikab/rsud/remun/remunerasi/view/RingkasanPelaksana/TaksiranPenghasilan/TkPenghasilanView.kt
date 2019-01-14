package id.go.patikab.rsud.remun.remunerasi.view.RingkasanPelaksana.TaksiranPenghasilan
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.RingkasanModel
interface TkPenghasilanView{
    fun showLoading()
    fun hideLoading()
    fun showplaceholder()
    fun show(ringkasan:List<RingkasanModel.Ringkasan>)
}