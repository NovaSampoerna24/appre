package id.go.patikab.rsud.remun.remunerasi.view.Login
import android.util.Log
import id.go.patikab.rsud.remun.remunerasi.data.api.ApiClient
import id.go.patikab.rsud.remun.remunerasi.data.api.ApiInterface
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginPresenter(private val mView:LoginView){

    suspend fun getLogin(username:String,password:String,token:String,map:HashMap<String,String>){
        mView.loading()
        var getResponse: ApiInterface
        getResponse = ApiClient.getClient().create(ApiInterface::class.java)
        val call: Call<LoginResponse>
        call = getResponse.goLogin(username,password,map)
        call.enqueue(object : Callback<LoginResponse> {
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.d("Failure", t.message.toString() + " --")
                mView.placeholder()
            }
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                Log.d("response code",response.code().toString()+" -- ")
                if (response.isSuccessful) {
                    val status = response.body()!!.status.toString()
                    if(status == "sukses"){
                        val datae= response.body()?.dataUser
                        if (datae != null) {
                            mView.login(datae)
                            mView.hideload()
                        }
                    }else{
                        mView.message(response.body()?.message.toString())
                        mView.hideload()
                    }

                }else{
                    mView.message("Terjadi gangguan saat menjangkau server !")
                    mView.hideload()
                }
            }
        })
    }
}