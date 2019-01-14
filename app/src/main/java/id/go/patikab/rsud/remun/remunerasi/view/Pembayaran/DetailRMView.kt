package id.go.patikab.rsud.remun.remunerasi.view.Pembayaran

import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.ListPasienDokter

interface DetailRMView{

    fun showRM(RMList:List<ListPasienDokter.Remid>,pendapatan:String,j_pasien:String,j_tindakan:String)
    fun showLoading()
    fun hideLoading()
    fun showPlaceholder()
}