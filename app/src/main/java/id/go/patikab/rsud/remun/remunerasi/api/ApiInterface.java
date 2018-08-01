package id.go.patikab.rsud.remun.remunerasi.api;

import java.util.List;

import id.go.patikab.rsud.remun.remunerasi.entity.DetailList;
import id.go.patikab.rsud.remun.remunerasi.entity.RegisterResponse;
import id.go.patikab.rsud.remun.remunerasi.entity.ValDetail;
import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {
    @GET("/")
    Call<ValDetail> getDetail();

    @FormUrlEncoded
    @POST("register")
    Call<RegisterResponse>registerResponse();
}
