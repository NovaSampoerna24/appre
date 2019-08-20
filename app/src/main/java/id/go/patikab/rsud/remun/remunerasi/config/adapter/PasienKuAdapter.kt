package id.go.patikab.rsud.remun.remunerasi.config.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.go.patikab.rsud.remun.remunerasi.R
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.PasienKuResponse
import kotlinx.android.synthetic.main.item_pasienku.view.*


class PasienKuAdapter(private val mItems: List<PasienKuResponse.Pasienku>,
                       private val mOnclick: (notif: PasienKuResponse.Pasienku) ->
                       Unit) : RecyclerView.Adapter<VPHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VPHolder {
        val inflater = LayoutInflater.from(parent.context)
        return VPHolder(inflater.inflate(R.layout.item_pasienku, parent, false))
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    override fun onBindViewHolder(holder: VPHolder, position: Int) {
        holder.bind(mItems[position], mOnclick)
    }

}

class VPHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(inf: PasienKuResponse.Pasienku, onClick: (inf: PasienKuResponse.Pasienku) -> Unit) {
        with(itemView) {
            judule.text = inf.judul
            jpasien.text = inf.jumlah
//            deske.text = inf.periode
//            if(inf.pesan.length >= 40) deske.text = inf.pesan + "..."
//            if( inf.jp == "1") labele.text = "Pengumuman" else labele.text ="Pesan"
//            labele.text = inf.kategori
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
