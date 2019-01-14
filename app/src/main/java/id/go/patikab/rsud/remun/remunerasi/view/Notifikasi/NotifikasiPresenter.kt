package id.go.patikab.rsud.remun.remunerasi.view.Notifikasi

import android.util.Log
import id.go.patikab.rsud.remun.remunerasi.data.api.ApiInterface
import id.go.patikab.rsud.remun.remunerasi.data.api.ApiClient
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.NotifikasiResponse

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotifikasiPresenter(private val mView: NotifikasiView) {

    suspend fun getPengumuman(id: String) {
        mView.showloading()
        var getResponse: ApiInterface
        getResponse = ApiClient.getClient().create(ApiInterface::class.java)
        val call: Call<NotifikasiResponse>
        call = getResponse.get_pengumuman(id)
        call.enqueue(object : Callback<NotifikasiResponse> {
            override fun onFailure(call: Call<NotifikasiResponse>, t: Throwable) {
                Log.d("Failure", t.message.toString() + " --")
                mView.placeholder()
            }
            override fun onResponse(call: Call<NotifikasiResponse>, response: Response<NotifikasiResponse>) {
                Log.d("response code",response.code().toString()+" -- ")
                if (response.isSuccessful) {
                    val notif = response.body()?.data
                    if (notif != null) {
                        mView.showInformasi(notif)
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
