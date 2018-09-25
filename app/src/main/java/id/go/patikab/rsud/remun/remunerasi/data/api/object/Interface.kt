package id.go.patikab.rsud.remun.remunerasi.data.api.`object`

import kotlinx.coroutines.experimental.Deferred

interface Interface{

    fun detailProfil(id:String):Deferred<ProfileGetData>
}