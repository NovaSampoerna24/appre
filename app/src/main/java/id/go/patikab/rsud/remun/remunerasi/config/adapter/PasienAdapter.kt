package id.go.patikab.rsud.remun.remunerasi.config.adapter

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import id.go.patikab.rsud.remun.remunerasi.R
import id.go.patikab.rsud.remun.remunerasi.data.api.ApiClient
import id.go.patikab.rsud.remun.remunerasi.data.api.ApiInterface
import kotlinx.android.synthetic.main.item_pasien.view.*
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.ListPasien
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.MessageRes
import id.go.patikab.rsud.remun.remunerasi.data.lokal.sharepreference.SharePref
import kotlinx.android.synthetic.main.detail_pasien.view.*
import kotlinx.android.synthetic.main.detail_pasien_ranap.*
import kotlinx.android.synthetic.main.form_laporkan.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PasienAdapter(private val mItems: List<ListPasien.Pasiene>,
                       private val counte: Int,
                    private val mOnclick: (notif: ListPasien.Pasiene) -> Unit
                    ) : RecyclerView.Adapter<IFXiewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IFXiewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return IFXiewHolder(inflater.inflate(R.layout.item_pasien, parent, false))
    }

    override fun getItemCount(): Int {
        return counte
    }

    override fun onBindViewHolder(holder: IFXiewHolder, position: Int) {
        holder.bind(mItems[position], mOnclick)
    }

}

class IFXiewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(inf: ListPasien.Pasiene, onClick: (inf: ListPasien.Pasiene) -> Unit) {
        with(itemView) {
            nama_pasien.text = inf.NAMA.trim()
            unit.text = inf.UNIT
            tgllahir.text = inf.tanggalLahir
            txt_norm.text = inf.NOMR
            txt_penjamin.text = inf.carabayar
            if(inf.visit == ""||inf.visit == null){
                txtwaktuvisit.text = "-";
            }else{
                txtwaktuvisit.text = inf.visit
                btnvisit.isEnabled = false
                var img = resources.getDrawable(R.drawable.ic_done)
                img.setBounds(0, 0, 60, 60)
                btnvisit.setCompoundDrawables(null, null, img, null)
            }
            if(inf.keterangan ==""|| inf.keterangan == null){
                txtketerangan.text = "-"
            }else{
                txtketerangan.text = inf.keterangan
                btnlaporkan.isEnabled = false
                var img = resources.getDrawable(R.drawable.ic_done)
                img.setBounds(0, 0, 60, 60)
                btnlaporkan.setCompoundDrawables(null, null, img, null)
            }

            if (inf.type == "rajal") {
                prakiraan_waktu_pelayanan.text = inf.waktu
            }
            btnvisit.onClick {

                postvisit(context, inf.IDXDAFTAR)
            }


            btnlaporkan.onClick {
                dialoglaporkan(context, inf.IDXDAFTAR)
            }
//            btndetailpas.onClick {
//                onClick(inf)
//            }
        }
    }
    fun dialoglaporkan(ctx:Context,idxdaftar: String){
        val mDialogView = LayoutInflater.from(ctx).inflate(R.layout.form_laporkan, null)
        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(ctx)
                .setView(mDialogView)
                .setTitle("Laporkan Pasien")
        //show dialog
        val  mAlertDialog = mBuilder.show()
        //login button click of custom layout
        mDialogView.dialogLoginBtn.setOnClickListener {
            //dismiss dialog
            mAlertDialog.dismiss()
            //get text from EditTexts of custom layout
            val name = mDialogView.dialogNameEt.text.toString()
            //set the input text in TextView
//            btnlaporkan.setText("Name:"+ name)
            postlaporkan(ctx,idxdaftar,name)

        }
        //cancel button click of custom layout
        mDialogView.dialogCancelBtn.setOnClickListener {
            //dismiss dialog
            mAlertDialog.dismiss()
        }

    }
    fun postlaporkan(ctx:Context,idxdaftar: String,keterangan:String){
        var prefs: SharedPreferences? = null
        prefs = ctx.getSharedPreferences(SharePref.pref, 0)
//        Log.d("tokene", sharedPreferences.getString(my_token, null)!! + " ")
        var user = prefs?.getString(SharePref.login_session, null).toString()
        var level = prefs?.getString(SharePref.level_user,"").toString()
        if(level =="2"){
//            direktur
            itemView.btnlaporkan.isEnabled = false
        }else {
            var getResponse: ApiInterface
            getResponse = ApiClient.getClient().create(ApiInterface::class.java)
            val call: Call<MessageRes>
            call = getResponse.Postlaporkan(user, idxdaftar, keterangan)
//        Log.d("test id",id)
            call.enqueue(object : Callback<MessageRes> {
                override fun onFailure(call: Call<MessageRes>, t: Throwable) {
                    Log.d("Failure", t.message.toString() + " --")
                }

                override fun onResponse(call: Call<MessageRes>, response: Response<MessageRes>) {
                    Log.d("response code nt", response.code().toString() + " -- ")
//                Log.d("response",response.body().toString())
                    if (response.isSuccessful) {
                        val notif = response.body()?.message.toString()
                        Log.d("test 3", notif.toString())

                        if (notif == "sukses") {
//                        mView.showDataPendaftaran(notif)
                            Log.d("message", notif + " --")
                            itemView.txtketerangan.text = keterangan
                            var img = itemView.resources.getDrawable( R.drawable.ic_done)
                            img.setBounds( 0, 0, 60, 60 )
                            itemView.btnlaporkan.setCompoundDrawables(null,null,img,null)
                            itemView.btnlaporkan.isEnabled = false
//                        mView.hideloading()
                        } else {
//                        mView.placeholder()
                            Log.d("message", "gagal" + " --")

                        }
                    } else {
//                    mView.placeholder()
                        Log.d("message", "gagal2" + " --")
                    }
                }
            })
        }
    }
    fun postvisit(ctx:Context,idxdaftar: String) {
        var prefs: SharedPreferences? = null
        prefs = ctx.getSharedPreferences(SharePref.pref, 0)
//        Log.d("tokene", sharedPreferences.getString(my_token, null)!! + " ")
        var user = prefs?.getString(SharePref.login_session, null).toString()
        var level = prefs?.getString(SharePref.level_user, "").toString()
        if (level == "2") {
//            direktur
            itemView.btnvisit.isEnabled = false
        } else {
            var getResponse: ApiInterface
            getResponse = ApiClient.getClient().create(ApiInterface::class.java)
            val call: Call<MessageRes>
            call = getResponse.Postvisit(user, idxdaftar)
//        Log.d("test id",id)
            call.enqueue(object : Callback<MessageRes> {
                override fun onFailure(call: Call<MessageRes>, t: Throwable) {
                    Log.d("Failure", t.message.toString() + " --")

                }

                override fun onResponse(call: Call<MessageRes>, response: Response<MessageRes>) {
                    Log.d("response code nt", response.code().toString() + " -- ")
//                Log.d("response",response.body().toString())
                    if (response.isSuccessful) {
                        val notif = response.body()?.message.toString()
                        Log.d("test 3", notif.toString())

                        if (notif == "sukses") {
//                        mView.showDataPendaftaran(notif)
                            Log.d("message", notif + " --")
                            itemView.txtwaktuvisit.text = response.body()?.waktuvisit.toString()
                            var img = itemView.resources.getDrawable(R.drawable.ic_done)
                            img.setBounds(0, 0, 60, 60)
                            itemView.btnvisit.setCompoundDrawables(null, null, img, null)
                            itemView.btnvisit.isEnabled = false
//                        mView.hideloading()
                        } else {
//                        mView.placeholder()
                            Log.d("message", "gagal" + " --")
                        }
                    } else {
//                    mView.placeholder()
                        Log.d("message", "gagal2" + " --")
                    }
                }
            });
        }
    }
}
