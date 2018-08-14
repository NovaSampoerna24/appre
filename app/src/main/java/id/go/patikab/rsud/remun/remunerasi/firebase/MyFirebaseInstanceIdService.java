package id.go.patikab.rsud.remun.remunerasi.firebase;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {
    private static final String REG_Token = "REG_TOKEN";
    public static final String pref  = "tokene";
    public static final String my_token = "token";
    public static final String date_up = "00-00-0000 00:00:00";
    public static final String login_session = null;

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
