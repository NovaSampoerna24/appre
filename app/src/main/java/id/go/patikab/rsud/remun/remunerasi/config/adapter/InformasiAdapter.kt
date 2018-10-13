package id.go.patikab.rsud.remun.remunerasi.config.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.go.patikab.rsud.remun.remunerasi.R
import id.go.patikab.rsud.remun.remunerasi.data.lokal.`object`.Informasi
import kotlinx.android.synthetic.main.item_informasi.view.*
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.*
import java.text.SimpleDateFormat
import java.util.*


class InformasiAdapter(private val mItems:List<NotifikasiResponse.Notif>,
                       private val mOnclick:(notif:NotifikasiResponse.Notif)->
                       Unit):RecyclerView.Adapter<IFViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IFViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return IFViewHolder(inflater.inflate(R.layout.item_informasi, parent, false))
    }

    override fun getItemCount(): Int {
        return mItems.size
          }

    override fun onBindViewHolder(holder: IFViewHolder, position: Int) {
        holder.bind(mItems[position], mOnclick)
    }

}

class IFViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
    fun bind(inf:NotifikasiResponse.Notif,onClick:(inf:NotifikasiResponse.Notif)->Unit){
        with(itemView){
            judule.text = inf.judul
            deske.text = inf.pesan
            if( inf.jp == "1") labele.text = "Pengumuman" else labele.text ="pesan"



            waktu.text = inf.waktu
            setOnClickListener{
                onClick(inf)
            }
        }

}




}
