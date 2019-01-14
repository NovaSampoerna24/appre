package id.go.patikab.rsud.remun.remunerasi.view.Pembayaran

import android.util.Log
import id.go.patikab.rsud.remun.remunerasi.data.api.ApiClient
import id.go.patikab.rsud.remun.remunerasi.data.api.ApiInterface
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.ListPasienDokter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.Toast



class DetailRMPresenter(private val mView: DetailRMView) {

    suspend fun getRM(kd_dokter: String, tgl_awal: String, tgl_akhir: String,map:HashMap<String,String>,signature:String) {
        mView.showLoading()
        var getResponse: ApiInterface
        getResponse = ApiClient.getClient().create(ApiInterface::class.java)
        val call: Call<ListPasienDokter>
        call = getResponse.getReMid(signature,kd_dokter, tgl_awal, tgl_akhir,map)
        call.enqueue(object : Callback<ListPasienDokter> {
            override fun onFailure(call: Call<ListPasienDokter>, t: Throwable) {
                Log.d("failure", "failed")
                mView.showPlaceholder()
            }
            override fun onResponse(call: Call<ListPasienDokter>, response: Response<ListPasienDokter>) {
                Log.d("response code", response.code().toString()+" -- ")
                if (response.isSuccessful) {
                    var  pendapatan = response.body()?.pendapatan


                    var pdt:String
                    if(pendapatan == null) pdt ="Rp. 0" else pdt = pendapatan.toString()
                    val data = response.body()?.remidList
                    val status = response.body()?.status
                    if (status != "failed") {
                        var pasien = response.body()?.j_pasien
                        var tindakan = response.body()?.j_tindakan
                        data?.let { mView.showRM(it,pdt, pasien.toString(),tindakan.toString()) }
                        mView.hideLoading()
                    }else{
                        mView.showPlaceholder()
                    }
//                    var signatures = response.body()?.signaturee
//                    Log.d("signature",signatures.toString()+" -- ")

                }else{
                    when (response.code()) {
                        404 -> Log.d("status 404","not found")
                        500 -> Log.d("status 505","server broken")
                        else -> Log.d("status error","unknown error")
                    }
                    mView.showPlaceholder()
                }

            }
        })
    }
}