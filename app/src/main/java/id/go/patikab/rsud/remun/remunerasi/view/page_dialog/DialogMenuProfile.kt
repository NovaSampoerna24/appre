package id.go.patikab.rsud.remun.remunerasi.view.page_dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import id.go.patikab.rsud.remun.remunerasi.R
import kotlinx.android.synthetic.main.dialog_menu_profile.*

class DialogMenuProfile(context: Context?) : Dialog(context) {



    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setCancelable(false)

        setContentView(R.layout.dialog_menu_profile)

        btn_ok.setOnClickListener({

        })
        btn.setOnClickListener({
            dismiss()
        })
    }

}