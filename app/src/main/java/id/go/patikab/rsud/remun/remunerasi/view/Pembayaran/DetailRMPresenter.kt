package id.go.patikab.rsud.remun.remunerasi.view.Pembayaran

import android.util.Log
import id.go.patikab.rsud.remun.remunerasi.data.api.ApiClient
import id.go.patikab.rsud.remun.remunerasi.data.api.ApiInterface
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.ListPasienDokter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailRMPresenter(private val mView: DetailRMView) {
    suspend fun getRM(kd_dokter: String, tgl_awal: String, tgl_akhir: String) {
        mView.showLoading()
        var getResponse: ApiInterface
        getResponse = ApiClient.getClient().create(ApiInterface::class.java)
        val call: Call<ListPasienDokter>
        call = getResponse.getReMid(kd_dokter, tgl_awal, tgl_akhir)
        call.enqueue(object : Callback<ListPasienDokter> {
            override fun onFailure(call: Call<ListPasienDokter>, t: Throwable) {
                Log.d("TAG failure", t.message)
                mView.showPlaceholder()
            }
            override fun onResponse(call: Call<ListPasienDokter>, response: Response<ListPasienDokter>) {
                if (response.isSuccessful) {
                    var  pendapatan = response.body()?.pendapatan
                    var pdt:String
                    if(pendapatan == null) pdt ="Rp. 0" else pdt = pendapatan.toString()
                    val data = response.body()?.remidList
                    val status = response.body()?.status
                    Log.d("status",status)
                    if (status != "failed") {
                        data?.let { mView.showRM(it,pdt) }
                        mView.hideLoading()
                        Log.d("hoheh","yes")
                    }else{
                        mView.showPlaceholder()
                        Log.d("hoheh","no")
                    }
                }else{
                    mView.showPlaceholder()
                }

            }
        })
    }
}