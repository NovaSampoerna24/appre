package id.go.patikab.rsud.remun.remunerasi.config.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.go.patikab.rsud.remun.remunerasi.R
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.ListJaspel
import kotlinx.android.synthetic.main.item_list_jaspel.view.*


class ListJaspelAdapter(private val mItems: List<ListJaspel.listJaspel>,
                       private val counte: Int,
                       private val mOnclick: (notif: ListJaspel.listJaspel) ->
                       Unit) : RecyclerView.Adapter<VViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return VViewHolder(inflater.inflate(R.layout.item_list_jaspel, parent, false))
    }

    override fun getItemCount(): Int {
        return counte
    }

    override fun onBindViewHolder(holder: VViewHolder, position: Int) {
        holder.bind(mItems[position], mOnclick)
    }

}

class VViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(inf: ListJaspel.listJaspel, onClick: (inf: ListJaspel.listJaspel) -> Unit) {
        with(itemView) {
            judule.text = inf.judul

            deske.text = inf.periode
//            if(inf.pesan.length >= 40) deske.text = inf.pesan + "..."
//            if( inf.jp == "1") labele.text = "Pengumuman" else labele.text ="Pesan"
            labele.text = inf.kategori
//            var dateFormat = SimpleDateFormat("dd/MM/yyyy")
//            val date= dateFormat.parse(inf.tanggal)
//            var d = date.toString()
//            waktu.text = inf.readableDate

            setOnClickListener {
                onClick(inf)
            }
        }

    }


}
