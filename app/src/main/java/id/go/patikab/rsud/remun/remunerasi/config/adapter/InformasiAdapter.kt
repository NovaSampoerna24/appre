package id.go.patikab.rsud.remun.remunerasi.config.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.go.patikab.rsud.remun.remunerasi.R
import id.go.patikab.rsud.remun.remunerasi.data.lokal.`object`.Informasi
import kotlinx.android.synthetic.main.item_informasi.view.*


class InformasiAdapter(private val mItems:List<Informasi>,
                       private val mOnclick:(informasine:Informasi)->
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
    fun bind(inf:Informasi,onClick:(infs:Informasi)->Unit){
        with(itemView){
            judule.text = inf.judul
            deske.text = inf.deskripsi
            labele.text = inf.label
            setOnClickListener{
                onClick(inf)
            }
        }

}




}
