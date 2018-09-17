package id.go.patikab.rsud.remun.remunerasi.config.api;

import id.go.patikab.rsud.remun.remunerasi.model.object.DataTindakan;
import id.go.patikab.rsud.remun.remunerasi.model.object.LoginResponse;
import id.go.patikab.rsud.remun.remunerasi.model.object.RegisterResponse;
import id.go.patikab.rsud.remun.remunerasi.model.object.ValDokter;
import id.go.patikab.rsud.remun.remunerasi.model.object.valueDetailTindakan;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {
//    listdata yg udah punya akun
    @GET("android/service/dokter_list.php")
    Call<ValDokter> getlistDokterlogin();
//    listdata yg belum punya akun
    @GET("android/service/dokter.php")
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
