package id.go.patikab.rsud.remun.remunerasi.data.api;

import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.*;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface ApiInterface {
//    listdata yg udah punya akun
    @GET("android/service/dokter_list.php")
    Call<DokterGetData> getlistDokterlogin();
//    listdata yg belum punya akun
    @GET("android/service/dokter.php")
    Call<DokterGetData> getDokter();

    @GET("android/service/detail_tindakan.php")
    Call<DetailPasien> getValueDetailTindakanCall(@Query("id") String id);

    @FormUrlEncoded
    @POST("android/service/register.php")
    Call<AuthResponse> getresponse(
            @Field("id_dokter") String id_dokter,
            @Field("password") String password,
            @Field("device_token") String device_token,
            @Field("re_password") String re_password
    );

    @FormUrlEncoded
    @POST("android/service/login.php")
    Call<AuthResponse> getloginresponse(
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
    Call<TindakanGetData> getDataTindakan(@Query("id_dokter") String id,
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
    @GET("android/service/profile/detailProfile.php")
    Call<ProfilGetData> getDataProfil(@Query("id") String id);

}
