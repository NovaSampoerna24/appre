package id.go.patikab.rsud.remun.remunerasi.view.DetailProfil

import android.content.ContentValues
import android.util.Log
import id.go.patikab.rsud.remun.remunerasi.data.api.*
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class DetailPresenter(private val mView: DetailView){

    suspend fun fetchGaji(kd_user: String){
        val cb = Calendar.getInstance()
        val wulan = cb.get(Calendar.MONTH) + 1
        val taun = cb.get(Calendar.YEAR)
        val dino_d_b = cb.getActualMaximum(Calendar.DAY_OF_MONTH)

        val a = taun.toString() + "-" + wulan + "-" + 1
        val en = taun.toString() + "-" + wulan + "-" + dino_d_b

        var getResponse: ApiInterface
        getResponse  = ApiClient.getClient().create(ApiInterface::class.java)
        val call: Call<TindakanGetData>
        call = getResponse.getDataTindakan(kd_user,a,en)
        call.enqueue(object : Callback<TindakanGetData> {
            override fun onFailure(call: Call<TindakanGetData>, t: Throwable) {
                Log.d(ContentValues.TAG,t.message)
            }

            override fun onResponse(call: Call<TindakanGetData>, response: Response<TindakanGetData>) {
                if(response.isSuccessful){
                    val gaji = response.body()
                    gaji?.let {
                        mView.showGaji(gaji)
                    }
                }
            }
        })
    }
    suspend fun fetchProfilRetro(kd_user: String) {

        var getResponse: ApiInterface
        getResponse = ApiClient.getClient().create(ApiInterface::class.java)
        val call: Call<ProfilGetData>
        call = getResponse.getDataProfil(kd_user)
        call.enqueue(object : Callback<ProfilGetData> {
            override fun onResponse(call: Call<ProfilGetData>, response: Response<ProfilGetData>) {
                if (response.isSuccessful) {
                    val detail = response.body()?.dataProfils?.get(0)
                    detail?.let {
                        mView.showDetail(it)
                    }
                }
            }
            override fun onFailure(call: Call<ProfilGetData>, t: Throwable) {
                Log.d(ContentValues.TAG, t.message)
            }

        })
    }
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