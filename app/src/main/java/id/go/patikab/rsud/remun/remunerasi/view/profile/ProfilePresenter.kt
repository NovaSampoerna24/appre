package id.go.patikab.rsud.remun.remunerasi.view.profile

import id.go.patikab.rsud.remun.remunerasi.data.api.ApiClient
import id.go.patikab.rsud.remun.remunerasi.data.api.ApiInterface
import id.go.patikab.rsud.remun.remunerasi.data.api.`interface`.ApiInterfac
import id.go.patikab.rsud.remun.remunerasi.data.api.`object`.ProfilData
import id.go.patikab.rsud.remun.remunerasi.data.api.`object`.ProfilDetail
import id.go.patikab.rsud.remun.remunerasi.data.lokal.DatabaseHandler
import id.go.patikab.rsud.remun.remunerasi.data.lokal.DbInterface
import id.go.patikab.rsud.remun.remunerasi.data.lokal.`object`.Informasi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProfilePresenter(private val mView: ProfileView,
                       private val mApi: ApiInterfac,
                       private val mDb:DbInterface
                       ){

    suspend fun fetchProfil(kd_user:String){
        mView.showLoading()
        val detail = mApi.detailProfil(kd_user).await().dataProfils?.get(0)
        detail?.let{
            mView.showDetail(it)
        }
        mView.hideLoading()
//        if(detail != null && detail.isNotEmpty()){
////            mView.showDetail(detail)
//        }
    }
    suspend fun getInformasi(kd_user: String){
        val informasi = mDb.getInformasi(kd_user)
        if(informasi.isNotEmpty()){
            mView.showInformasi(informasi)
        }else{
            mView.showplaceholder()
        }

    }
}
