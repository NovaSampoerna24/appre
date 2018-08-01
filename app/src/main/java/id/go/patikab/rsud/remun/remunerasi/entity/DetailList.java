package id.go.patikab.rsud.remun.remunerasi.entity;

import com.google.gson.annotations.SerializedName;

public class DetailList {
    @SerializedName("id")
    String id;
    @SerializedName("nama_tindakan")
    String nama_tindakan;
    @SerializedName("pengolahan")
    String pengolahan;
    @SerializedName("persentase_pribadi")
    String persentase_pribadi;
    @SerializedName("dapat")
    String dapat;

    public DetailList(String id,String pengolahan,String nama_tindakan,String persentase_pribadi,String dapat){
        this.id = id;
        this.nama_tindakan = nama_tindakan;
        this.pengolahan = pengolahan;
        this.persentase_pribadi = persentase_pribadi;
        this.dapat = dapat;
    }
    public String getId() {
        return id;
    }

    public String getNama_tindakan() {
        return nama_tindakan;
    }

    public String getPengolahan() {
        return pengolahan;
    }

    public String getPersentase_pribadi() {
        return persentase_pribadi;
    }

    public String getDapat() {
        return dapat;
    }
}
