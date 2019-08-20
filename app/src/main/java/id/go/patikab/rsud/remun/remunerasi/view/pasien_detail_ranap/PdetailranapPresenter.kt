package id.go.patikab.rsud.remun.remunerasi.view.pasien_detail_ranap

import android.util.Log
import id.go.patikab.rsud.remun.remunerasi.R
import id.go.patikab.rsud.remun.remunerasi.data.api.ApiClient
import id.go.patikab.rsud.remun.remunerasi.data.api.ApiInterface
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.DetailPasienRanap
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.ListPasien
import id.go.patikab.rsud.remun.remunerasi.view.pasien_rajal.PranapView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class PdetailranapPresenter(private val mView: PdetailranapView) {

    suspend fun getPdetailRanap(idxdaftar: String,nomr:String) {
        mView.showloading()
        var getResponse: ApiInterface
        getResponse = ApiClient.getClient().create(ApiInterface::class.java)
        val call: Call<DetailPasienRanap>
        call = getResponse.getDetailPasienRanap(idxdaftar,nomr)
//        Log.d("test id",id)
        call.enqueue(object : Callback<DetailPasienRanap> {
            override fun onFailure(call: Call<DetailPasienRanap>, t: Throwable) {
                Log.d("Failure", t.message.toString() + " --")
                mView.placeholder()
            }
            override fun onResponse(call: Call<DetailPasienRanap>, response: Response<DetailPasienRanap>) {
                Log.d("response code nt",response.code().toString()+" -- ")

                if (response.isSuccessful) {
                    val notif = response.body()?.data
//                    Log.d("test 3",notif.toString())
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