package id.go.patikab.rsud.remun.remunerasi.entity;

import com.google.gson.annotations.SerializedName;

public class DetailTindakan {
    @SerializedName("tindakan")
    String tindakan;
    @SerializedName("tanggal")
    String tanggal;
    @SerializedName("tarif")
    String tarif;
public DetailTindakan(String tindakan,String tanggal,String tarif){
    this.tindakan = tindakan;
    this.tanggal = tanggal;
    this.tarif = tarif;
}
    public String getTindakan() {
        return tindakan;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getTarif() {
        return tarif;
    }
}
