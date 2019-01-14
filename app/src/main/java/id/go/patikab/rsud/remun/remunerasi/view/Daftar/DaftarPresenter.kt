package id.go.patikab.rsud.remun.remunerasi.view.Daftar

import android.util.Log
import id.go.patikab.rsud.remun.remunerasi.data.api.ApiClient
import id.go.patikab.rsud.remun.remunerasi.data.api.ApiInterface
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.DaftarResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DaftarPresenter(private val mView: DaftarView){

    suspend fun goesDaftar(username:String,email:String,password:String,repassword:String,map:HashMap<String,String>){
        mView.loading()
        var getResponse: ApiInterface
        getResponse = ApiClient.getClient().create(ApiInterface::class.java)
        val call: Call<DaftarResponse>
        call = getResponse.goDaftar(username,email,password,repassword,map)
        call.enqueue(object : Callback<DaftarResponse> {
            override fun onFailure(call: Call<DaftarResponse>, t: Throwable) {
                Log.d("Failure daftar", t.message.toString() + " --")
                mView.placeholder()
            }
            override fun onResponse(call: Call<DaftarResponse>, response: Response<DaftarResponse>) {
                Log.d("response code",response.code().toString()+" -- ")
                if(response.code() == 200){
                if (response.isSuccessful) {
                    val status = response.body()!!.status.toString()
                    if(status == "gagal"){
                        mView.message(response.body()?.message.toString())
                        mView.hideload()
                    }else if(status== "sukses"){
                        val datae= response.body()?.dataUser
                        if (datae != null) {
                            mView.daftar(datae)
                            mView.hideload()
                        }else{
                            mView.message("Terjadi gangguan saat menjangkau server  !")
                            mView.placeholder()
                        }
                    }else{
                        mView.message("Terjadi gangguan saat menjangkau server !")
                        mView.placeholder()
                    }

                }else{
                    mView.message("Terjadi gangguan saat menjangkau server !")
                    mView.hideload()
                }
                }else{
                    mView.message("Terjadi gangguan saat menjangkau server !")
                    mView.placeholder()
                }
            }

        })
    }
}