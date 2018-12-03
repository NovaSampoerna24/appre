package id.go.patikab.rsud.remun.remunerasi.config.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.go.patikab.rsud.remun.remunerasi.R
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.ListPasienDokter
import kotlinx.android.synthetic.main.item_tindakan.view.*

class TindakanAdapter(private val mItems: List<ListPasienDokter.Remid.Tindakan>)
    : RecyclerView.Adapter<TDAdapter>() {

    override fun onBindViewHolder(holder: TDAdapter, position: Int) {
        holder.bind(mItems[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TDAdapter {
        val inflater = LayoutInflater.from(parent.context)
        return TDAdapter(inflater.inflate(R.layout.item_tindakan, parent, false))

    }
    override fun getItemCount(): Int {
        return mItems.size
    }
}
class TDAdapter(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(tindakan: ListPasienDokter.Remid.Tindakan) {
        with(itemView) {
            nama_tindakan.text = tindakan.tindakan
//            tarif_tindakan.text = tindakan.tarif_tindakan
//            tarif_jp.text = tindakan.tarif_jp
            tarif_reward_tindakan.text = tindakan.reward
        }
    }
}
