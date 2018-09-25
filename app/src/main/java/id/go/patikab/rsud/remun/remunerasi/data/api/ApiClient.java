package id.go.patikab.rsud.remun.remunerasi.data.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit = null;
    public static final String base_Url_upload = "http://onlines.rsud.patikab.go.id:8081/android/service/upload/";
    public static final String base_Url = "http://onlines.rsud.patikab.go.id:8081/";
    public static Retrofit getClient(){
        if(retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(base_Url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}

