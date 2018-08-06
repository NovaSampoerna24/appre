package id.go.patikab.rsud.remun.remunerasi.firebase;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import id.go.patikab.rsud.remun.remunerasi.sharePreference.sharepreference;

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {

    private static final String REG_Token = "REG_TOKEN";
    sharepreference sp_tken;
    @Override
    public void onTokenRefresh() {
        String recent_token = FirebaseInstanceId.getInstance().getToken();

        sp_tken = new sharepreference();
        sp_tken.save(getApplicationContext(),recent_token);
//        Log.d(REG_Token,recent_token);
        String token = sp_tken.getValue(getApplicationContext());
        Log.d("tokene",token);

    }
}
