package id.go.patikab.rsud.remun.remunerasi.view.DetailProfil

import android.util.Log
import id.go.patikab.rsud.remun.remunerasi.R
import id.go.patikab.rsud.remun.remunerasi.data.api.ApiClient
import id.go.patikab.rsud.remun.remunerasi.data.api.ApiInterface
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.ResponseMenu
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.ProfilGetData
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.MenuModel
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.NotifikasiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class DetailPresenter(private val mView: DetailView) {

    suspend fun fetchProfilRetro(kd_user: String) {

        var getResponse: ApiInterface
        getResponse = ApiClient.getClient().create(ApiInterface::class.java)
        val call: Call<ProfilGetData>
        call = getResponse.getDataProfil(kd_user)
        call.enqueue(object : Callback<ProfilGetData> {
            override fun onResponse(call: Call<ProfilGetData>, response: Response<ProfilGetData>) {
                Log.d("response code", response.code().toString() + " -- ")
                if (response.isSuccessful) {
                    val detail = response.body()?.dataProfils?.get(0)
                    detail?.let {
                        mView.showDetail(it)
                    }
                }
            }

            override fun onFailure(call: Call<ProfilGetData>, t: Throwable) {
                Log.d("Failure", t.message.toString() + " --")
            }

        })
    }

    fun getmenu(id_dokter: String) {
        var getResponse: ApiInterface
        getResponse = ApiClient.getClient().create(ApiInterface::class.java)
        val call: Call<ResponseMenu>
        call = getResponse.getprofilMenu(id_dokter)
        call.enqueue(object : Callback<ResponseMenu> {
            override fun onResponse(call: Call<ResponseMenu>, response: Response<ResponseMenu>) {
                Log.d("response code", response.code().toString() + " -- ")
                if (response.isSuccessful) {
                    val detail = response.body()?.menuList
                    detail?.let {
                        mView.showMenu(it)
                    }
                }
            }

            override fun onFailure(call: Call<ResponseMenu>, t: Throwable) {
                Log.d("Failure", t.message.toString() + " --")
            }

        })
    }
    fun getmenu1(){
//        val sdf = MutableList(0){
//            MenuModel("","test","")
//        }
//
//        sdf.add(MenuModel("0","Heart",""))
//        sdf.add(MenuModel("1","Atomic",""))
//        sdf.add(MenuModel("2","Stethoscope",""))
//        sdf.add(MenuModel("3","Hospital",""))
//        sdf.add(MenuModel("4","Report",""))
//        sdf.add(MenuModel("5","Book",""))
//
//        val myImageList =  ArrayList<Int>()
//        myImageList.add(R.drawable.heart)
//        myImageList.add(R.drawable.atomic)
//        myImageList.add(R.drawable.stethoscope)
//        myImageList.add(R.drawable.hospital)
//        myImageList.add(R.drawable.report)
//        myImageList.add(R.drawable.book)
//
//        mView.showMenu1(sdf,myImageList)
    }
    suspend fun getPengumuman(id: String) {
        mView.showloading()
        var getResponse: ApiInterface
        getResponse = ApiClient.getClient().create(ApiInterface::class.java)
        val call: Call<NotifikasiResponse>
        call = getResponse.get_pengumuman(id)
//        Log.d("test id",id)
        call.enqueue(object : Callback<NotifikasiResponse> {
            override fun onFailure(call: Call<NotifikasiResponse>, t: Throwable) {
                Log.d("Failure", t.message.toString() + " --")
                mView.placeholder()
            }
            override fun onResponse(call: Call<NotifikasiResponse>, response: Response<NotifikasiResponse>) {
                Log.d("response code nt",response.code().toString()+" -- ")
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
