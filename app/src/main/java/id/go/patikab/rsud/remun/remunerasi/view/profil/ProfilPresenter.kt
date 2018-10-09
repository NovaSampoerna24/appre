package id.go.patikab.rsud.remun.remunerasi.view.profil

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import id.go.patikab.rsud.remun.remunerasi.data.api.ApiClient
import id.go.patikab.rsud.remun.remunerasi.data.api.ApiInterface
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.ProfilGetData
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.TindakanGetData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class ProfilPresenter(private val mView: ProfilView){

    suspend fun showadapter(){
        mView.show()
    }

}