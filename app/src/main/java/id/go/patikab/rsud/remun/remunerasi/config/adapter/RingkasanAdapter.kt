package id.go.patikab.rsud.remun.remunerasi.config.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.go.patikab.rsud.remun.remunerasi.R
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.RingkasanModel
import kotlinx.android.synthetic.main.item_tk.view.*

class RingkasanAdapter(private val mItems:List<RingkasanModel.Ringkasan>,
                       private val counte:Int,
                       private val mOnclick:(ringkasan:RingkasanModel.Ringkasan) -> Unit)
    :RecyclerView.Adapter<RingkasanViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RingkasanViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return RingkasanViewHolder(inflater.inflate(R.layout.item_tk, parent, false))
    }

    override fun getItemCount(): Int {
        return counte
   }

    override fun onBindViewHolder(holder: RingkasanViewHolder, position: Int) {
        holder.bind(mItems[position],mOnclick)
   }


}

class RingkasanViewHolder(itemView : View):RecyclerView.ViewHolder(itemView) {
    fun bind(ringkasan: RingkasanModel.Ringkasan,onClick:(ringkasan:RingkasanModel.Ringkasan)->Unit){
        with(itemView){
            txt_judul.text = ringkasan.judul
            txt_taksiran.text = ringkasan.taksiran
            setOnClickListener{
                onClick(ringkasan)
            }
        }
    }

}
