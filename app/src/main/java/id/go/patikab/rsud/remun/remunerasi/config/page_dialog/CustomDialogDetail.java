package id.go.patikab.rsud.remun.remunerasi.config.page_dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import id.go.patikab.rsud.remun.remunerasi.R;

public class CustomDialogDetail extends Dialog implements View.OnClickListener {
    public Context context;
    public Dialog d;
    public Button yes;
    public CustomDialogDetail(Context a) {
        super(a);
        this.context = a;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCancelable(false);
        setContentView(R.layout.dialog_custom_detail);
        yes = (Button) findViewById(R.id.btn_ok);
        yes.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_ok:
                break;
            default:
                break;
        }
        dismiss();
    }
}
