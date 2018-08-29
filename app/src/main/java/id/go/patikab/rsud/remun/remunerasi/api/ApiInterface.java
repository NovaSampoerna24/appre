package id.go.patikab.rsud.remun.remunerasi.api;

import id.go.patikab.rsud.remun.remunerasi.entity.DataTindakan;
import id.go.patikab.rsud.remun.remunerasi.entity.LoginResponse;
import id.go.patikab.rsud.remun.remunerasi.entity.RegisterResponse;
import id.go.patikab.rsud.remun.remunerasi.entity.ValDokter;
import id.go.patikab.rsud.remun.remunerasi.entity.valueDetailTindakan;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("android/service/dokter_list.php")
    Call<ValDokter> getlistDokterlogin();

    @GET("android/dokter.php")
    Call<ValDokter> getDokter();

    @GET("android/service/detail_tindakan.php")
    Call<valueDetailTindakan> getValueDetailTindakanCall(@Query("id") String id);

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

    @GET("android/service/dokter_detail.php")
    Call<DataTindakan> getDataTindakan(@Query("id_dokter") String id,
                                       @Query("start") String start,
                                       @Query("end") String end);
}
