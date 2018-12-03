package id.go.patikab.rsud.remun.remunerasi.config.adapter

import android.content.res.TypedArray
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.go.patikab.rsud.remun.remunerasi.R
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.MenuModel
import kotlinx.android.synthetic.main.item_menu.view.*
import android.graphics.drawable.Drawable
import com.squareup.picasso.Picasso
import id.go.patikab.rsud.remun.remunerasi.R.color.icons
import kotlinx.android.synthetic.main.acitivity_detail_profil.*
import id.go.patikab.rsud.remun.remunerasi.data.api.ApiClient.base_Url_upload

class MenuAdapter(private val mItem: List<MenuModel>,
                  private val myIconList:ArrayList<Int>,
                  private val mOnclik: (remid: MenuModel) ->
                  Unit) : RecyclerView.Adapter<MenuViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
       val inflater = LayoutInflater.from(parent.context)
        return MenuViewHolder(inflater.inflate(R.layout.item_menu, parent, false))
           }

    override fun getItemCount(): Int {
    return mItem.size
        }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int){
    holder.bind( mItem[position],myIconList.get(position),onClicks =  mOnclik)
  }

}

class MenuViewHolder(inflate: View):RecyclerView.ViewHolder(inflate) {
    fun bind(menuM:MenuModel,icon:Int,onClicks:(MenuModel)->Unit){
        with(itemView){
            title_icon.text = menuM.nama
            if(image_icon != null){
                image_icon.setImageResource(icon.toInt())

                }
            setOnClickListener {
                onClicks(menuM)
            }
        }
    }

}
