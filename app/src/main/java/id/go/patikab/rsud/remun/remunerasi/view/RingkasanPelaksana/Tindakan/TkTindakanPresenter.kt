package id.go.patikab.rsud.remun.remunerasi.view.RingkasanPelaksana.Tindakan

import android.util.Log
import id.go.patikab.rsud.remun.remunerasi.data.api.ApiInterface
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.RingkasanModel
import id.go.patikab.rsud.remun.remunerasi.view.RingkasanPelaksana.TaksiranPenghasilan.TkPenghasilanView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import id.go.patikab.rsud.remun.remunerasi.data.api.ApiClient.*

class TkTindakanPresenter(private val mView: TkTindakanView) {

    suspend fun getTaksiranTindakan(id: String) {
        mView.showLoading()
        var getResponse: ApiInterface
        getResponse = getClient().create(ApiInterface::class.java)
        val call: Call<RingkasanModel>
        call = getResponse.getringkasanTindakan(id)
        call.enqueue(object : Callback<RingkasanModel> {
            override fun onFailure(call: Call<RingkasanModel>, t: Throwable) {
                mView.showplaceholder()
                Log.d("failure","failed")
            }

            override fun onResponse(call: Call<RingkasanModel>, response: Response<RingkasanModel>) {
                Log.d("response code :", response.code().toString()+" -- ")
                if (response.isSuccessful) {
                    val data = response.body()?.data
                    val status = response.body()?.status
                    if (status == "True") {
                        data?.let { mView.show(it) }
                        mView.hideLoading()
                    } else {
                        mView.showplaceholder()
                    }
                }else{
                    when (response.code()) {
                        404 -> Log.d("status 404","not found")
                        500 -> Log.d("status 505","server broken")
                        else -> Log.d("status error","unknown error")
                    }
                    mView.showplaceholder()
                }
            }

        })

    }
}