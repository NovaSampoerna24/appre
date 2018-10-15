package id.go.patikab.rsud.remun.remunerasi.view.Notifikasi

import android.content.ContentValues
import android.util.Log
import id.go.patikab.rsud.remun.remunerasi.data.api.ApiClient
import id.go.patikab.rsud.remun.remunerasi.data.api.ApiInterface
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.*

import id.go.patikab.rsud.remun.remunerasi.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotifikasiPresenter(private val mView:NotifikasiView){

    suspend fun getPengumuman(id:String){
        var getResponse: ApiInterface
        getResponse  = ApiClient.getClient().create(ApiInterface::class.java)
        val call: Call<NotifikasiResponse>
        call = getResponse.get_pengumuman(id)
         call.enqueue(object :Callback<NotifikasiResponse>{
             override fun onFailure(call: Call<NotifikasiResponse>, t: Throwable) {
               Log.d("TAG",t.message)
             }

             override fun onResponse(call: Call<NotifikasiResponse>, response: Response<NotifikasiResponse>) {
                 if(response.isSuccessful){
                    val notif = response.body()?.data
                     if (notif != null) {
                         mView.showInformasi(notif)
                     }
                     }
                 }
             });
         }

    }