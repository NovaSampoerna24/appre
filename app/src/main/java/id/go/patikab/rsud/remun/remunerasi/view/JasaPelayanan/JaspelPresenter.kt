package id.go.patikab.rsud.remun.remunerasi.view.JasaPelayanan

import android.util.Log
import id.go.patikab.rsud.remun.remunerasi.data.api.ApiClient
import id.go.patikab.rsud.remun.remunerasi.data.api.ApiInterface
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.ListJaspel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JaspelPresenter(private val mView:JaspelView){
    suspend fun getlistjaspel(){
        mView.showloading()
        var getResponse: ApiInterface
        getResponse = ApiClient.getClient().create(ApiInterface::class.java)
        val call: Call<ListJaspel>
        call = getResponse.getlistJaspel()
        call.enqueue(object : Callback<ListJaspel> {
            override fun onFailure(call: Call<ListJaspel>, t: Throwable) {
                Log.d("Failure", t.message.toString() + " --")
                mView.placeholder()
            }
            override fun onResponse(call: Call<ListJaspel>, response: Response<ListJaspel>) {
                Log.d("response code",response.code().toString()+" -- ")
                if (response.isSuccessful) {
                    val notif = response.body()?.listJp
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