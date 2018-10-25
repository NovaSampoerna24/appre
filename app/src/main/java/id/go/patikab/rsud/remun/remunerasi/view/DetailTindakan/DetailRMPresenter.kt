package id.go.patikab.rsud.remun.remunerasi.view.DetailTindakan

import android.util.Log
import id.go.patikab.rsud.remun.remunerasi.view.Notifikasi.NotifikasiView
import id.go.patikab.rsud.remun.remunerasi.data.api.*
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailRMPresenter(private val mView: DetailRMView) {
    suspend fun getRM(kd_dokter: String, tgl_awal: String, tgl_akhir: String) {
        var getResponse: ApiInterface
        getResponse = ApiClient.getClient().create(ApiInterface::class.java)
        val call: Call<ListPasienDokter>
        call = getResponse.getReMid(kd_dokter, tgl_awal, tgl_akhir)
        call.enqueue(object : Callback<ListPasienDokter> {
            override fun onFailure(call: Call<ListPasienDokter>, t: Throwable) {
                Log.d("TAG failure", t.message)
            }
            override fun onResponse(call: Call<ListPasienDokter>, response: Response<ListPasienDokter>) {
                if (response.isSuccessful) {
                    val data = response.body()?.remidList
                    if (data != null) {
                        mView.showRM(data)
                    }
                }
            }

        })
    }
}