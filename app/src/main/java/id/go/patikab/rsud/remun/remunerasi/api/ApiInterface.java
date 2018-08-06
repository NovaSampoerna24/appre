package id.go.patikab.rsud.remun.remunerasi.api;

import java.util.List;

import id.go.patikab.rsud.remun.remunerasi.entity.DetailList;
import id.go.patikab.rsud.remun.remunerasi.entity.LoginResponse;
import id.go.patikab.rsud.remun.remunerasi.entity.RegisterResponse;
import id.go.patikab.rsud.remun.remunerasi.entity.ValDetail;
import id.go.patikab.rsud.remun.remunerasi.entity.ValDokter;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {
    @GET("android/data.php")
    Call<ValDetail> getDetail();

    @GET("android/service/dokter_list.php")
    Call<ValDokter> getlistDokterlogin();

    @GET("android/dokter.php")
    Call<ValDokter> getDokter();

    @FormUrlEncoded
    @POST("android/service/register.php")
    Call<RegisterResponse> getresponse(
            @Field("id_dokter") String id_dokter,
            @Field("password") String password,
            @Field("device_token") String device_token,
            @Field("re_password") String re_password
    );

    @FormUrlEncoded
    @POST("android/service/login.php")
    Call<LoginResponse> getloginresponse(
            @Field("id_dokter") String id_dokter,
            @Field("password") String password,
            @Field("device_token") String device_token
    );
}
