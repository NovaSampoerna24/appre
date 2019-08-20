package id.go.patikab.rsud.remun.remunerasi.config.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.go.patikab.rsud.remun.remunerasi.R
import kotlinx.android.synthetic.main.item_pasien.view.*
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.ListPasien
import kotlinx.android.synthetic.main.detail_pasien.view.*


class PasienAdapter(private val mItems: List<ListPasien.Pasiene>,
                       private val counte: Int,
                       private val mOnclick: (notif: ListPasien.Pasiene) ->
                       Unit) : RecyclerView.Adapter<IFXiewHolder>() {

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
