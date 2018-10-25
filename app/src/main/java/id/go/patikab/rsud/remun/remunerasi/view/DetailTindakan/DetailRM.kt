package id.go.patikab.rsud.remun.remunerasi.view.DetailTindakan

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.transition.Slide
import android.transition.TransitionManager
import android.view.Gravity
import android.view.LayoutInflater
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.ListPasienDokter
import id.go.patikab.rsud.remun.remunerasi.R
import kotlinx.android.synthetic.main.layout_remid.*
import id.go.patikab.rsud.remun.remunerasi.config.adapter.*
import kotlinx.android.synthetic.main.item_detail_pembayaran.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.toast
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.widget.*
import kotlinx.android.synthetic.main.itempembayaranremid.view.*
import kotlinx.android.synthetic.main.list_tindakan.*


class DetailRM : AppCompatActivity(), DetailRMView {

    private val mPresenter by lazy { DetailRMPresenter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_remid)

        refresh()
        recycle_data.setOnClickListener {

        }
    }

    fun refresh() {
        launch(UI) { mPresenter.getRM("15", "2018-09-01", "2018-10-31") }
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun showRM(RMList: List<ListPasienDokter.Remid>) {
        recycle_data?.let {
            with(recycle_data) {
                layoutManager = LinearLayoutManager(context)
                var counte: Int
                if (RMList.size > 10) counte = 10 else counte = RMList.size
                adapter = PembayaranRemidAdapter(RMList, counte) { rmlist ->
//                    toast("klik rm")
                    val inflater: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

                    // Inflate a custom view using layout inflater
                    val view = inflater.inflate(R.layout.list_tindakan, null)

                    // Initialize a new instance of popup window
                    val popupWindow = PopupWindow(
                            view, // Custom view to show in popup window
                            LinearLayout.LayoutParams.WRAP_CONTENT, // Width of popup window
                            LinearLayout.LayoutParams.WRAP_CONTENT  // Window height
                    )

                    // Set an elevation for the popup window
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        popupWindow.elevation = 10.0F
                        // Closes the popup window when touch outside.
                        popupWindow.setOutsideTouchable(true);
//                        popupWindow.setFocusable(true);
                    }


                    // If API level 23 or higher then execute the code
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        // Create a new slide animation for popup window enter transition
                        val slideIn = Slide()
                        slideIn.slideEdge = Gravity.TOP
                        popupWindow.enterTransition = slideIn

                        // Slide animation for popup window exit transition
                        val slideOut = Slide()
                        slideOut.slideEdge = Gravity.RIGHT
                        popupWindow.exitTransition = slideOut

                    }
                    // Get the widgets reference from custom view
                    val tv = view.findViewById<TextView>(R.id.text_view)
                    val buttonPopup = view.findViewById<Button>(R.id.button_popup)
                    val lists = view.findViewById<ListView>(R.id.list_tindakans)

                    // Set click listener for popup window's text view
                    tv.setOnClickListener {
                        // Change the text color of popup window's text view
                        tv.setTextColor(Color.RED)
                    }

                    // Set a click listener for popup's button widget
                    buttonPopup.setOnClickListener {
                        // Dismiss the popup window
                        popupWindow.dismiss()
                    }

                    // Set a dismiss listener for popup window
                    popupWindow.setOnDismissListener {
//                        toast("pop up close")
                    }
                    val rem = rmlist.tindakanList
                    val list: ArrayList<String> = ArrayList()
                    for (i in rem) {
                        list.add(i.tindakan)
                        Log.d("text", i.tindakan)
                    }
                    lists.adapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, list)

                    // Finally, show the popup window on app
                    TransitionManager.beginDelayedTransition(list_root)
                    popupWindow.showAtLocation(
                            list_root, // Location to display popup window
                            Gravity.CENTER, // Exact position of layout to display popup
                            0, // X offset
                            0 // Y offset
                    )
                }
            }
        }
    }

}

