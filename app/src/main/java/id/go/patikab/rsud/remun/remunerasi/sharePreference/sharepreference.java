package id.go.patikab.rsud.remun.remunerasi.sharePreference;

import android.content.Context;
import android.content.SharedPreferences;

public class sharepreference {
    public static final String pref_name = "pf_token";

    SharedPreferences tokens;
    SharedPreferences.Editor editor;

    public sharepreference(){
        super();
    }

    public void save(Context context ,String token){
        tokens = context.getSharedPreferences(pref_name,Context.MODE_PRIVATE);
        editor = tokens.edit();

        editor.putString("token",token);
        editor.commit();
    }
    public String getValue(Context context){
        String tokene ;
        tokens =context.getSharedPreferences(pref_name,Context.MODE_PRIVATE);
        tokene = tokens.getString("token",null);
        return tokene;
    }
    public void removeValue(Context context){
        tokens= context.getSharedPreferences(pref_name,context.MODE_PRIVATE);
        editor = tokens.edit();

        editor.remove("token");
        editor.commit();
    }
}
