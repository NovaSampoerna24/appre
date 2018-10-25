package id.go.patikab.rsud.remun.remunerasi.view.DetailTindakan

import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.*

interface DetailRMView{
    fun showRM(RMList:List<ListPasienDokter.Remid>)
}