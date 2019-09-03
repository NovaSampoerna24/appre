package id.go.patikab.rsud.remun.remunerasi.view.pasien_ranap

import android.util.Log
import id.go.patikab.rsud.remun.remunerasi.R
import id.go.patikab.rsud.remun.remunerasi.data.api.ApiClient
import id.go.patikab.rsud.remun.remunerasi.data.api.ApiInterface
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.ListDokter
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.ListPasien
import id.go.patikab.rsud.remun.remunerasi.view.pasien_rajal.PranapView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class PranapPresenter(private val mView: PranapView) {

    suspend fun getPranap(id: String) {
        mView.showloading()
        var getResponse: ApiInterface
        getResponse = ApiClient.getClient().create(ApiInterface::class.java)
        val call: Call<ListPasien>
        call = getResponse.getListPasienRanap(id)
//        Log.d("test id",id)
        call.enqueue(object : Callback<ListPasien> {
            override fun onFailure(call: Call<ListPasien>, t: Throwable) {
//                Log.d("Failure", t.message.toString() + " --")
                mView.placeholder()
            }
            override fun onResponse(call: Call<ListPasien>, response: Response<ListPasien>) {
//                Log.d("response code nt",response.code().toString()+" -- ")
                if (response.isSuccessful) {
                    val notif = response.body()?.data
                    if (notif != null) {
                        mView.showPranap(notif)
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
    suspend fun getsearchPranap(id: String,q:String) {
        mView.showloading()
        var getResponse: ApiInterface
        getResponse = ApiClient.getClient().create(ApiInterface::class.java)
        val call: Call<ListPasien>
            call = getResponse.getListSearchPasienRanap(id,q)
//        Log.d("test id",id)
        call.enqueue(object : Callback<ListPasien> {
            override fun onFailure(call: Call<ListPasien>, t: Throwable) {
//                Log.d("Failure", t.message.toString() + " --")
                mView.placeholder()
            }
            override fun onResponse(call: Call<ListPasien>, response: Response<ListPasien>) {
//                Log.d("response code nt",response.code().toString()+" -- ")
                if (response.isSuccessful) {
                    val notif = response.body()?.data
                    val jumlah = response.body()?.jumlah
                    if (notif != null) {
                        mView.showPranap(notif)
                        mView.hideloading()
                    }else{
                        mView.placeholder()
                    }
                    if(jumlah == 0){
                        mView.placeholder()
                    }
                }else{
                    mView.placeholder()
                }
            }
        });
    }
    suspend fun getDokter(){

        var getResponse: ApiInterface
        getResponse = ApiClient.getClient().create(ApiInterface::class.java)
        val call: Call<ListDokter>
        call = getResponse.getListDokter()
//        Log.d("test id",id)
        call.enqueue(object : Callback<ListDokter> {
            override fun onFailure(call: Call<ListDokter>, t: Throwable) {
                Log.d("Failure", t.message.toString() + " --")
                mView.placeholder()
            }
            override fun onResponse(call: Call<ListDokter>, response: Response<ListDokter>) {
                Log.d("response code nt2",response.code().toString()+" -- ")
                if (response.isSuccessful) {
                    val notif = response.body()?.data
                    if (notif != null ) {
                        mView.showDokter(notif)
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
