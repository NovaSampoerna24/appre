package id.go.patikab.rsud.remun.remunerasi.view.PasienKu

import android.util.Log
import id.go.patikab.rsud.remun.remunerasi.data.api.ApiClient
import id.go.patikab.rsud.remun.remunerasi.data.api.ApiInterface
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.PasienKuResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PasienKuPresenter(private val mView:PasienKuView){
    suspend fun getPasienku(kd_user:String){
        mView.showloading()
        var getResponse: ApiInterface
        getResponse = ApiClient.getClient().create(ApiInterface::class.java)
        val call: Call<PasienKuResponse>
        call = getResponse.getPasienku(kd_user)
        call.enqueue(object : Callback<PasienKuResponse> {
            override fun onFailure(call: Call<PasienKuResponse>, t: Throwable) {
                Log.d("Failure", t.message.toString() + " --")
                mView.placeholder()
            }
            override fun onResponse(call: Call<PasienKuResponse>, response: Response<PasienKuResponse>) {
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