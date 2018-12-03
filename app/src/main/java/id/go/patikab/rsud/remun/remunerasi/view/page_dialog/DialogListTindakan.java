package id.go.patikab.rsud.remun.remunerasi.view.page_dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.ListPasienDokter;
import java.util.List;
import id.go.patikab.rsud.remun.remunerasi.R;
import id.go.patikab.rsud.remun.remunerasi.config.adapter.TindakanAdapter;

public class DialogListTindakan extends Dialog implements View.OnClickListener {
    public Context context;
    List<ListPasienDokter.Remid.Tindakan> lists;
    public Button yes;
    LinearLayoutManager layoutManager;

    RecyclerView mrecRecyclerView;
    RecyclerView.Adapter madapter;
    RecyclerView.LayoutManager mlayoutManager;

    public DialogListTindakan(Context a, List<ListPasienDokter.Remid.Tindakan> list) {
        super(a);
        this.context = a;
        this.lists = list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCancelable(false);
        setContentView(R.layout.list_tindakan);

        yes = (Button) findViewById(R.id.btn_ok);
        yes.setOnClickListener(this);
        mrecRecyclerView = (RecyclerView) findViewById(R.id.list_tindakans);

        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        mlayoutManager = new LinearLayoutManager(context);

        mrecRecyclerView.setLayoutManager(layoutManager);
        madapter = new TindakanAdapter(lists);
        mrecRecyclerView.setAdapter(madapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_ok:
                dismiss();
                break;
            default:
                break;
        }

    }
}
