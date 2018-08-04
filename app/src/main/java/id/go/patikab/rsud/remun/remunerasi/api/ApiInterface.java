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

    @GET("android/dokter.php")
    Call<ValDokter> getDokter();

    @FormUrlEncoded
    @POST("prototype/service/register.php")
    Call<RegisterResponse> getresponse(
            @Field("nama_dokter") String nama_dokter,
            @Field("password") String password,
            @Field("device_token") String device_token
    );

    @FormUrlEncoded
    @POST("prototype/service/login.php")
    Call<LoginResponse> getloginresponse(
            @Field("nama_dokter") String nama_dokter,
            @Field("password") String password,
            @Field("device_token") String device_token
    );
}
