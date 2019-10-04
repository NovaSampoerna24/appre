package id.go.patikab.rsud.remun.remunerasi.view.Allrajaldokter

import android.util.Log
import id.go.patikab.rsud.remun.remunerasi.data.api.ApiClient
import id.go.patikab.rsud.remun.remunerasi.data.api.ApiInterface
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.Allrajal
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.ListPasien
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.NotifikasiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AllrajalPresenter(private val mView: AllrajalView){

    suspend fun getdata(tanggal:String){
        mView.showloading()
        var getResponse: ApiInterface
        getResponse = ApiClient.getClient().create(ApiInterface::class.java)
        val call: Call<Allrajal>
        call = getResponse.getallrajal(tanggal)
        call.enqueue(object : Callback<Allrajal> {
            override fun onFailure(call: Call<Allrajal>, t: Throwable) {
                Log.d("Failure", t.message.toString() + " --")
                mView.placeholder()
            }
            override fun onResponse(call: Call<Allrajal>, response: Response<Allrajal>) {
                Log.d("response code",response.code().toString()+" -- ")
                if (response.isSuccessful) {
                    val notif = response.body()?.dataRajal
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
//    suspend fun getPrajal(id: String,tanggal:String) {
//        mView.showloading()
//        var getResponse: ApiInterface
//        getResponse = ApiClient.getClient().create(ApiInterface::class.java)
//        val call: Call<ListPasien>
//        call = getResponse.getListPasienRajal(id,tanggal)
////        Log.d("test id",id)
//        call.enqueue(object : Callback<ListPasien> {
//            override fun onFailure(call: Call<ListPasien>, t: Throwable) {
//                Log.d("Failure", t.message.toString() + " --")
//                mView.placeholder()
//            }
//            override fun onResponse(call: Call<ListPasien>, response: Response<ListPasien>) {
//                Log.d("response code nt",response.code().toString()+" -- ")
//                if (response.isSuccessful) {
//                    val notif = response.body()?.data
//                    val jumlah = response.body()?.jumlah
//                    if (notif != null) {
//                        mView.showData(notif)
//                        mView.hideloading()
//                    }else{
//                        mView.placeholder()
//                    }
//                    if(jumlah == 0){
//                        mView.placeholder()
//                    }
//                }else{
//                    mView.placeholder()
//                }
//            }
//        });
//    }
//    suspend fun getSearchPrajal(id: String,tanggal: String,q:String) {
//        mView.showloading()
//        var getResponse: ApiInterface
//        getResponse = ApiClient.getClient().create(ApiInterface::class.java)
//        val call: Call<ListPasien>
//        call = getResponse.getListSearchPasienRajal(id,tanggal,q)
////        Log.d("test id",id)
//        call.enqueue(object : Callback<ListPasien> {
//            override fun onFailure(call: Call<ListPasien>, t: Throwable) {
//                Log.d("Failure", t.message.toString() + " --")
//                mView.placeholder()
//            }
//            override fun onResponse(call: Call<ListPasien>, response: Response<ListPasien>) {
//                Log.d("response code nt",response.code().toString()+" -- ")
//                if (response.isSuccessful) {
//                    val notif = response.body()?.data
//                    val jumlah = response.body()?.jumlah
//                    if (notif != null ) {
//                        mView.showData(notif)
//                        mView.hideloading()
//                    }else{
//                        mView.placeholder()
//                    }
//                    if(jumlah == 0){
//                        mView.placeholder()
//                    }
//                }else{
//                    mView.placeholder()
//                }
//            }
//        });
//    }
}