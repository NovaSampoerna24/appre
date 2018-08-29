package id.go.patikab.rsud.remun.remunerasi.entity;

import com.google.gson.annotations.SerializedName;

public class DataUser {
    @SerializedName("user_id")
    String id;

    public String getId() {
        return id;
    }
    //    @SerializedName("nama_tindakan")
//    String nama_tindakan;
//    @SerializedName("pengolahan")
//    String pengolahan;
//    @SerializedName("persentase_pribadi")
//    String persentase_pribadi;
}
