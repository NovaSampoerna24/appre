package id.go.patikab.rsud.remun.remunerasi.data.api;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

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
            @Field("re_password") String re_password,
            @HeaderMap Map<String, String> headers
    );

    @FormUrlEncoded
    @POST("android/service/login.php")
    Call<AuthResponse> getloginresponse(
            @Field("id_dokter") String id_dokter,
            @Field("password") String password,
            @Field("device_token") String device_token,
            @HeaderMap Map<String, String> headers
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
    //    getdetailpembayaran rm

    @GET("android/service/list_pasien_dokter.php")
    Call<ListPasienDokter> getReMid(@Query("signature") String signature,
                                    @Query("id_dokter") String id,
                                    @Query("dari") String start,
                                    @Query("sampai") String end, @HeaderMap Map<String, String> headers);

    //    insert/update foto profil
    @Multipart
    @POST("android/service/profile/updateProfile.php")
    Call<ServerResponse> uploadFile(@Part MultipartBody.Part file,
                                    @Part("id_dokter") RequestBody id,
                                    @Part("foto") RequestBody foto,
                                    @Part("nama_dokter") RequestBody nama_dokter

    );

    @FormUrlEncoded
    @POST("android/service/profile/insertProfile.php")
    Call<ServerResponse> insert_profile(@Field("id_dokter") String id,
                                        @Field("nama_dokter") String nama_dokter
    );

    //    melihat data profil(foto)
    @GET("android/service/profile/detailProfile.php")
    Call<ProfilGetData> getDataProfil(@Query("id") String id);

    @GET("android/service/cek_version.php")
    Call<VersionCek> getVersion(@Query("nama_aplikasi") String nama_aplikasi,
                                @Query("versi") String versi,
                                @Query("app_key") String app_key);

    //    get tarifinacbgs perbulan saat itu juga
    @GET("android/service/tarif_bulan.php")
    Call<Inacbgs> getTarifInacgs();

    @GET("/android/PanelAdminDokter/index.php/api/pasien-rajal/{tanggal}")
    Call<Allrajal> getallrajal(@Path("tanggal")String tanggal);

    @GET("/android/PanelAdminDokter/index.php/api/pasien-ranap")
    Call<Allranap> getallranap();

    @FormUrlEncoded
    @POST("/android/PanelAdminDokter/index.php/admin/laporkan/plush")
    Call<MessageRes> Postlaporkan(
            @Field("dokter") String kddokter,
            @Field("pasien") String idxdaftar,
            @Field("laporkan") String keterangan
    );
    @FormUrlEncoded
    @POST("/android/PanelAdminDokter/index.php/admin/visit/plush")
    Call<MessageRes> Postvisit(
            @Field("dokter") String kddokter,
            @Field("pasien") String idxdaftar
    );

    //    get notifikasi
    @FormUrlEncoded
    @POST("android/service/pengumuman/pengumuman.php")
    Call<NotifikasiResponse> get_pengumuman(@Field("id_dokter") String id);

    @GET("android/service/dokter_aktif.php")
    Call<ListDokter> getListDokter();

    @GET("android/service/pasien_ranap/list_pasien_ranap.php")
    Call<ListPasien> getListPasienRanap(@Query("id_dokter") String id);

//    list pasien rajal
    @GET("android/service/pasien_rajal/list_pasien_rajal.php")
    Call<ListPasien> getListPasienRajal(
            @Query("id_dokter") String id,
            @Query("tanggal")String tanggal
            );
//    search pasien rajal
    @GET("android/service/pasien_rajal/search_pasien_rajal.php")
    Call<ListPasien> getListSearchPasienRajal(
            @Query("id_dokter") String id,
            @Query("tanggal") String tanggal,
            @Query("keyword") String keyword
            );
    //    search pasien ranap
    @GET("android/service/pasien_ranap/search_pasien_ranap.php")
    Call<ListPasien> getListSearchPasienRanap(
            @Query("id_dokter") String id,
            @Query("keyword") String keyword);

//    get detail pasien rajal
@GET("android/service/pasien_rajal/detail_pasien_rajal.php")
Call<DetailPasienRajal> getDetailPasien(
        @Query("idxdaftar") String idxdaftar,
        @Query("nomr")String nomr);
    //    get detail pasien ranap
    @GET("android/service/pasien_ranap/detail_pasien_ranap.php")
    Call<DetailPasienRanap> getDetailPasienRanap(
            @Query("idxdaftar") String idxdaftar,
            @Query("nomr")String nomr);

    //    get ringkasan
    //    getdetailpembayaran rm
    @GET("android/service/ringkasan_pelaksana/ringkasan_pasien.php")
    Call<RingkasanModel> getringkasanPasien(@Query("id_dokter") String id);

    //    getdetailpembayaran rm
    @GET("android/service/ringkasan_pelaksana/ringkasan_penghasilan.php")
    Call<RingkasanModel> getringkasanPenghasilan(@Query("id_dokter") String id);

    //    getdetailpembayaran rm
    @GET("android/service/ringkasan_pelaksana/ringkasan_tindakan.php")
    Call<RingkasanModel> getringkasanTindakan(@Query("id_dokter") String id);

    //    get list pembayaran
    @GET("android/service/list_jaspel/list_jp.php")
    Call<ListJaspel> getlistJaspel();

    //    get pasienku
    @GET("android/service/pasien/pasien_ku.php")
    Call<PasienKuResponse> getPasienku(
            @Query("id_dokter")String id
    );

    //    get list menu profil
    @GET("android/service/v1/menu_profil.php")
    Call<ResponseMenu> getprofilMenu(@Query("id_dokter")String id_dokter);

    //   Version v1 Beta
    //    daftar proses
    @FormUrlEncoded
    @POST("android/service/v1/daftar.php")
    Call<DaftarResponse> goDaftar(@Field("username") String username,
                                @Field("email") String email,
                                @Field("password") String password,
                                @Field("re_password") String re_password,
                                @HeaderMap Map<String, String> headers);
    //    login proses
    @FormUrlEncoded
    @POST("android/service/v1/login.php")
    Call<LoginResponse> goLogin(@Field("username") String username,
                                @Field("password") String password,
                                @HeaderMap Map<String, String> headers);
//    cek aktivasi akun jika sudah diaktifkan status akan bernilai true
    @FormUrlEncoded
    @POST("android/service/v1/function.php")
    Call<AktivasiResponse> cek_aktivasi(@Field("aktivasi_cek") String username,
                                @HeaderMap Map<String, String> headers);
//    cek username apakah sudah terdaftar jika belum status true
    @FormUrlEncoded
    @POST("android/service/v1/function.php")
    Call<FunctionResponse> cek_username(@Field("username_cek") String username,
                                        @HeaderMap Map<String, String> headers);
//    cek email apakah sudah terdaftar jika belum status true
    @FormUrlEncoded
    @POST("android/service/v1/function.php")
    Call<FunctionResponse> cek_email(@Field("email") String username,
                                        @HeaderMap Map<String, String> headers);
}
