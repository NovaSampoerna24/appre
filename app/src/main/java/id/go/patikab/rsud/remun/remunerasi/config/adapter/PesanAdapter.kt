package id.go.patikab.rsud.remun.remunerasi.config.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.go.patikab.rsud.remun.remunerasi.R
import kotlinx.android.synthetic.main.item_notifikasi.view.*
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.*



class InformasiAdapter(private val mItems:List<NotifikasiResponse.Notif>,private val counte:Int,
                       private val mOnclick:(notif:NotifikasiResponse.Notif)->
                       Unit):RecyclerView.Adapter<IFViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IFViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return IFViewHolder(inflater.inflate(R.layout.item_notifikasi, parent, false))
    }

    override fun getItemCount(): Int {
        return counte
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
//            if(inf.pesan.length >= 40) deske.text = inf.pesan + "..."
//            if( inf.jp == "1") labele.text = "Pengumuman" else labele.text ="Pesan"
            labele.text = inf.jenis_p
//            var dateFormat = SimpleDateFormat("dd/MM/yyyy")
//            val date= dateFormat.parse(inf.tanggal)
//            var d = date.toString()
            waktu.text = inf.readableDate

            setOnClickListener{
                onClick(inf)
            }
        }

}


}
