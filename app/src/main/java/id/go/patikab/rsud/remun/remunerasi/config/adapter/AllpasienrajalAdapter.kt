package id.go.patikab.rsud.remun.remunerasi.config.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.go.patikab.rsud.remun.remunerasi.R
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.Allrajal
import kotlinx.android.synthetic.main.item_allpasien.view.*


class AllpasienrajalAdapater(private val mItems: List<Allrajal.Datarajal>,
                             private val counte: Int,
                             private val mOnclick: (notif: Allrajal.Datarajal) ->
                             Unit) : RecyclerView.Adapter<AllrajalHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllrajalHolder {
        val inflater = LayoutInflater.from(parent.context)
        return AllrajalHolder(inflater.inflate(R.layout.item_allpasien, parent, false))
    }

    override fun getItemCount(): Int {
        return counte
    }

    override fun onBindViewHolder(holder: AllrajalHolder, position: Int) {
        holder.bind(mItems[position], mOnclick)
    }

}

class AllrajalHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(inf: Allrajal.Datarajal, onClick: (inf: Allrajal.Datarajal) -> Unit) {
        with(itemView) {
            txt_dokter.text = inf.dokter
            txt_jumlah.text = inf.jumlah
            //            judule.text = inf.judul
//
//            deske.text = inf.pesan
////            if(inf.pesan.length >= 40) deske.text = inf.pesan + "..."
////            if( inf.jp == "1") labele.text = "Pengumuman" else labele.text ="Pesan"
//            labele.text = inf.jenis_p
////            var dateFormat = SimpleDateFormat("dd/MM/yyyy")
////            val date= dateFormat.parse(inf.tanggal)
////            var d = date.toString()
//            waktu.text = inf.readableDate

            setOnClickListener {
                onClick(inf)
            }
        }

    }


}