package id.go.patikab.rsud.remun.remunerasi.view.DetailProfil

import android.content.ContentValues
import android.util.Log
import android.view.Menu
import android.widget.ArrayAdapter
import id.go.patikab.rsud.remun.remunerasi.R
import id.go.patikab.rsud.remun.remunerasi.data.api.ApiClient
import id.go.patikab.rsud.remun.remunerasi.data.api.ApiInterface
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.TindakanGetData
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.ProfilGetData
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.MenuModel
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.NotifikasiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class DetailPresenter(private val mView: DetailView){

    suspend fun fetchProfilRetro(kd_user: String) {

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
                }
            }
            override fun onFailure(call: Call<ProfilGetData>, t: Throwable) {
                Log.d(ContentValues.TAG, t.message)
            }

        })
    }
    fun getmenu(){
        val sdf = MutableList(0){
            MenuModel("","test","")
        }

        sdf.add(MenuModel("0","Heart",""))
        sdf.add(MenuModel("1","Atomic",""))
        sdf.add(MenuModel("2","Stethoscope",""))
        sdf.add(MenuModel("3","Hospital",""))
        sdf.add(MenuModel("4","Report",""))
        sdf.add(MenuModel("5","Book",""))

        val myImageList =  ArrayList<Int>()
        myImageList.add(R.drawable.heart)
        myImageList.add(R.drawable.atomic)
        myImageList.add(R.drawable.stethoscope)
        myImageList.add(R.drawable.hospital)
        myImageList.add(R.drawable.report)
        myImageList.add(R.drawable.book)

        mView.showMenu(sdf,myImageList)
    }
}
