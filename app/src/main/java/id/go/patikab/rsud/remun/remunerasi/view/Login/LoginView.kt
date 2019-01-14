package id.go.patikab.rsud.remun.remunerasi.view.Login

import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.LoginResponse

interface LoginView{
    fun login(data: List<LoginResponse.Akun>)
    fun placeholder()
    fun hideload()
    fun loading()
    fun message(msg:String)
}