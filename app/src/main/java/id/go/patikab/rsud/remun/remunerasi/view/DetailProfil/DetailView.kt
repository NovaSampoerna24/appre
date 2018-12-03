package id.go.patikab.rsud.remun.remunerasi.view.DetailProfil

import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.*

interface  DetailView {
    fun showDetail(detail: ProfilDetail)
    fun showMenu(menu:List<MenuModel>,icon:ArrayList<Int>)

}