package id.go.patikab.rsud.remun.remunerasi.config.adapter

import android.content.res.TypedArray
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.go.patikab.rsud.remun.remunerasi.R
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.ResponseMenu
import kotlinx.android.synthetic.main.item_menu.view.*
import android.graphics.drawable.Drawable
import com.squareup.picasso.Picasso
import id.go.patikab.rsud.remun.remunerasi.R.color.icons
import kotlinx.android.synthetic.main.acitivity_detail_profil.*
import id.go.patikab.rsud.remun.remunerasi.data.api.ApiClient.base_Url_upload

class MenuAdapter(private val mItem: List<ResponseMenu.Menune>,
                  private val mOnclik: (remid: ResponseMenu.Menune) ->
                  Unit) : RecyclerView.Adapter<MenuViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
       val inflater = LayoutInflater.from(parent.context)
        return MenuViewHolder(inflater.inflate(R.layout.item_menu, parent, false))
           }

    override fun getItemCount(): Int {
    return mItem.size
        }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int){
    holder.bind( mItem[position],onClicks =  mOnclik)
  }

}

class MenuViewHolder(inflate: View):RecyclerView.ViewHolder(inflate) {
    fun bind(menuM:ResponseMenu.Menune,onClicks:(ResponseMenu.Menune)->Unit){
        with(itemView){
            title_icon?.text = menuM.judul
            if(menuM.icon != ""){
                Picasso.get()
                        ?.load(menuM.icon+"")
                        ?.error(R.drawable.hospital)
                        ?.into(image_icon)
            }
            setOnClickListener {
                onClicks(menuM)
            }
        }
    }

}
