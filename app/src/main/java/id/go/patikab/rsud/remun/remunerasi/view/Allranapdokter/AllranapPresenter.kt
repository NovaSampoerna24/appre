package id.go.patikab.rsud.remun.remunerasi.view.Allranapdokter

import android.util.Log
import id.go.patikab.rsud.remun.remunerasi.data.api.ApiClient
import id.go.patikab.rsud.remun.remunerasi.data.api.ApiInterface
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.Allranap
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AllranapPresenter(private val mView: AllranapView){

    suspend fun getdata(){
        mView.showloading()
        var getResponse: ApiInterface
        getResponse = ApiClient.getClient().create(ApiInterface::class.java)
        val call: Call<Allranap>
        call = getResponse.getallranap()
        call.enqueue(object : Callback<Allranap> {
            override fun onFailure(call: Call<Allranap>, t: Throwable) {
                Log.d("Failure", t.message.toString() + " --")
                mView.placeholder()
            }
            override fun onResponse(call: Call<Allranap>, response: Response<Allranap>) {
                Log.d("response code",response.code().toString()+" -- ")
                if (response.isSuccessful) {
                    val notif = response.body()?.dataRanap
                    val jumlah = response.body()?.jumlah.toString()
                    if (notif != null) {
                        mView.showData(notif, jumlah)
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