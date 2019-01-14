package id.go.patikab.rsud.remun.remunerasi.data.api;



import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import id.go.patikab.rsud.remun.remunerasi.BuildConfig;
public class ApiClient {
    private static Retrofit retrofit = null;
    public static final String base_Url_upload = BuildConfig.SERVER_URL_IMAGES;
    public static final String base_Url = BuildConfig.SERVER_URL;
    public static Retrofit getClient(){
//        Log.d("url test",base_Url+"");
        if(retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(base_Url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}

