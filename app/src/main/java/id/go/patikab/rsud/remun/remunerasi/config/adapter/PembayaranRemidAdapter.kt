package id.go.patikab.rsud.remun.remunerasi.config.adapter
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import id.go.patikab.rsud.remun.remunerasi.R
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.*
import kotlinx.android.synthetic.main.itempembayaranremid.view.*

import android.R.layout.simple_list_item_1

class PembayaranRemidAdapter(private val mItem:List<ListPasienDokter.Remid>,
                             private val counte:Int,
                             private val mOnclik:(remid:ListPasienDokter.Remid)->
                             Unit): RecyclerView.Adapter<RMViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RMViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return RMViewHolder(inflater.inflate(R.layout.itempembayaranremid, parent, false))
    }

    override fun getItemCount(): Int {
        return counte;
       }

    override fun onBindViewHolder(holder: RMViewHolder, position: Int) {
        holder.bind(mItem[position],mOnclik)
            }
}

class RMViewHolder(inflate: View) :RecyclerView.ViewHolder(inflate) {
   fun bind(remid:ListPasienDokter.Remid,onClicks:(ListPasienDokter.Remid)-> Unit){
       with(itemView){
           nama_pasien.text = remid.nama
           rewarde.text = remid.total
           setOnClickListener {
               onClicks (remid)
           }

       }
   }

}
