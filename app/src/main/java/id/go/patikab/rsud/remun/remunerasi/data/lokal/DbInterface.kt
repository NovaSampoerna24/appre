package id.go.patikab.rsud.remun.remunerasi.data.lokal

import id.go.patikab.rsud.remun.remunerasi.data.lokal.`object`.Informasi

interface DbInterface{
    fun getInformasi(kduser:String):List<Informasi>
    fun addtoInformasi(informasi:Informasi)
}