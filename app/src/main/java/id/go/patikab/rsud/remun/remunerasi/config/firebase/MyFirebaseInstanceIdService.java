package id.go.patikab.rsud.remun.remunerasi.config.firebase;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import static id.go.patikab.rsud.remun.remunerasi.data.lokal.sharepreference.SharePref.REG_Token;
import static id.go.patikab.rsud.remun.remunerasi.data.lokal.sharepreference.SharePref.my_token;
import static id.go.patikab.rsud.remun.remunerasi.data.lokal.sharepreference.SharePref.pref;

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {


    SharedPreferences preferences;
    @Override
    public void onTokenRefresh() {
        String recent_token = FirebaseInstanceId.getInstance().getToken();
        Log.d(REG_Token,recent_token);
        preferences = getSharedPreferences(pref, Context.MODE_PRIVATE);
        if(!preferences.contains(my_token)){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(my_token,recent_token);
            editor.apply();
        }
    }
}

