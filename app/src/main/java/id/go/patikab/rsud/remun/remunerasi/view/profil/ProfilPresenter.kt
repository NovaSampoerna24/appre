package id.go.patikab.rsud.remun.remunerasi.view.profil

import android.content.ContentValues
import android.util.Log
import id.go.patikab.rsud.remun.remunerasi.data.api.ApiClient
import id.go.patikab.rsud.remun.remunerasi.data.api.ApiInterface
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.ProfilGetData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfilPresenter(private val mView: ProfilView){

    suspend fun showadapter(){
        mView.show()
    }
    suspend fun fetchProfilRetro(kd_user: String) {

        mView.showLoading()

        var getResponse: ApiInterface
        getResponse = ApiClient.getClient().create(ApiInterface::class.java)
        val call: Call<ProfilGetData>
        call = getResponse.getDataProfil(kd_user)
        call.enqueue(object : Callback<ProfilGetData> {

            override fun onResponse(call: Call<ProfilGetData>, response: Response<ProfilGetData>) {
                if (response.isSuccessful) {
                    val detail = response.body()?.dataProfils?.get(0)
                    detail?.let {
                        mView.showDetail(it)
                    }
                } else {
                    mView.showplaceholder()
                }
                mView.hideLoading()
            }

            override fun onFailure(call: Call<ProfilGetData>, t: Throwable) {
                Log.d(ContentValues.TAG, t.message)
                mView.hideLoading()
            }

        })
    }
}