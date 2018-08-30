package id.go.patikab.rsud.remun.remunerasi.page_dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import id.go.patikab.rsud.remun.remunerasi.R;
import id.go.patikab.rsud.remun.remunerasi.entity.DetailTindakan;

public class CustomDialogFailure extends Dialog implements View.OnClickListener {
    public Context context;
    public DetailTindakan tindakan;
    public Dialog d;
    public Button yes;

    public CustomDialogFailure(Context a) {
        super(a);
        this.context = a;


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_custom_failure);
        setCancelable(false);
        yes = (Button) findViewById(R.id.btn_yes);
        yes.setOnClickListener(this);
    }
    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_yes:
                break;
            default:
                break;
        }
        dismiss();
    }
}
