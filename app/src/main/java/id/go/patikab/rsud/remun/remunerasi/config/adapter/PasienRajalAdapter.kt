package id.go.patikab.rsud.remun.remunerasi.config.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.go.patikab.rsud.remun.remunerasi.R
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.ListPasien
import kotlinx.android.synthetic.main.item_pasien_rajal.view.*


class PasienRajalAdapter(private val mItems: List<ListPasien.Pasiene>,
                       private val counte: Int,
                    private val mOnclick: (notif: ListPasien.Pasiene) -> Unit
                    ) : RecyclerView.Adapter<IFXXiewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IFXXiewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return IFXXiewHolder(inflater.inflate(R.layout.item_pasien_rajal, parent, false))
    }

    override fun getItemCount(): Int {
        return counte
    }

    override fun onBindViewHolder(holder: IFXXiewHolder, position: Int) {
        holder.bind(mItems[position], mOnclick)
    }

}

class IFXXiewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(inf: ListPasien.Pasiene, onClick: (inf: ListPasien.Pasiene) -> Unit) {
        with(itemView) {
            nama_pasien.text = inf.NAMA.trim()
            unit.text = inf.UNIT
            tgllahir.text = inf.tanggalLahir
            txt_norm.text = inf.NOMR
            txt_penjamin.text = inf.carabayar
            if(inf.type == "rajal"){
                prakiraan_waktu_pelayanan.text = inf.waktu
            }
            setOnClickListener{
                onClick(inf)
            }

        }

    }
}
