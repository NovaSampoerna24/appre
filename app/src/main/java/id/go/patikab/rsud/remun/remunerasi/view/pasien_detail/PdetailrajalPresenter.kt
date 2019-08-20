package id.go.patikab.rsud.remun.remunerasi.view.pasien_detail

import android.util.Log
import id.go.patikab.rsud.remun.remunerasi.R
import id.go.patikab.rsud.remun.remunerasi.data.api.ApiClient
import id.go.patikab.rsud.remun.remunerasi.data.api.ApiInterface
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.DetailPasienRajal
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.ListPasien
import id.go.patikab.rsud.remun.remunerasi.view.pasien_rajal.PranapView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class PdetailrajalPresenter(private val mView: PdetailrajalView) {

    suspend fun getPdetail(idxdaftar: String,nomr:String) {
        mView.showloading()
        var getResponse: ApiInterface
        getResponse = ApiClient.getClient().create(ApiInterface::class.java)
        val call: Call<DetailPasienRajal>
        call = getResponse.getDetailPasien(idxdaftar,nomr)
//        Log.d("test id",id)
        call.enqueue(object : Callback<DetailPasienRajal> {
            override fun onFailure(call: Call<DetailPasienRajal>, t: Throwable) {
                Log.d("Failure", t.message.toString() + " --")
                mView.placeholder()
            }
            override fun onResponse(call: Call<DetailPasienRajal>, response: Response<DetailPasienRajal>) {
                Log.d("response code nt",response.code().toString()+" -- ")
                if (response.isSuccessful) {
                    val notif = response.body()?.data
                    if (notif != null) {
                        mView.showDataPendaftaran(notif)
                        mView.hideloading()
                    }else{
                        mView.placeholder()
                    }
                }else{
                    mView.placeholder()
                }
            }
        });
    }
}
