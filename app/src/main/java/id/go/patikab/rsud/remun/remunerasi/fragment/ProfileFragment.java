package id.go.patikab.rsud.remun.remunerasi.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import id.go.patikab.rsud.remun.remunerasi.R;

import static id.go.patikab.rsud.remun.remunerasi.database.sharepreference.SharePref.login_session;
import static id.go.patikab.rsud.remun.remunerasi.database.sharepreference.SharePref.my_token;
import static id.go.patikab.rsud.remun.remunerasi.database.sharepreference.SharePref.nm_dokter;
import static id.go.patikab.rsud.remun.remunerasi.database.sharepreference.SharePref.pref;


public class ProfileFragment extends Fragment {
    SharedPreferences sharedPreferences;
    String kd_user, nama_dokter;
    TextView nm_dk;
    CircleImageView circleImageViewdokter;
    Boolean gendermen = true;
    public ActionBar getActionBar() {
        return ((AppCompatActivity) getActivity()).getSupportActionBar();
    }

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        nm_dk = (TextView) view.findViewById(R.id.nm_dokter);
        circleImageViewdokter = (CircleImageView)view.findViewById(R.id.profile_image);
        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActionBar().setTitle("Profile");

        sharedPreferences = getActivity().getSharedPreferences(pref, Context.MODE_PRIVATE);
        Log.d("tokene", sharedPreferences.getString(my_token, null) + " ");
        kd_user = sharedPreferences.getString(login_session, null);
        nama_dokter = sharedPreferences.getString(nm_dokter, null);
        nm_dk.setText(nama_dokter);
        if(gendermen == false){
            circleImageViewdokter.setImageResource(R.drawable.dk);
        }else{
            circleImageViewdokter.setImageResource(R.drawable.dk_1);
        }
    }
}
