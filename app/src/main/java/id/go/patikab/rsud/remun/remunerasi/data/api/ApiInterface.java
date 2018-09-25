package id.go.patikab.rsud.remun.remunerasi.data.api;

import id.go.patikab.rsud.remun.remunerasi.data.api.object.ProfilData;
import id.go.patikab.rsud.remun.remunerasi.data.api.object.ProfilDetail;
import id.go.patikab.rsud.remun.remunerasi.data.api.object.DataTindakan;
import id.go.patikab.rsud.remun.remunerasi.data.api.object.LoginResponse;
import id.go.patikab.rsud.remun.remunerasi.data.api.object.RegisterResponse;
import id.go.patikab.rsud.remun.remunerasi.data.api.object.ValDokter;
import id.go.patikab.rsud.remun.remunerasi.data.api.object.valueDetailTindakan;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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

    @FormUrlEncoded
    @POST("android/service/profile/ubahPassword.php")
    Call<ServerResponse> toUbahPassword(
            @Field("id_dokter") String id_dokter,
            @Field("passwordbaru") String password
    );
//    getdetailpembayarandokter
    @GET("android/service/dokter_detail.php")
    Call<DataTindakan> getDataTindakan(@Query("id_dokter") String id,
                                       @Query("start") String start,
                                       @Query("end") String end);
//    insert/update foto profil
    @Multipart
    @POST("android/service/profile/updateProfile.php")
    Call<ServerResponse> uploadFile(@Part MultipartBody.Part file,
                                    @Part("id_dokter") RequestBody id,
                                    @Part("foto") RequestBody foto,
                                    @Part("nama_dokter") RequestBody nama_dokter

    );


}
