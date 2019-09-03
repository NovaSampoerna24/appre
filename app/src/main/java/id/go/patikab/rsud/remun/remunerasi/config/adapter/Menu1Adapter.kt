package id.go.patikab.rsud.remun.remunerasi.config.adapter

import android.content.res.TypedArray
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.go.patikab.rsud.remun.remunerasi.R
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.MenuModel
import kotlinx.android.synthetic.main.item_menu.view.*
class Menu1Adapter(private val mItem: List<MenuModel>,
                  private val myIconList:ArrayList<Int>,
                  private val mOnclik: (remid: MenuModel) ->
                  Unit) : RecyclerView.Adapter<MenuViewHolder1>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder1 {
        val inflater = LayoutInflater.from(parent.context)
        return MenuViewHolder1(inflater.inflate(R.layout.item_menu, parent, false))
    }

    override fun getItemCount(): Int {
    return mItem.size
        }

    override fun onBindViewHolder(holder: MenuViewHolder1, position: Int) {
        holder.bind( mItem[position],myIconList.get(position),onClicks =  mOnclik)

    }


}

class MenuViewHolder1(inflate: View):RecyclerView.ViewHolder(inflate) {
    fun bind(menuM:MenuModel,icon:Int,onClicks:(MenuModel)->Unit){
        with(itemView){
            title_icon.text = menuM.nama
            if(image_icon != null){
                image_icon.setImageResource(icon)

            }
            setOnClickListener {
                onClicks(menuM)
            }
        }
    }

}
