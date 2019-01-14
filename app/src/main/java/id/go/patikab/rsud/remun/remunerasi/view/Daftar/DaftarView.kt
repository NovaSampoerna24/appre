package id.go.patikab.rsud.remun.remunerasi.view.Daftar

import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.DaftarResponse

interface DaftarView{
    fun daftar(data: List<DaftarResponse.Akun>)
    fun placeholder()
    fun hideload()
    fun loading()
    fun message(msg:String)
}